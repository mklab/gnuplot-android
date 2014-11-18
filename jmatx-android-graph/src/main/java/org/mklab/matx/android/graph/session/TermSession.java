/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mklab.matx.android.graph.session;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import org.mklab.matx.android.graph.MainActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * A terminal session, consisting of a TerminalEmulator, a TranscriptScreen,
 * the PID of the process attached to the session, and the I/O streams used to
 * talk to the process.
 */
public class TermSession {

    private int mProcId;
    private FileDescriptor mTermFd;
    private FileOutputStream mTermOut;
    private FileInputStream mTermIn;
    private Thread mWatcherThread;

    private Thread mPollingThread;
    private Thread mWaitThread;
    private ByteQueue mByteQueue;
    private byte[] mReceiveBuffer;

    private CharBuffer mWriteCharBuffer;
    private ByteBuffer mWriteByteBuffer;
    private CharsetEncoder mUTF8Encoder;

    private static final int NEW_INPUT = 1;
    private static final int PROCESS_EXITED = 2;
    
    private MainActivity mParent;

    /**
     * Callback to be invoked when a TermSession finishes.
     */
    public interface FinishCallback {
        void onSessionFinish(TermSession session);
    }

    private boolean mIsRunning = false;
    private Handler mMsgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!mIsRunning) {
                return;
            }
            if (msg.what == NEW_INPUT) {
                readFromProcess();
            } else if (msg.what == PROCESS_EXITED) {
                onProcessExit((Integer) msg.obj);
            }
            Log.d("TERM", "HANDLE END");
        }
    };

    public TermSession(MainActivity inParent) {

        int[] processId = new int[1];
        
        mParent = inParent;

        createSubprocess(processId);
        mProcId = processId[0];
        mTermOut = new FileOutputStream(mTermFd);
        mTermIn = new FileInputStream(mTermFd);

        mWatcherThread = new Thread() {
             @Override
             public void run() {
                Log.i("term", "waiting for: " + mProcId);
                int result = Exec.waitFor(mProcId);
                Log.i("term", "Subprocess exited: " + result);
                mMsgHandler.sendMessage(mMsgHandler.obtainMessage(PROCESS_EXITED, result));
                Log.d("TERM", "RUN END");
             }
        };
        mWatcherThread.setName("Process watcher");

        mWriteCharBuffer = CharBuffer.allocate(2);
        mWriteByteBuffer = ByteBuffer.allocate(4);
        mUTF8Encoder = Charset.forName("UTF-8").newEncoder();
        mUTF8Encoder.onMalformedInput(CodingErrorAction.REPLACE);
        mUTF8Encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        mReceiveBuffer = new byte[4 * 1024];
        mByteQueue = new ByteQueue(4 * 1024);

        mPollingThread = new Thread() {
            private byte[] mBuffer = new byte[4096];

            @Override
            public void run() {
                try {
                    while(true) {
                        int read = mTermIn.read(mBuffer);
                        if (read == -1) {
                            // EOF -- process exited
                            return;
                        }
                        mByteQueue.write(mBuffer, 0, read);
                        mMsgHandler.sendMessage(
                                mMsgHandler.obtainMessage(NEW_INPUT));
                    }
                } catch (IOException e) {
                } catch (InterruptedException e) {
                }
                Log.d("TERM", "TREAD END");
            }
        };
        mPollingThread.setName("Input reader");
        mWaitThread = new Thread(){
        	@Override
        	public void run() {
        		try {
        			Log.d("TERM", "WAIT START");
					sleep(500);
					Log.d("TERM", "WAIT END");
					mParent.sendBitmap();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        };
        Log.d("TERM", "SESSION END");
    }

    private void initializeEmulator() {
    	Log.d("TERM", "PROCESS START");
        mIsRunning = true;
        mWatcherThread.start();
        mPollingThread.start();
        mWaitThread.start();
		Log.d("TERM", "PROCESS END");
        /*
        try {
			mPollingThread.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    }

    public void write(String data) {
        try {
            mTermOut.write(data.getBytes("UTF-8"));
            mTermOut.flush();
        } catch (IOException e) {
            // Ignore exception
            // We don't really care if the receiver isn't listening.
            // We just make a best effort to answer the query.
        }
    }

    public void write(int codePoint) {
        CharBuffer charBuf = mWriteCharBuffer;
        ByteBuffer byteBuf = mWriteByteBuffer;
        CharsetEncoder encoder = mUTF8Encoder;
        try {
            charBuf.clear();
            byteBuf.clear();
            Character.toChars(codePoint, charBuf.array(), 0);
            encoder.reset();
            encoder.encode(charBuf, byteBuf, true);
            encoder.flush(byteBuf);
            mTermOut.write(byteBuf.array(), 0, byteBuf.position()-1);
            mTermOut.flush();
        } catch (IOException e) {
            // Ignore exception
        }
    }

    private void createSubprocess(int[] processId) {
        mTermFd = Exec.createSubprocess(processId);
    }

    public FileOutputStream getTermOut() {
        return mTermOut;
    }

    public void updateSize(int columns, int rows) {
        // Inform the attached pty of our new size:
        Exec.setPtyWindowSize(mTermFd, rows, columns, 0, 0);
        if (mIsRunning == false) {
        	initializeEmulator();
        }
    }

    /**
     * Look for new input from the ptty, send it to the terminal emulator.
     */
    private void readFromProcess() {
        int bytesAvailable = mByteQueue.getBytesAvailable();
        int bytesToRead = Math.min(bytesAvailable, mReceiveBuffer.length);
        try {
            int bytesRead = mByteQueue.read(mReceiveBuffer, 0, bytesToRead);
            String valueStr = new String(mReceiveBuffer);
            mParent.processString(valueStr.substring(0, bytesRead));
            Log.d("TERM", "readFromProcess END");
        } catch (InterruptedException e) {
        }
    }

    private void onProcessExit(int result) {
    	//CCX do something interesting here
    	finish();
    }

    public void finish() {
        Exec.hangupProcessGroup(mProcId);
        Exec.close(mTermFd);
        mIsRunning = false;
    }
}

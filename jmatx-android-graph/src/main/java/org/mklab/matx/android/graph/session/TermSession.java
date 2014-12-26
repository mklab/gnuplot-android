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
            if (!TermSession.this.mIsRunning) {
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
        
        this.mParent = inParent;

        createSubprocess(processId);
        this.mProcId = processId[0];
        this.mTermOut = new FileOutputStream(this.mTermFd);
        this.mTermIn = new FileInputStream(this.mTermFd);

        this.mWatcherThread = new Thread() {
             @Override
             public void run() {
                Log.i("term", "waiting for: " + TermSession.this.mProcId);
                int result = Exec.waitFor(TermSession.this.mProcId);
                Log.i("term", "Subprocess exited: " + result);
                TermSession.this.mMsgHandler.sendMessage(TermSession.this.mMsgHandler.obtainMessage(PROCESS_EXITED, result));
                Log.d("TERM", "RUN END");
             }
        };
        this.mWatcherThread.setName("Process watcher");

        this.mWriteCharBuffer = CharBuffer.allocate(2);
        this.mWriteByteBuffer = ByteBuffer.allocate(4);
        this.mUTF8Encoder = Charset.forName("UTF-8").newEncoder();
        this.mUTF8Encoder.onMalformedInput(CodingErrorAction.REPLACE);
        this.mUTF8Encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        this.mReceiveBuffer = new byte[4 * 1024];
        this.mByteQueue = new ByteQueue(4 * 1024);

        this.mPollingThread = new Thread() {
            private byte[] mBuffer = new byte[4096];

            @Override
            public void run() {
                try {
                    while(true) {
                        int read = TermSession.this.mTermIn.read(this.mBuffer);
                        if (read == -1) {
                            // EOF -- process exited
                            return;
                        }
                        TermSession.this.mByteQueue.write(this.mBuffer, 0, read);
                        TermSession.this.mMsgHandler.sendMessage(
                                TermSession.this.mMsgHandler.obtainMessage(NEW_INPUT));
                    }
                } catch (IOException e) {
                } catch (InterruptedException e) {
                }
                Log.d("TERM", "TREAD END");
            }
        };
        this.mPollingThread.setName("Input reader");
        this.mWaitThread = new Thread(){
        	@Override
        	public void run() {
        		try {
        			Log.d("TERM", "WAIT START");
					sleep(1000);
					Log.d("TERM", "WAIT END");
					TermSession.this.mParent.sendBitmap();
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
        this.mIsRunning = true;
        this.mWatcherThread.start();
        this.mPollingThread.start();
        this.mWaitThread.start();
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
            this.mTermOut.write(data.getBytes("UTF-8"));
            this.mTermOut.flush();
        } catch (IOException e) {
            // Ignore exception
            // We don't really care if the receiver isn't listening.
            // We just make a best effort to answer the query.
        }
    }

    public void write(int codePoint) {
        CharBuffer charBuf = this.mWriteCharBuffer;
        ByteBuffer byteBuf = this.mWriteByteBuffer;
        CharsetEncoder encoder = this.mUTF8Encoder;
        try {
            charBuf.clear();
            byteBuf.clear();
            Character.toChars(codePoint, charBuf.array(), 0);
            encoder.reset();
            encoder.encode(charBuf, byteBuf, true);
            encoder.flush(byteBuf);
            this.mTermOut.write(byteBuf.array(), 0, byteBuf.position()-1);
            this.mTermOut.flush();
        } catch (IOException e) {
            // Ignore exception
        }
    }

    private void createSubprocess(int[] processId) {
        this.mTermFd = Exec.createSubprocess(processId);
    }

    public FileOutputStream getTermOut() {
        return this.mTermOut;
    }

    public void updateSize(int columns, int rows) {
        // Inform the attached pty of our new size:
        Exec.setPtyWindowSize(this.mTermFd, rows, columns, 0, 0);
        if (this.mIsRunning == false) {
        	initializeEmulator();
        }
    }

    /**
     * Look for new input from the ptty, send it to the terminal emulator.
     */
    private void readFromProcess() {
        int bytesAvailable = this.mByteQueue.getBytesAvailable();
        int bytesToRead = Math.min(bytesAvailable, this.mReceiveBuffer.length);
        try {
            int bytesRead = this.mByteQueue.read(this.mReceiveBuffer, 0, bytesToRead);
            String valueStr = new String(this.mReceiveBuffer);
            this.mParent.processString(valueStr.substring(0, bytesRead));
            Log.d("TERM", "readFromProcess END");
        } catch (InterruptedException e) {
        }
    }

    private void onProcessExit(int result) {
    	//CCX do something interesting here
    	finish();
    }

    public void finish() {
        Exec.hangupProcessGroup(this.mProcId);
        Exec.close(this.mTermFd);
        this.mIsRunning = false;
    }
}

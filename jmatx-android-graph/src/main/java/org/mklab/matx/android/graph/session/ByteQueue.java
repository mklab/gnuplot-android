/*
* Copyright (C) 2007 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.mklab.matx.android.graph.session;
/**
* A multi-thread-safe produce-consumer byte array.
* Only allows one producer and one consumer.
*/

public class ByteQueue {
    public ByteQueue(int size) {
        this.mBuffer = new byte[size];
    }

    public int getBytesAvailable() {
        synchronized(this) {
            return this.mStoredBytes;
        }
    }

    public int read(byte[] buffer, int offset, int length)
        throws InterruptedException {
        if (length + offset > buffer.length) {
            throw
                new IllegalArgumentException("length + offset > buffer.length");
        }
        if (length < 0) {
            throw
            new IllegalArgumentException("length < 0");

        }
        if (length == 0) {
            return 0;
        }
        synchronized(this) {
            while (this.mStoredBytes == 0) {
                wait();
            }
            int totalRead = 0;
            int bufferLength = this.mBuffer.length;
            boolean wasFull = bufferLength == this.mStoredBytes;
            while (length > 0 && this.mStoredBytes > 0) {
                int oneRun = Math.min(bufferLength - this.mHead, this.mStoredBytes);
                int bytesToCopy = Math.min(length, oneRun);
                System.arraycopy(this.mBuffer, this.mHead, buffer, offset, bytesToCopy);
                this.mHead += bytesToCopy;
                if (this.mHead >= bufferLength) {
                    this.mHead = 0;
                }
                this.mStoredBytes -= bytesToCopy;
                length -= bytesToCopy;
                offset += bytesToCopy;
                totalRead += bytesToCopy;
            }
            if (wasFull) {
                notify();
            }
            return totalRead;
        }
    }

    public void write(byte[] buffer, int offset, int length)
    throws InterruptedException {
        if (length + offset > buffer.length) {
            throw
                new IllegalArgumentException("length + offset > buffer.length");
        }
        if (length < 0) {
            throw
            new IllegalArgumentException("length < 0");

        }
        if (length == 0) {
            return;
        }
        synchronized(this) {
            int bufferLength = this.mBuffer.length;
            boolean wasEmpty = this.mStoredBytes == 0;
            while (length > 0) {
                while(bufferLength == this.mStoredBytes) {
                    wait();
                }
                int tail = this.mHead + this.mStoredBytes;
                int oneRun;
                if (tail >= bufferLength) {
                    tail = tail - bufferLength;
                    oneRun = this.mHead - tail;
                } else {
                    oneRun = bufferLength - tail;
                }
                int bytesToCopy = Math.min(oneRun, length);
                System.arraycopy(buffer, offset, this.mBuffer, tail, bytesToCopy);
                offset += bytesToCopy;
                this.mStoredBytes += bytesToCopy;
                length -= bytesToCopy;
            }
            if (wasEmpty) {
                notify();
            }
        }
    }

    private byte[] mBuffer;
    private int mHead;
    private int mStoredBytes;
}
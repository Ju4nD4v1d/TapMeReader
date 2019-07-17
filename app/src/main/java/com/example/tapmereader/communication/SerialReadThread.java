package com.example.tapmereader.communication;

import android.os.SystemClock;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class SerialReadThread extends Thread {

    private BufferedInputStream inputStream;

    SerialReadThread(InputStream is) {
        inputStream = new BufferedInputStream(is);
    }

    @Override
    public void run() {
        byte[] received = new byte[16];
        int size;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                int available = inputStream.available();

                if (available > 0) {
                    size = inputStream.read(received);
                    if (size > 0) {
                        onDataReceive(received, size);
                    }
                } else {
                    SystemClock.sleep(1);
                }
            } catch (IOException e) {
                // TODO: what to do when card wasn't read
            }
        }
    }

    private void onDataReceive(byte[] received, int size) {
        // TODO: this is the cardId ->  new String(received, 1, size - 2)
        try {
            System.out.println(new String(received, 1, size - 2, "UTF-8"));
            System.out.println(new String(received, 1, size - 2));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void close() {

        try {
            inputStream.close();
        } catch (IOException e) {
            // TODO: what to do when serial port couldn't be closed
        } finally {
            super.interrupt();
        }
    }
}

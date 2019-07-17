package com.example.tapmereader.communication;

import com.example.serialport.SerialPort;

import java.io.File;


public class SerialPortManager {

    private SerialReadThread readThread;
    private SerialPort serialPort;

    private static final String DEVICE_PATH = "/dev/ttyO0";
    private static final String BAUD_RATE = "9600";

    private static class InstanceHolder {
        static SerialPortManager manager = new SerialPortManager();
    }

    public static SerialPortManager instance() {
        return InstanceHolder.manager;
    }

    private SerialPortManager() {
        SerialPort.setSuPath("/system/xbin/su");
    }

    public SerialPort open() {
        try {
            serialPort = new SerialPort(new File(DEVICE_PATH), Integer.parseInt(BAUD_RATE), 0);

            readThread = new SerialReadThread(serialPort.getInputStream());
            readThread.start();

            return serialPort;
        } catch (Throwable tr) {
            // TODO: what to do when couldn't open the serial port
            close();
            return null;
        }
    }

    public void close() {
        if (readThread != null) {
            readThread.interrupt();
            readThread.close();
        }

        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }
}
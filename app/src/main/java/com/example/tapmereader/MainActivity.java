package com.example.tapmereader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tapmereader.communication.SerialPortManager;

public class MainActivity extends AppCompatActivity {

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isOpen = SerialPortManager.instance().open() != null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isOpen) {
            SerialPortManager.instance().close();
            isOpen = false;
        }
    }
}

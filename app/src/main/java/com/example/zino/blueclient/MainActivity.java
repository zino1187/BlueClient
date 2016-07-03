package com.example.zino.blueclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    BroadcastReceiver broadcastReceiver;
    BluetoothDevice device;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                device=(BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Toast.makeText(getApplicationContext(), device.getName()+" 발견", Toast.LENGTH_SHORT).show();
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);

    }

    public void btnClick(View view){
        if(view.getId() == R.id.bt_scan){
            bluetoothAdapter.startDiscovery();
        }else if(view.getId() == R.id.bt_connect){
            ClientThread clientThread = new ClientThread(bluetoothAdapter, device);
            clientThread.start();
        }
    }

}

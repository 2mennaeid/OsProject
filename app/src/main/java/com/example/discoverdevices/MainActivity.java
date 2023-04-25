package com.example.discoverdevices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter myadapter;
    Button button;
    ListView list_View;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        list_View = (ListView) findViewById(R.id.listview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String permission = Manifest.permission.READ_CONTACTS;
                int grantResult = ContextCompat.checkSelfPermission(getApplicationContext(), permission);

                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, do something
                    myadapter.startDiscovery();
                }

            }
        });
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(myReceiver,intentFilter);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,stringArrayList);
        list_View.setAdapter(arrayAdapter);

    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String permission = Manifest.permission.READ_CONTACTS;
                int grantResult = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted, do something
                    stringArrayList.add(device.getName());
                    arrayAdapter.notifyDataSetChanged();
                }


            }
        }
    };
}
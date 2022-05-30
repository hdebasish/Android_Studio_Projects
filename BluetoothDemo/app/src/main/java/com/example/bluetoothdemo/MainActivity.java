package com.example.bluetoothdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter BA;
    Button turnOnBT;
    Button findDevices;
    Button showPairedDevices;
    ListView listView;
    ArrayList pairedDeviceArrayList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnOnBT=findViewById(R.id.turnOnBT);
        findDevices=findViewById(R.id.findDevices);
        showPairedDevices=findViewById(R.id.showPairedDevices);
        listView=findViewById(R.id.listView);
        turnOnBT.setOnClickListener(onClickListener);
        findDevices.setOnClickListener(onClickListener);
        showPairedDevices.setOnClickListener(onClickListener);
        BA=BluetoothAdapter.getDefaultAdapter();

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.turnOnBT:
                    if (BA.isEnabled()){
                        BA.disable();
                        turnOnBT.setText(R.string.turn_on_bluetooth);
                        Toast.makeText(getApplicationContext(),"Bluetooth is OFF!",Toast.LENGTH_LONG).show();
                    }else {
                        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivity(i);
                        turnOnBT.setText(R.string.turn_off_bluetooth);
                    }
                    break;
                case R.id.findDevices:
                    int ACTION_REQUEST_DISCOVERABLE = 1;
                    Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(i,ACTION_REQUEST_DISCOVERABLE);
                    break;
                case R.id.showPairedDevices:
                    Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
                    pairedDeviceArrayList = new ArrayList<String>();
                    for(BluetoothDevice device:pairedDevices){
                        pairedDeviceArrayList.add(device.getName());
                    }
                    adapter = new  ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,pairedDeviceArrayList);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (BA.isEnabled()){
                turnOnBT.setText(R.string.turn_off_bluetooth);
            }
        }
    }
}

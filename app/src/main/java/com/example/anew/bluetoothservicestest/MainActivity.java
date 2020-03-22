package com.example.anew.bluetoothservicestest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    BluetoothAdapter bAdapter;
    Switch s1;
    ArrayList<String> list;
    ArrayAdapter<String> adap;
    ListView listView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1=findViewById(R.id.s1);
        listView=findViewById(R.id.lv1);
        bAdapter=BluetoothAdapter.getDefaultAdapter();
       s1.setChecked(bAdapter.isEnabled());
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(s1.isChecked())
                {
                    bAdapter.enable();
                }
                else
                {
                    bAdapter.disable();
                }

            }
        });
        list=new ArrayList<>();
      adap=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        listView.setAdapter(adap);
    }

    public void getBtDevices(View view)
    {
        bAdapter.startDiscovery();
        IntentFilter inf=new IntentFilter();
        inf.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(new BroadcastReceiver() 
        {
            @Override
            public void onReceive(Context context, Intent intent) 
            {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Toast.makeText(MainActivity.this, device.getName()+"\n"+device.getAddress(),Toast.LENGTH_LONG).show();
                list.add(device.getName()+"\n"+device.getAddress());
                adap.notifyDataSetChanged();
            }
        },inf);
    }
}

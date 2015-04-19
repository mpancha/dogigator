package net.osmand.plus.routing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.util.LogPrinter;
import java.util.Set;

public class BluetoothModule {

    private static final int REQUEST_ENABLE_BT = 1;
    private LogPrinter logPrinter;
    public BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothClientSocket;
    private OutputStream mmOutStream;
    private ArrayAdapter<String> mArrayAdapter;
    private UUID myUUID;
    private String myName;
    protected OutputStream getBtSender() {
            return mmOutStream;
    }
    protected OutputStream startModule() {
        logPrinter = new LogPrinter(4,"MITUL");
        logPrinter.println("BluetoothModule: startModule");

        //generate UUID on web: http://www.famkruithof.net/uuid/uuidgen
        //have to match the UUID on the another device of the BT connection
        myUUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
        myName = myUUID.toString();
       logPrinter.println("MainActivity:onStart:UUID generated");
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            logPrinter.println("BluetoothModule: not supported on this hardware platform");
            return null;
        }
        else
           logPrinter.println("BluetoothModule: startModule: getDefaultAdapter success");

        //Turn ON BlueTooth if it is OFF
        if (!bluetoothAdapter.isEnabled()) {
            if (bluetoothAdapter.enable() != true)
            {
               logPrinter.println("BluetoothModule: startModule: Enabling Bluetooth Failed!");
               return null;
            }
            //Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            logPrinter.println("BluetoothModule: startModule: Enabling Bluetooth now");
            //startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        logPrinter.println("MainActivity:onStart: BT turned ON");

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothDevice target_device=null;
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                logPrinter.println(device.getAddress());
                if(device.getAddress().equals("00:1E:0A:00:00:39") == true)
                { 
                    target_device = device;
                    logPrinter.println("Address matched");
                }
                else
                {
                    logPrinter.println("Dog harness not found");
                     return null;
                }
            }
        }

        BluetoothSocket tmp = null;
       // Get a BluetoothSocket to connect with the given BluetoothDevice

        try {
            // MY_UUID is the app's UUID string, also used by the server code
            //tmp1 = pairedDevices.toArray(new BluetoothDevice[pairedDevices.size()]);
            //tmp = tmp1[0].createRfcommSocketToServiceRecord(myUUID);
            tmp = target_device.createRfcommSocketToServiceRecord(myUUID);
        }
        catch (IOException e) {
            logPrinter.println("BluetoothModule:startModule: createRfcomm exception");
            return null;
        }
        bluetoothClientSocket = tmp;
        logPrinter.println("MainActivity:OnStart: bluetoothClientSocket created");

        OutputStream tmpOut = null;
        bluetoothAdapter.cancelDiscovery();
        logPrinter.println("BluetoothModule: startModule: cancelDiscovery done");
        try {
            bluetoothClientSocket.connect();
        } catch (IOException e) {
            //bluetoothClientSocket.close();
            //bluetoothClientSocket.close();
            logPrinter.println("BluetoothModule: startModule: ClientSocket connect failed!!");
        }

        logPrinter.println("MainActivity:OnStart:bluetoothClientSocket connected!!");

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpOut = bluetoothClientSocket.getOutputStream();
        } catch (IOException e) {logPrinter.println("MainActivity:onStart: getOutputStream exception"); }
        mmOutStream = tmpOut;

        logPrinter.println("MAinActivity:OnStart: All set");
        return mmOutStream;
    }

    private void send(byte[] bytesToSend)
    {
       try {
         mmOutStream.write(bytesToSend);
          logPrinter.println("MAinActivity:OnStart: send message");
       } catch (IOException e) {
          e.printStackTrace();
       }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                startModule();
            }else{
                 logPrinter.println("onActivityResult: BT not enabled");
            }
        }
    }

    public void cancel() {
        logPrinter.println("MAinActivity:cancel: close socket");
        try {
           bluetoothClientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
           e.printStackTrace();
        }
    }
}



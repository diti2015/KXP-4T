/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.example.android.BluetoothChat;

import java.util.ArrayList;
import java.util.Set;

import com.liqin.instrument.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class DeviceListActivityAuto extends Activity
{
	// Debugging
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = true;

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	// Member fields
	private BluetoothAdapter mBtAdapter;
	
	ArrayList<String> mNewDevicesArrayAdapter = new ArrayList<String>(0);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "AutoCreate()");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.device_connect);
		//doDiscovery();

		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		//mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
		//		R.layout.device_name);
		//mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
		//		R.layout.device_name);

		
		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		// If there are paired devices, add each one to the ArrayAdapter
		if (pairedDevices.size() > 0)
		{
			//findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
			for (BluetoothDevice device : pairedDevices)
			{
				 Log.d(TAG,'|'+ device.getName() + '|' + device.getAddress());
				/*
				mPairedDevicesArrayAdapter.add(device.getName() + "\n"
						+ device.getAddress());
				*/
			}
		}
		else
		{
			//没有找到设备
		}
	}
	
	@Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "AutoStart()");
        doDiscovery();
        
    }
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null)
		{
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery()
	{
		if (commond.D) Log.d(TAG, "doDiscovery()");

		// Indicate scanning in the title
		//setProgressBarIndeterminateVisibility(true);
		//setTitle(R.string.scanning);

		// Turn on sub-title for new devices
		//findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering())
		{
			mBtAdapter.cancelDiscovery();
		}

		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}

	// The on-click listener for all devices in the ListViews
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3)
		{
			// Cancel discovery because it's costly and we're about to connect
			mBtAdapter.cancelDiscovery();

			// Get the device MAC address, which is the last 17 chars in the
			// View
			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);

			// Create the result Intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};
	private void Manual(){
		Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, 1);
		
	}
	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action))
			{
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed
				// already
				//if (device.getBondState() != BluetoothDevice.BOND_BONDED)
				//{
				if (commond.D)Log.d(TAG, device.getName() + '|' + device.getAddress());
					if(!(device.getName()==null)){
						if(device.getName().length()>4&&device.getName().substring(0,3).equals("430")){
							mBtAdapter.cancelDiscovery();
						}
					}
					
					
					mNewDevicesArrayAdapter.add( device.getName() + '-' + device.getAddress());
					/*
					mNewDevicesArrayAdapter.add(device.getName() + "\n"
							+ device.getAddress());
					*/	
				//}
				// When discovery is finished, change the Activity title
			}
			else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			{
				if (commond.D)Log.d(TAG,"aa"+mNewDevicesArrayAdapter);
				int matchNum = 0;
				int matchIndex = 0;
				for(int i=0;i<mNewDevicesArrayAdapter.size();i++){
					if(mNewDevicesArrayAdapter.get(i).substring(0,3).equals("430")){
						matchNum++;
						matchIndex=i;
					}
				}
				if(matchNum==1){
					String[] info = mNewDevicesArrayAdapter.get(matchIndex).split("-");
					int cc = info.length-1;
					String address = info[cc];
					if (commond.D)Log.d(TAG,address);
					// Create the result Intent and include the MAC address
					Intent intent2 = new Intent();
					intent2.putExtra(EXTRA_DEVICE_ADDRESS, address);
					
					// Set result and finish this Activity
					DeviceListActivityAuto.this.setResult(Activity.RESULT_OK, intent2);
					main.connectedDeviceName.setText("");
		        	main.conntedprogress.setVisibility(View.VISIBLE);
					finish();
				}
				else {
					commond.setToast(getApplication().getString(R.string.device_autosearchfailed));
					//Toast.makeText(commond.activeContext, "没有匹配到合适的设备", Toast.LENGTH_LONG).show();
					commond.closeSocket();
					Manual();
					finish();
				}
				/*
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.select_device);
				if (mNewDevicesArrayAdapter.getCount() == 0)
				{
					String noDevices = getResources().getText(
							R.string.none_found).toString();
					mNewDevicesArrayAdapter.add(noDevices);
				}
				*/
			}
		}
	};

}

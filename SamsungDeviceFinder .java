import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class SamsungDeviceFinder {

    private static final String TAG = SamsungDeviceFinder.class.getSimpleName();
    private static final String SAMSUNG_MAC_PREFIX = "DC:CB:A8"; // Samsung MAC address prefix

    private Context context;

    public SamsungDeviceFinder(Context context) {
        this.context = context;
    }

    public void findDevice(DeviceFinderCallback callback) {
        new DeviceFinderTask(callback).execute();
    }

    public interface DeviceFinderCallback {
        void onDeviceFound(String ipAddress);
        void onDeviceNotFound();
    }

    private class DeviceFinderTask extends AsyncTask<Void, Void, String> {

        private DeviceFinderCallback callback;

        public DeviceFinderTask(DeviceFinderCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Get the device's Wi-Fi MAC address
                String wifiMacAddress = getWifiMacAddress();
                if (wifiMacAddress == null) {
                    Log.e(TAG, "Failed to obtain Wi-Fi MAC address");
                    return null;
                }

                // Extract the first 3 octets of the MAC address
                String macPrefix = wifiMacAddress.substring(0, 8);

               

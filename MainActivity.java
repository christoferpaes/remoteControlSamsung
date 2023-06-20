import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.samsung.multiscreen.Service;
import com.samsung.multiscreen.ServiceSearch;
import com.samsung.multiscreen.ServiceSearch.OnServiceFoundListener;

import androidx.appcompat.app.AppCompatActivity;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.remoteservice.RemoteDevice;
import com.samsung.android.sdk.remoteservice.RemoteService;
import com.samsung.android.sdk.remoteservice.RemoteServiceListener;
import com.samsung.android.sdk.remoteservice.RemoteServiceManager;
import com.samsung.android.sdk.remoteservice.device.RemoteControlManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DEVICE_MODEL = "Samsung";

    private Button volumeUpButton;
    private Button volumeDownButton;
    private Button[] numberButtons;
    private Button muteButton;
    private Button menuButton;
    private Button searchButton;
    private Button fastForwardButton;
    private Button pauseButton;
    private Button rewindButton;
    private Button playButton;

    // Add more buttons as needed for other remote control actions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volumeUpButton = findViewById(R.id.volumeUpButton);
        volumeDownButton = findViewById(R.id.volumeDownButton);
        muteButton = findViewById(R.id.muteButton);
        menuButton = findViewById(R.id.menuButton);
        searchButton = findViewById(R.id.searchButton);
        fastForwardButton = findViewById(R.id.fastForwardButton);
        pauseButton = findViewById(R.id.pauseButton);
        rewindButton = findViewById(R.id.rewindButton);
        playButton = findViewById(R.id.playButton);
        SamsungSmartViewSDK.init(this);
        // Connect to the Samsung Smart TV
remoteService = new RemoteService(getApplicationContext(), new RemoteServiceListener() {
    @Override
    public void onServiceDisconnected(int errorCode) {
        Log.e(TAG, "RemoteService disconnected with error code: " + errorCode);
    }

    @Override
    public void onServiceConnected(RemoteServiceManager serviceManager) {
        remoteServiceManager = serviceManager;
        initializeRemoteControlManager();
    }
});

// Obtain the TV IP address by recognizing a Samsung device on the network
SamsungDeviceFinder samsungDeviceFinder = new SamsungDeviceFinder(getApplicationContext());
samsungDeviceFinder.findDevice(new SamsungDeviceFinder.DeviceFinderCallback() {
    @Override
    public void onDeviceFound(String ipAddress) {
        // Connect to the Samsung Smart TV using the obtained IP address
        RemoteDevice device = new RemoteDevice();
        device.setIp(ipAddress);
        remoteControlManager.connect(device);
    }

    @Override
    public void onDeviceNotFound() {
        // Handle the case when no Samsung device is found on the network
        Log.e(TAG, "No Samsung device found on the network");
    }
});

        // Set click listeners for the remote control buttons
        volumeUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVolumeUp();
            }
        });

        volumeDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVolumeDown();
            }
        });

        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMute();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });

        fastForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFastForward();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPause();
            }
        });

        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRewind();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlay();
            }
        });

        // Set click listeners for number buttons
        numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            int buttonId = getResources().getIdentifier("numberButton" + i, "id", getPackageName());
            numberButtons[i] = findViewById(buttonId);
            final int number = i;
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendNumber(number);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            remoteService.initialize();
        } catch (SsdkUnsupportedException e) {
            Log.e(TAG, "Failed to initialize the RemoteService: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteServiceManager.disconnect();
    }

    private void initializeRemoteControlManager() {
        remoteControlManager = remoteServiceManager.getRemoteControlManager();
    }

    // Volume Up
    private void setVolumeUp() {
        remoteControlManager.setVolumeUp(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Volume Up command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Volume Up command. Error code: " + errorCode);
            }
        });
    }

    // Volume Down
    private void setVolumeDown() {
        remoteControlManager.setVolumeDown(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Volume Down command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Volume Down command. Error code: " + errorCode);
            }
        });
    }

    // Mute
    private void setMute() {
        remoteControlManager.setMute(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Mute command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Mute command. Error code: " + errorCode);
            }
        });
    }

    // Menu
    private void openMenu() {
        remoteControlManager.openMenu(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Menu command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Menu command. Error code: " + errorCode);
            }
        });
    }

    // Search
    private void openSearch() {
        remoteControlManager.openSearch(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Search command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Search command. Error code: " + errorCode);
            }
        });
    }

    // Fast Forward
    private void setFastForward() {
        remoteControlManager.setFastForward(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Fast Forward command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Fast Forward command. Error code: " + errorCode);
            }
        });
    }

    // Pause
    private void setPause() {
        remoteControlManager.setPause(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Pause command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Pause command. Error code: " + errorCode);
            }
        });
    }

    // Rewind
    private void setRewind() {
        remoteControlManager.setRewind(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Rewind command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Rewind command. Error code: " + errorCode);
            }
        });
    }

    // Play
    private void setPlay() {
        remoteControlManager.setPlay(new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Play command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Play command. Error code: " + errorCode);
            }
        });
    }

    // Send number
    private void sendNumber(int number) {
        remoteControlManager.sendNumber(number, new RemoteControlManager.ResultCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Number " + number + " command sent successfully");
            }

            @Override
            public void onError(int errorCode) {
                Log.e(TAG, "Failed to send Number " + number + " command. Error code: " + errorCode);
            }
        });
    }
}

        searchSamsungDevice();
    }

    private void searchSamsungDevice() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    if (connectivityManager != null) {
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

                if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (wifiManager != null) {
                        String connectedSSID = wifiManager.getConnectionInfo().getSSID();

                        if (connectedSSID != null && connectedSSID.contains("Samsung")) {
                            ServiceSearch serviceSearch = Service.search(MainActivity.this);
                            serviceSearch.setOnServiceFoundListener(new OnServiceFoundListener() {
                                @Override
                                public void onFound(List<Service> services) {
                                    for (Service service : services) {
                                        if (service.getModel().contains(DEVICE_MODEL)) {
                                            String tvIpAddress = service.getIp();
                                            Log.d(TAG, "Found Samsung Smart TV at IP address: " + tvIpAddress);
                                            // Connect to the Samsung Smart TV and initialize remote control
                                            connectToSamsungTV(tvIpAddress);
                                            break;
                                        }
                                    }
                                }
                            });
                            serviceSearch.start();
                        }
                    }
                }
            }
        });
        }
    }

    private void connectToSamsungTV(String tvIpAddress) {
        remoteService = new RemoteService(getApplicationContext(), new RemoteServiceListener() {
            @Override
            public void onServiceDisconnected(int errorCode) {
                Log.e(TAG, "RemoteService disconnected with error code: " + errorCode);
            }

            @Override
            public void onServiceConnected(RemoteServiceManager serviceManager) {
                remoteServiceManager = serviceManager;
                initializeRemoteControlManager();
            }
        });

        RemoteDevice device = new RemoteDevice();
        device.setIp(tvIpAddress);
        remoteServiceManager.connect(device);
    }

    private void initializeRemoteControlManager() {
        remoteControlManager = remoteServiceManager.getRemoteControlManager();

        // Set click listeners for the remote control buttons
        volumeUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVolumeUp();
            }
        });

        volumeDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVolumeDown();
            }
        });

        // Set click listeners for other remote control buttons
        // ...
    }

    private void setVolumeUp() {
        if (remoteControlManager != null) {
            remoteControlManager.sendKeyEvent(RemoteControlManager.KEYCODE_VOLUME_UP);
        }
    }

    private void setVolumeDown() {
        if (remoteControlManager != null) {
            remoteControlManager.sendKeyEvent(RemoteControlManager.KEYCODE_VOLUME_DOWN);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Disconnect from the Samsung Smart TV and release any resources
    }
}

package com.samsung.android.sdk.remoteservice;

import com.samsung.android.sdk.remoteservice.device.RemoteControlManager;

public class RemoteServiceManager {

    private RemoteControlManager remoteControlManager;

    public RemoteServiceManager() {
        // Initialize the remote control manager
        remoteControlManager = new RemoteControlManager();
    }

    public RemoteControlManager getRemoteControlManager() {
        return remoteControlManager;
    }

    public void disconnect() {
        // Disconnect logic for the RemoteServiceManager
        // ...
    }
}

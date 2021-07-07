package com.kovospace.scrap.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import com.kovospace.scrap.helpers.Settings;

public class Connection {
    public static final int CONNECTION_OFFLINE = 1;
    public static final int CONNECTION_ONLINE = 2;
    private Context context;
    private int currentConnection;
    private int newConnection;
    private boolean connectionChanged;

    public Connection(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        this.currentConnection = 0;
        this.newConnection = 0;
    }

    private Boolean getStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network nw = connectivityManager.getActiveNetwork();
        if (nw == null) return false;
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return (actNw != null)
                && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
    }


    public int getConnectionMethod() {
        if (getStatus() && Settings.getAllowConnection()) {
            newConnection = CONNECTION_ONLINE;
        } else {
            newConnection = CONNECTION_OFFLINE;
        }
        connectionChanged = newConnection != currentConnection;
        currentConnection = newConnection;
        return newConnection;
    }

    public boolean isConnectionChanged() {
        return connectionChanged;
    }

    public boolean isConnectionAvailable() {
        return currentConnection == CONNECTION_ONLINE;
    }

}
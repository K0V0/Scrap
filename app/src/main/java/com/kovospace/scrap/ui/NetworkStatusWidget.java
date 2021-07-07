package com.kovospace.scrap.ui;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageButton;
import com.kovospace.scrap.R;
import com.kovospace.scrap.helpers.Connection;

public class NetworkStatusWidget
{
  private Context context;
  private ImageButton networkStatusButton;
  private Connection connection;

  public NetworkStatusWidget(Context context) {
    this.context = context;
    this.init();
  }

  private void init() {
    this.connection = new Connection(context);
    connection.getConnectionMethod();
    this.networkStatusButton = ((Activity) context).findViewById(R.id.networkStatusButton);
    this.networkStatusButton.setOnClickListener(new MainMenuPopup(context));
    this.changeStatusIcon();
  }

  private void changeStatusIcon() {
    int iconPath = connection.isConnectionAvailable() ? R.mipmap.net_ok_foreground : R.mipmap.no_net_foreground;
    this.networkStatusButton.setImageResource(iconPath);
  }

  public void refresh() {
    this.changeStatusIcon();
  }
}

package com.kovospace.scrap.bands;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import com.kovospace.scrap.appBase.Activity;
import com.kovospace.scrap.R;
import com.kovospace.scrap.bands.ui.NetworkStatusWidget;
import com.kovospace.scrap.bands.ui.PlayerWidget;
import com.kovospace.scrap.bands.ui.SearchField;

public  class BandsActivity
        extends Activity
{
    private SearchField searchField;
    private PlayerWidget playerWidget;
    private NetworkStatusWidget networkStatusWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.networkStatusWidget = new NetworkStatusWidget(this);
        this.searchField = new SearchField(this);
        this.playerWidget = new PlayerWidget(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (super.connectionTest.isConnectionChanged()) {
            this.refreshActivity();
        }
        this.searchField.onResumeChecks();
        this.playerWidget.check();
    }

    @Override
    protected void onNetworkChanged() {
        this.refreshActivity();
    }

    @Override
    public void onUserInteraction() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void refreshActivity() {
        Intent intent = new Intent(this, BandsActivity.class);
        String extra = this.searchField.getSearchFieldText();
        if (extra.length() > 0) {
            intent.putExtra("searchString", extra);
        }
        startActivity(intent);
        finish();
    }
}
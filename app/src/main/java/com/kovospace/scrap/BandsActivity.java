package com.kovospace.scrap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import com.kovospace.scrap.mainActivityClasses.BandsSearchService;
import com.kovospace.scrap.ui.NetworkStatusWidget;
import com.kovospace.scrap.ui.PlayerWidget;

public  class BandsActivity
        extends Activity
{
    private BandsSearchService bandsSearchService;
    private PlayerWidget playerWidget;
    private NetworkStatusWidget networkStatusWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.networkStatusWidget = new NetworkStatusWidget(this);
        this.bandsSearchService = new BandsSearchService(BandsActivity.this, this);
        this.playerWidget = new PlayerWidget(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (super.connectionTest.isConnectionChanged()) {
            this.refreshActivity();
        }
        this.bandsSearchService.onResumeChecks();
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
        String extra = this.bandsSearchService.getSearchFieldText();
        if (extra.length() > 0) {
            intent.putExtra("searchString", extra);
        }
        startActivity(intent);
        finish();
    }
}
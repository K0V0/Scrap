package com.kovospace.scrap.bands.ui;

import android.content.Context;
import android.widget.EditText;
import com.kovospace.scrap.R;
import com.kovospace.scrap.bands.BandsActivity;
import com.kovospace.scrap.bands.BandsService;
import com.kovospace.scrap.utils.Connection;
import com.kovospace.scrap.helpers.OnFinishTypingHelper;
import com.kovospace.scrap.helpers.SearchFieldProgress;
import java.util.Optional;

public  class SearchField
        extends OnFinishTypingHelper
{
    private BandsService bandsService;
    private Connection connection;
    private final Context context;
    private BandsActivity bandsActivity;
    private EditText bandSearchField;
    private boolean pauseSearch;

    public SearchField(Context context) {
        super();
        this.context = context;
        init();
    }

    private void init() {
        this.bandsService = new BandsService(context);
        this.connection = new Connection(context);
        this.bandsActivity = (BandsActivity) context;
        this.bandSearchField = this.bandsActivity.findViewById(R.id.bandInput);
        this.bandSearchField.addTextChangedListener(watchText());
        Optional<String> searchString = Optional
            .ofNullable(this.bandsActivity.getIntent().getStringExtra("searchString"));
        SearchFieldProgress.init(context);
        if (searchString.isPresent()) {
            String ss = searchString.get();
            pauseTextListenerOnce();
            bandSearchField.setText(ss);
            this.bandsService.search(ss);
        } else {
            this.bandsService.search("");
        }
    }

    @Override
    public void doStuffNotOften() {
        if (getText().length() > 0) {
            if (!pauseSearch) {
                this.bandsService.search(super.getText());
            } else {
                pauseSearch = false;
            }
        }
    }

    @Override
    public void doStuffOnZero() {
        if (!pauseSearch) {
            this.bandsService.search("");
        } else {
            pauseSearch = false;
        }
    }

    /*private void chooseDataSource() {
        connection.getConnectionMethod();
        if (connection.isConnectionChanged()) {
            if (connection.isConnectionAvailable()) {
                //this.bandsWrapper = new BandsWrapperNet(bandsActivity, context, connection);
            } else {
                //this.bandsWrapper = new BandsWrapperOffline(bandsActivity, context);
            }
        }
    }*/

    public void pauseTextListenerOnce() {
        this.pauseSearch = true;
    }

    /*public void resumeTextListener() {
        this.pauseSearch = false;
    }*/

    public String getSearchFieldText() {
        return this.bandSearchField.getText().toString();
    }

    public void onResumeChecks() {
        //bandsWrapper.onResumeChecks();
    }
}

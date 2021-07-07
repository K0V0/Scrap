package com.kovospace.scrap.mainActivityClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.EditText;
import com.kovospace.scrap.BandsActivity;
import com.kovospace.scrap.R;
import com.kovospace.scrap.helpers.Connection;
import com.kovospace.scrap.helpers.OnFinishTypingHelper;
import com.kovospace.scrap.helpers.SearchFieldProgress;
import java.util.Optional;

public class BandsSearchService extends OnFinishTypingHelper {

    private EditText bandSearchField;
    private Optional<String> searchString;


    private Connection conectionTester;
    private BandsWrapper bandsWrapper;
    private Context context;
    private BandsActivity bandsActivity;
    private boolean pauseSearch;

    public BandsSearchService(BandsActivity bandsActivity, Context context) {
        super();
        this.bandsActivity = bandsActivity;
        this.context = context;
        this.conectionTester = new Connection(this.context);
        init();
        search("");
    }

    @SuppressLint("NewApi")
    private void init() {
        this.conectionTester = new Connection(this.context);
        this.bandSearchField = this.bandsActivity.findViewById(R.id.bandInput);
        this.bandSearchField.addTextChangedListener(watchText());
        this.searchString = Optional.ofNullable(this.bandsActivity.getIntent().getStringExtra("searchString"));
        searchString.ifPresent(ss -> {
            pauseTextListenerOnce();
            bandSearchField.setText(ss);
            search(ss);
        });
        SearchFieldProgress.init(context);
    }

    private void decideWrapperOnConnection() {
        conectionTester.getConnectionMethod();
        if (conectionTester.isConnectionChanged()) {
            if (conectionTester.isConnectionAvailable()) {
                this.bandsWrapper = new BandsWrapperNet(bandsActivity, context, conectionTester);
            } else {
                this.bandsWrapper = new BandsWrapperOffline(bandsActivity, context);
            }
        }
    }

    public void search(String search) {
        decideWrapperOnConnection();
        bandsWrapper.search(search);
    }

    public void pauseTextListenerOnce() {
        this.pauseSearch = true;
    }

    public void resumeTextListener() {
        this.pauseSearch = false;
    }

    @Override
    public void doStuffNotOften() {
        if (getText().length() > 0) {
            if (!pauseSearch) {
                search(getText());
            } else {
                pauseSearch = false;
            }
        }
    }

    @Override
    public void doStuffOnZero() {
        if (!pauseSearch) {
            search("");
        } else {
            pauseSearch = false;
        }
    }

    public String getSearchFieldText() {
        return this.bandSearchField.getText().toString();
    }

    public void onResumeChecks() {
        bandsWrapper.onResumeChecks();
    }

}

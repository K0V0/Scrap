package com.kovospace.scrap.helpers;

import android.app.Activity;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kovospace.scrap.R;
import com.kovospace.scrap.appBase.ui.ToastMessage;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonRequest
{
    protected Activity activity;
    protected String query;
    protected JSONObject responseData;
    protected ToastMessage toastMessage;

    public JsonRequest(Context context) {
        this(context, "");
    }

    public JsonRequest(Context context, String query) {
        this.query = query;
        this.activity = (Activity) context;
        this.init();
    }

    private void init() {
        this.toastMessage = new ToastMessage(this.activity);
        this.requestData();
    }

    public abstract void doStuff(JSONObject responseData);

    private String escape(String query) {
       return query.replace(" ", "%20");
    }

    private void requestData() {
        StringRequest request = new StringRequest(Request.Method.GET, escape(this.query), s -> {
            try {
                responseData = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            doStuff(responseData);
        }, volleyError -> toastMessage.send(R.string.noInternet));
        RequestQueue rQueue = Volley.newRequestQueue(activity);
        rQueue.add(request);
    }
}

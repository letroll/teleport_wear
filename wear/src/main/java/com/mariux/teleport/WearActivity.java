package com.mariux.teleport;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import de.greenrobot.event.EventBus;

public class WearActivity extends Activity {

    private static final String TAG = "WearActivity";
    private TeleportClient mTeleportClient;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient = new TeleportClient(this);
        mTeleportClient.connect();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
        EventBus.getDefault().unregister(this);
    }

    public void sendString(View v) {
        //Let's sync a Custom Object - how cool is that?
//        mTeleportClient.syncString("string", ((Button) v).getText().toString());
        mTeleportClient.syncString("string", "toto");
    }

    public void sendMessage(View v) {
        mTeleportClient.sendMessage(((Button) v).getText().toString(),null);
    }

    //For Message API receiving
    public void onEvent(final String messageContent) {
        if(mTextView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText("Received message: " + messageContent);
                }
            });
        }
    }

    //For DataItem API changes
    public void onEvent(DataMap dataMap) {
        final String string = dataMap.getString("string");
        if(mTextView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText("Received String from map: " + string);
                }
            });
        }
    }
}

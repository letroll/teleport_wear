package com.mariux.teleport;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class WearActivity extends Activity {
    private static final String TAG = "WearActivity";
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
        TeleportApp.eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TeleportApp.eventBus.unregister(this);
    }

    //Used by EventBus to pass event objects to our subscribed class
    public void onEvent(CustomObject event) {
        Log.d(TAG, "onEvent object: " + event);
        if(mTextView != null) {
            mTextView.setText("Received object: " + event.getName());
        }
    }

    public void onEvent(String event) {
        Log.d(TAG, "onEvent string: " + event);
        if(mTextView != null) {
            mTextView.setText("Received string: " + event);
        }
    }
}

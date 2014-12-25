package com.mariux.teleport;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import de.greenrobot.event.EventBus;

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    //For Message API receiving
    public void onEvent(final String event) {
        Log.d(TAG, "onEvent string: " + event);
        if(mTextView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText("Received string: " + event);
                }
            });
        }
    }

    //For DataItem API changes
    public void onEvent(DataMap dataMap) {
        Log.d(TAG, "onPostExecute map: " + dataMap);
        final CustomObject obj = new CustomObject(TeleportClient.byteToParcel(dataMap.getByteArray("byte")));
        Log.d(TAG, "onPostExecute object from map: " + obj);
        if(mTextView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText("Received object from map: " + obj.getName());
                }
            });
        }
    }
}

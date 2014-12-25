package com.mariux.teleport;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;
import com.mariux.teleport.lib.TeleportService;

/**
 * Created by Mario on 10/07/2014.
 */
public class WearService extends TeleportService{
    private static final String TAG = "WearService";


    @Override
    public void onCreate() {
        super.onCreate();
        //The quick way is to use setOnGetMessageTask, and set a new task
        setOnGetMessageTask(new StartActivityTask());

        setOnSyncDataItemTask(new StartObjectTask());

        //alternatively, you can use the Builder to create new Tasks
        /*
        setOnGetMessageTaskBuilder(new OnGetMessageTask.Builder() {
            @Override
            public OnGetMessageTask build() {
                return new OnGetMessageTask() {
                    @Override
                    protected void onPostExecute(String path) {
                        if (path.equals("startActivity")){

                            Intent startIntent = new Intent(getBaseContext(), WearActivity.class);
                            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startIntent);
                        }

                    }
                };
            }
        });
        */

    }

    //Task that shows the path of a received message
    public class StartActivityTask extends TeleportService.OnGetMessageTask {

        @Override
        protected void onPostExecute(String  path) {

       if (path.equals("startActivity")){

            Intent startIntent = new Intent(getBaseContext(), WearActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);
         }

            //let's reset the task (otherwise it will be executed only once)
            setOnGetMessageTask(new StartActivityTask());
        }
    }

    public class StartObjectTask extends OnSyncDataItemTask {

        @Override
        protected void onPostExecute(DataMap result) {
            CustomObject obj = new CustomObject(TeleportClient.byteToParcel(result.getByteArray("byte")));
            Log.d(TAG, "onPostExecute object: " + obj);
            
            //Pass object to EventBus to notify all subscribers
            TeleportApp.eventBus.post(obj);

            //let's reset the task (otherwise it will be executed only once)
            setOnSyncDataItemTask(new StartObjectTask());
        }
    }
}

package com.mariux.teleport;

import android.app.Application;

import de.greenrobot.event.EventBus;

/**
 * Created by michaltajchert on 25/12/14.
 */
public class TeleportApp extends Application {
    public static final EventBus eventBus = EventBus.getDefault();

}

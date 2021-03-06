package com.reindeercrafts.notificationpeek;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.reindeercrafts.notificationpeek.peek.NotificationPeek;
import com.reindeercrafts.notificationpeek.settings.PreferenceKeys;


/**
 * Notification listener service. Implemented onNotificationPosted
 * method so that whenever a notification is posted, it'll be saved
 * into database.
 * <p/>
 * Created by zhelu on 2/21/14.
 */
public class NotificationService extends NotificationListenerService {


    private String TAG = this.getClass().getSimpleName();

    private NotificationHub mNotificationHub;

    private NotificationPeek mNotificationPeek;


    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationHub = NotificationHub.getInstance();
        mNotificationPeek = new NotificationPeek(mNotificationHub, this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG, sbn.getPackageName() + " Notification received:" +
                sbn.getNotification().tickerText);

        Notification postedNotification = sbn.getNotification();

        String packageName = sbn.getPackageName();

        if (postedNotification.tickerText == null ||
                sbn.isOngoing() || !sbn.isClearable() ||
                isInBlackList(packageName)) {
            return;
        }

        mNotificationHub.addNotification(sbn);

        mNotificationHub.setCurrentNotification(sbn);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieve user specified peek timeout.
        int peekTimeoutMultiplier =
                Integer.parseInt(preferences.getString(PreferenceKeys.PREF_PEEK_TIMEOUT, "1"));

        // Does user select always listening?
        boolean alwaysListening =
                preferences.getBoolean(PreferenceKeys.PREF_ALWAYS_LISTENING, false);

        mNotificationPeek.showNotification(sbn, false, peekTimeoutMultiplier, alwaysListening);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        mNotificationHub.removeNotification(sbn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationPeek.unregisterScreenReceiver();
    }

    /**
     * Check if the given package name is in black list. There are some notifications that contain
     * ticker text or content, but they are actually not useful and not from user applications.
     *
     * @param packageName Given package name.
     * @return True if the package is in black list, false otherwise.
     */
    private boolean isInBlackList(String packageName) {
        return packageName.equals("com.android.systemui") || packageName.equals("android") ||
                packageName.contains("googlequicksearchbox");
    }


}

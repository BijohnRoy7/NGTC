package invenz.roy.ngtc.notification_utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import invenz.roy.ngtc.R;

public class MyNotificationManager {

    private Context mContext;
    private int NOTIFICATION_ID =1234;

    public MyNotificationManager(Context mContext) {
        this.mContext = mContext;
    }


    /*##########  showMyNotification##############*/
    public void showMyNotification(String from, String notification, Intent intent){

        /*######## create pending intent #########*/
        PendingIntent pendingIntent = PendingIntent.getActivity(
                mContext,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        /*####### Create Notification Builder #######*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);


        /*####### Showing Notification ########*/
        Notification myNotification = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notification)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_round))
                .build();

        /*########## myNotification flags #############*/
        myNotification.flags = Notification.FLAG_AUTO_CANCEL;

        /*######## NotificationManager ##########*/
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, myNotification);

    }

}

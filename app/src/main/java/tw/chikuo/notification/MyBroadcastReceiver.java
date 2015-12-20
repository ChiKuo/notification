package tw.chikuo.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Chi on 2015/12/20.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Get Intent Data
        String message = intent.getStringExtra("message");

        sendNotification(context,message);

    }

    private void sendNotification(Context context,String message) {

        // Create a Notification Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Title")
                .setContentText("From Alarm Message :" + message )
                .setAutoCancel(true);

        // Define the Notification's Action
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the Notification's Click Behavior
        builder.setContentIntent(resultPendingIntent);

        // Issue the Notification
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());

    }
}

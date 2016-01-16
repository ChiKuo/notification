package tw.chikuo.notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_NOTIFICATION = "tw.chikuo.notification.NOTIFICATION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Send Notification
        Button sendNotificationButton = (Button) findViewById(R.id.send_notification_button);
        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        // Update Notification
        Button updateNotificationButton = (Button) findViewById(R.id.update_notification_button);
        updateNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });

        // Remove Notification
        Button removeNotificationButton = (Button) findViewById(R.id.remove_notification_button);
        removeNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeNotification();
            }
        });

        // Send Notification with AlarmManager
        Button sendAlarmButton = (Button) findViewById(R.id.send_alarm_button);
        sendAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlarmNotification();
            }
        });

        // Send Repeat Notification with AlarmManager
        Button sendRepeatAlarmButton = (Button) findViewById(R.id.send_repeat_alarm_button);
        sendRepeatAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRepeatAlarmNotification();
            }
        });

        // Remove Repeat Notification with AlarmManager
        Button removeRepeatAlarmButton = (Button) findViewById(R.id.remove_repeat_alarm_button);
        removeRepeatAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRepeatAlarmNotification();
            }
        });
    }

    private void sendNotification() {

        // Create a Notification Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Title")
                .setContentText("Message")
                .setAutoCancel(true);

        // Define the Notification's Action
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the Notification's Click Behavior
        builder.setContentIntent(resultPendingIntent);

        // Issue the Notification
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());

    }

    private void updateNotification() {

        // Modify a Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("New Title")
                .setContentText("New Message")
                .setAutoCancel(true);

        // Set the large number at the right-hand side of the notification.
//        builder.setNumber(number);

        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());

    }

    private void removeNotification() {

        // You call cancel() for a specific notification ID. This method also deletes ongoing notifications.
        // You call cancelAll(), which removes all of the notifications you previously issued.
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);

    }

    private void sendAlarmNotification() {

        // Setup Alarm Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 3);

        String message = "notification with 3 second";
        int alarmId = (int) System.currentTimeMillis();

        // Define the AlarmManager's Action
        Intent intent = new Intent(BROADCAST_NOTIFICATION);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void sendRepeatAlarmNotification() {

        // Setup Alarm Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);

        String message = "repeat notification with 5 second";
        int pendingIntentId = 2;

        // Define the AlarmManager's Action
        Intent intent = new Intent(BROADCAST_NOTIFICATION);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, pendingIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Setup AlarmManager Repeating with 5 second
        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5 * 1000, pendingIntent);
    }

    private void removeRepeatAlarmNotification() {

        // Setup the alarmId which you need to remove
        int pendingIntentId = 2;

        Intent intent = new Intent(BROADCAST_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, pendingIntentId, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

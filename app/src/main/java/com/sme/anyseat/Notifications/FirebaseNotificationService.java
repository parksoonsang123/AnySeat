package com.sme.anyseat.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sme.anyseat.FreeBoardDetailActivity;
import com.sme.anyseat.NoticeDetailActivity;
import com.sme.anyseat.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseNotificationService extends FirebaseMessagingService {

    int uidflag = 0;
    String title;
    String body;
    String postid;
    String type;
    String alramid;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseNotificationService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("Service", "pss");
        Map<String, String> data_notify = remoteMessage.getData();

        if(data_notify != null){
            Log.e("FCMService","received");
            title = "";
            body = data_notify.get("contents");
            postid = data_notify.get("postid");
            type = data_notify.get("type");
            alramid = data_notify.get("alramid");
            final String uid = mAuth.getCurrentUser().getUid();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String postuid = snapshot.child("userid").getValue(String.class);
                    if(postuid.equals(uid)){
                        //uidflag = 1;
                        if (type.equals("1")) {
                            title = "???????????? ????????? ???????????????.\n";
                            Intent intent = new Intent(getBaseContext(), FreeBoardDetailActivity.class);
                            intent.putExtra("postid", postid);
                            intent.putExtra("alram", "1");
                            intent.putExtra("alramid", alramid);
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            String channelId = "mychannel";

                            Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            NotificationCompat.Builder notificationBuilder =
                                    new NotificationCompat.Builder(getApplicationContext(), channelId)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(title)
                                            .setAutoCancel(true)
                                            .setSound(defaultUri)
                                            .setContentIntent(pendingIntent);

                            NotificationManager notificationManger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                String channelName = "channelName";
                                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                                notificationManger.createNotificationChannel(channel);
                            }
                            notificationManger.notify(0, notificationBuilder.build());
                        } else {
                            title = "????????? ??????????????? ????????????.\n";
                            Intent intent = new Intent(getBaseContext(), NoticeDetailActivity.class);
                            intent.putExtra("postid", postid);
                            intent.putExtra("alram", "1");
                            intent.putExtra("alramid", alramid);
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            String channelId = "mychannel";

                            Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            NotificationCompat.Builder notificationBuilder =
                                    new NotificationCompat.Builder(getApplicationContext(), channelId)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(title)
                                            .setAutoCancel(true)
                                            .setSound(defaultUri)
                                            .setContentIntent(pendingIntent);

                            NotificationManager notificationManger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                String channelName = "channelName";
                                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                                notificationManger.createNotificationChannel(channel);
                            }
                            notificationManger.notify(0, notificationBuilder.build());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }


    /*private void sendNotification(RemoteMessage remoteMessage){

        Log.e("eqweqweqwe", remoteMessage.getSenderId());

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            String channelId = MainActivity.CHANNEL_ID;
            String channelName = "????????????";
            String channelDescription = "????????????";


            NotificationChannel channel = new NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);


            notificationManager.createNotificationChannel(channel);

            notificationBuilder = new NotificationCompat.Builder(this, channelId);

        }
        else{
            notificationBuilder = new NotificationCompat.Builder(this);
        }


        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("????????????")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("bodybody"))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, notificationBuilder.build());
    }*/


    /* @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("Firebase", "FIS : "+ s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if(remoteMessage != null && remoteMessage.getData().size()>0){
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage remoteMessage){

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channel = "??????";
            String channel_nm = "?????????";

            NotificationManager notichannel = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm, NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("????????? ?????? ??????");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(false);
            channelMessage.setVibrationPattern(new long[]{100,200,100,200});
            notichannel.createNotificationChannel(channelMessage);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setChannelId(channel)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(9999, notificationBuilder.build());
        }
        else{
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(9999, notificationBuilder.build());

        }


    }*/
}

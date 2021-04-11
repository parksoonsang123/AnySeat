package com.sme.anyseat.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                        "Authorization:key=AAAAOjFCeWA:APA91bGysX8eYlOSyZ6PNmvZm4Famv0tPlnnTjpdm5xLPVFskywsWXqWFvR1gA22l5fnkuh8CWLtF9q92tKXNamMsO0sQvt5ZjYTrSNz3mHdfmovkDO1VNNwtqOlKQFvlR1Jpw2ehHNm"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationData body);
}

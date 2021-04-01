package com.example.anyseat.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                        "Authorization:key=AAAAOj6cgHk:APA91bE6kw8_oJHBLnkDIcmYZkNBdUvTscZVQBAw6cn9H6WEeg8fI_QSQJ4KtlbFw6bMTweCJeCMEql5dRMo5Lc_NHajW9XjyrJPssCWdOKUV46LyLOkKnvWwphHEPYHMJlw8qnXDRUw"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationData body);
}

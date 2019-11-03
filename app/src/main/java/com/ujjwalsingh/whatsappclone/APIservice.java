package com.ujjwalsingh.whatsappclone;

import com.ujjwalsingh.whatsappclone.Notification.Sender;
import com.ujjwalsingh.whatsappclone.Notification.TheResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIservice {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:AAAAWMtN8Ds:APA91bF4AwrQoBcUFwNZ0Vwqn2LTFw4uj1glr5p_GsIrW9q70QLIngp-5zVQwtXpLe43jUhZ0RbeQiGF9yIk5vBQ1Kxhf6eccOwMAoI5Yxu2hohSaoZRMa54O78oujI4aikAAQbN5uoM"
            }
    )

    @POST("fcm/send")
    Call<TheResponse> sendNotification(@Body Sender body);
}

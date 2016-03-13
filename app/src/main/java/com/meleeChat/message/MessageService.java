package com.meleeChat.message;

import com.meleeChat.message.Messages;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessageService {
        @GET("post_message")
        Call<Messages> post_Message(@Query("lat") float lat,
                                    @Query("lng") float lng,
                                    @Query("nickname") String nickname,
                                    @Query("user_id") String user_id,
                                    @Query("message") String message,
                                    @Query("message_id") String message_id);

        @GET("get_messages")
        Call<Messages> get_Messages(@Query("lat") float lat,
                                    @Query("lng") float lng,
                                    @Query("user_id") String user_id);
}

package com.example.mcbud.firebase_basic2_171031;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mcbud on 2017-10-31.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "IDService";
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]
    private void sendRegistrationToServer(String token) {
        // TODO: 내 데이터 베이스의 사용자 token 값을 여기서 갱신
        String user_node = "user/사용자아이디/token";
        // user_node.setValue(token);  // 내 데이터베이스의 토큰을 갱신한다.
    }
}


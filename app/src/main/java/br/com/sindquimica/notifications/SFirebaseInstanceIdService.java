package br.com.sindquimica.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.sindquimica.util.Data;

/**
 * Created by fred on 12/01/17.
 */

public class SFirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("Notificações","Token:"+token);

        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token){

       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

       Data.saveToken(preferences,token);
    }

}

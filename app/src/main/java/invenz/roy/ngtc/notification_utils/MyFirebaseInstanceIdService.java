package invenz.roy.ngtc.notification_utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import invenz.roy.ngtc.utils.Constants;

import static invenz.roy.ngtc.utils.Constants.TOKEN_BROADCAST;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {


    private static final String TAG = "ROY";

    @Override
    public void onTokenRefresh() {
        /*#####            Get updated InstanceID token.               ###*/
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        /*####        storing token using method              ####*/
        storeTokenToSharedPref(refreshedToken);

        /*#####           Broadcast so that textView in mainCativity will appaear after token is genarated         ######*/
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));


    }


/*###                 storing Token To SharedPreference                         ####*/

    private void storeTokenToSharedPref(String refreshedToken) {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_ACCESS_TOKEN, refreshedToken);
        editor.commit();

    }


}

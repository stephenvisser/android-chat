package com.andmobility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by visser on 2014-03-15.
 */
public class PushReceiver extends BroadcastReceiver {
        private static final String TAG = "MyCustomReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "PUSH WORKS YEAH");
//            try {
//                String action = intent.getAction();
//                String channel = intent.getExtras().getString("com.parse.Channel");
//                JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
//
//                Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
//                Iterator itr = json.keys();
//                while (itr.hasNext()) {
//                    String key = (String) itr.next();
//                    Log.d(TAG, "..." + key + " => " + json.getString(key));
//                }
//            } catch (JSONException e) {
//                Log.d(TAG, "JSONException: " + e.getMessage());
//            }
        }
}

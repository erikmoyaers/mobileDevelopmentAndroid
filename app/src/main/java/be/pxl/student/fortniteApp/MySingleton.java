package be.pxl.student.fortniteApp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static String challengesUrl = "https://api.fortnitetracker.com/v1/challenges";
    private static String userStatsUrl = "https://api.fortnitetracker.com/v1/profile";
    private static String storeUrl = "https://api.fortnitetracker.com/v1/store";
    private List<String> mChallenges;

    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            mChallenges = requestChallenges();
        }
        return mRequestQueue;
    }


    public HashMap<String,String> sendUserStatsRequest(SharedPreferences preferences, final ILifetimeStatsCallback callback){

        String platform = preferences.getString(mCtx.getString(R.string.platform), "");
        String username = preferences.getString(mCtx.getString(R.string.username),"");
        String url = userStatsUrl+"/"+platform+"/"+username;
        HashMap<String,String> userDataMap = new HashMap<>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest
                (Request.Method.GET, url, null, (JSONObject response) -> {
                    try {
                        JSONArray array = response.getJSONArray("lifeTimeStats");
                        System.out.println(array);

                        for(int i=0;i<array.length();i++){
                            String key = array.getJSONObject(i).getString("key");
                            System.out.println(key);
                            String value = array.getJSONObject(i).getString("value");
                            System.out.println(value);
                            userDataMap.put(key,value);
                        }
                        callback.onSuccesResponse(userDataMap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                        error -> {
                            //Failure Callback
                            System.out.println(error);
                        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("TRN-Api-Key", BuildConfig.ApiKey);
                return headers;
            }
        };
        mRequestQueue.add(jsonObjReq);
        return userDataMap;
    }

    private List<String> requestChallenges(){
        List<String> challenges = new ArrayList<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest
                (Request.Method.GET, challengesUrl, null, response -> {
                    JSONArray array = null;
                    try {
                        array = response.getJSONArray("items");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for(int i=0;i<array.length();i++){
                        try {
                            challenges.add(array.getJSONObject(i).getJSONArray("metadata").getJSONObject(1).getString("value"));
                            System.out.println(array.getJSONObject(i).getJSONArray("metadata").getJSONObject(1).getString("value"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                        error -> {
                            //Failure Callback
                            System.out.println(error);
                        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("TRN-Api-Key", BuildConfig.ApiKey);
                return headers;
            }
        };
        mRequestQueue.add(jsonObjReq);
        return challenges;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public List<String> getChallenges(){
        return mChallenges;
    }


}

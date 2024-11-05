package com.example.apphotel.Login.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.apphotel.Login.AuthService.AccessTokenJson;
import com.example.apphotel.Login.AuthService.AuthEnpoint;
import com.example.apphotel.Login.AuthService.AuthenticationCallback;
import com.example.apphotel.Login.AuthService.AuthenticationRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthenticationTask extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private AuthenticationCallback callback;
    private AuthEnpoint apiAuthEnpointService;
    private ProgressDialog progressDialog;

    public AuthenticationTask(Context context, AuthenticationCallback callback, AuthEnpoint apiAuthEnpointService) {
        this.context = context;
        this.callback = callback;
        this.apiAuthEnpointService = apiAuthEnpointService;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Login to your account...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... credentials) {
        if (credentials.length < 2) {
            return false;
        }

        String email = credentials[0];
        String password = credentials[1];

        Call<AccessTokenJson> call = apiAuthEnpointService.authenticate(new AuthenticationRequest(password, email));

        try {
            Response<AccessTokenJson> response = call.execute();

            if (response.isSuccessful()) {
                AccessTokenJson authenticationResponse = response.body();
                String jwtToken = authenticationResponse.getAccessToken();

                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("jwtKey", jwtToken);
                editor.putLong("lastPuttedJwtTime", System.currentTimeMillis());
                editor.apply();

                return true;
            } else {
                Log.e("JWT", "Thong tin dang nhap bi sai roi");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            progressDialog.dismiss();
            callback.onSuccess();
        } else {
            progressDialog.dismiss();
            callback.onFailure();
        }
    }
}


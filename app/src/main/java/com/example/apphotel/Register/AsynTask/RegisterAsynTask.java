package com.example.apphotel.Register.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.apphotel.Login.AuthService.AccessTokenJson;
import com.example.apphotel.Register.RegisterApiService.RegisterCallBack;
import com.example.apphotel.Register.RegisterApiService.RegisterEndpoint;
import com.example.apphotel.Register.dto.RegisterRequest;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterAsynTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private RegisterEndpoint registerEndpoint;
    private RegisterCallBack registerCallBack;
    private ProgressDialog progressDialog;

    public RegisterAsynTask(Context context, RegisterEndpoint registerEndpoint, RegisterCallBack registerCallBack) {
        this.context = context;
        this.registerEndpoint = registerEndpoint;
        this.registerCallBack = registerCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Register your account...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... credentials) {
        Log.e("DANG KY", "DANG DANG KY ....");

        String email = credentials[0];
        String password = credentials[1];
        String username = credentials[2];
        Call<AccessTokenJson> call = registerEndpoint.signup(new RegisterRequest(email,password, username));
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
                Log.i("DANG KY2", "DANG DANG THANH CONG....");

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
       if(success) {
           progressDialog.dismiss();
           registerCallBack.onSuccess();
       }
       else {
           progressDialog.dismiss();
           registerCallBack.onFailure();
       }
    }
}

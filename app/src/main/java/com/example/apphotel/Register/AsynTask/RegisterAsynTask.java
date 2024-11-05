package com.example.apphotel.Register.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.apphotel.Register.RegisterApiService.RegisterCallBack;
import com.example.apphotel.Register.RegisterApiService.RegisterEndpoint;
import com.example.apphotel.Register.dto.RegisterRequest;

import okhttp3.ResponseBody;
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
        progressDialog.setMessage("Đang đăng ký tài khoản...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... credentials) {
        Log.e("DANG KY", "ĐANG ĐĂNG KÝ ....");

        String email = credentials[0];
        String password = credentials[1];
        String username = credentials[2];

        // Gọi API đăng ký
        Call<ResponseBody> call = registerEndpoint.signup(new RegisterRequest(email, password, username));
        try {
            Response<ResponseBody> response = call.execute();

            if (response.isSuccessful()) {
                // Kiểm tra nếu đăng ký thành công
                return true;
            } else {
                Log.e("Register Error", "Đăng ký không thành công: " + response.errorBody().string());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        progressDialog.dismiss();
        if (success) {
            registerCallBack.onSuccess();
            Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        } else {
            registerCallBack.onFailure();
            Toast.makeText(context, "Đăng ký thất bại. Kiểm tra lại thông tin.", Toast.LENGTH_SHORT).show();
        }
    }
}

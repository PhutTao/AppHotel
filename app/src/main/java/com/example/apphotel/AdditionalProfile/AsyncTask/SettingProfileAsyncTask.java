package com.example.apphotel.AdditionalProfile.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.apphotel.AdditionalProfile.Utils.DateUtils;
import com.example.apphotel.AdditionalProfile.Dto.ResponseData;
import com.example.apphotel.AdditionalProfile.Dto.SexEnum;
import com.example.apphotel.AdditionalProfile.Dto.UserProfile;
import com.example.apphotel.AdditionalProfile.profileApiService.ProfileApiCallBack;
import com.example.apphotel.AdditionalProfile.profileApiService.ProfileEndpoint;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class SettingProfileAsyncTask extends AsyncTask<String, Void, Boolean> {

    private ProfileEndpoint profileEndpoint;
    private ProfileApiCallBack callBack;
    private File file;
    private  String jwt;

    public SettingProfileAsyncTask(ProfileEndpoint profileEndpoint, ProfileApiCallBack callBack, File file) {
        this.profileEndpoint = profileEndpoint;
        this.callBack = callBack;
        this.file = file;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            String dateOfBirth=strings[0];
            SexEnum sex=null;
            try {

                 sex =SexEnum.valueOf(strings[1].toLowerCase());
            } catch (Exception e) {
            }

            String address=strings[2];
            String phone=strings[3];
            jwt=strings[4];

            if(sex==null) sex=SexEnum.famale;

            UserProfile userProfile=new UserProfile(
                    phone, sex.name().toString(),dateOfBirth,address
            );
            this.jwt="Bearer " +jwt;
            Call<ResponseData> call = profileEndpoint.setProfile(jwt, userProfile);
            Response<ResponseData> response = call.execute();
            if (response.code() != 200) {
                Log.i("STATUS", "STATUS : " + response.code() + "" + response.message());
                Log.e("RESULT", "THAT BAI.");
                Log.i("Profile", userProfile.toString());
            }
            return setAvatar();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private Boolean setAvatar() {
        try{
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipartBody=MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            Call<ResponseData> call=profileEndpoint.setAvatar(jwt,multipartBody);
            Response<ResponseData> data= call.execute();
            if(data.isSuccessful() && data.code()==200){
                return true;
            }else{
                Log.e("BUG", "Bug ... khong biet nua ");
            }


        }catch (Exception ioe){
            ioe.printStackTrace();
            Log.e("BUG", "... khong biet nua ");
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) callBack.onSuccess();
        else {
            callBack.onFailure();
        }
    }

}

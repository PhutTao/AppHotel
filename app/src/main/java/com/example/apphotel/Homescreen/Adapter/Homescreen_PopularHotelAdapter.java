package com.example.apphotel.Homescreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelsApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
import com.example.apphotel.R;
import android.content.SharedPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homescreen_PopularHotelAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Homescreen_PopularHotel> popularHotelList;

    public Homescreen_PopularHotelAdapter(Context context, int layout, List<Homescreen_PopularHotel> popularHotelList) {
        this.context = context;
        this.layout = layout;
        this.popularHotelList = popularHotelList;
    }

    @Override
    public int getCount() {
        return popularHotelList.size();
    }

    @Override
    public Object getItem(int i) {
        return popularHotelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView name , location;

        ImageView imgHinh;
        TextView txtDanhGia;
        TextView txtSLDanhGia;
        TextView txtGia;
        ImageView heartImageView;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.home_name_popularhotel);
            holder.location = view.findViewById(R.id.home_location_popularhotel);
            holder.imgHinh = view.findViewById(R.id.home_img_popularhotel);
            holder.name = view.findViewById(R.id.home_rate_popularhotel);
            holder.name = view.findViewById(R.id.home_SLdanhgia_popularhotel);
            holder.name = view.findViewById(R.id.home_price_popularhotel);
            holder.heartImageView = view.findViewById(R.id.home_tym);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Gán giá trị từ đối tượng popularHotel vào các View
        Homescreen_PopularHotel popularHotel = popularHotelList.get(i);
        holder.name.setText(popularHotel.getTen());
        holder.location.setText(popularHotel.getDiaChi());
        holder.name.setText(String.valueOf(popularHotel.getDanhGia()));
        holder.name.setText(String.valueOf(popularHotel.getSoLuongDanhGia()));
        holder.name.setText(String.valueOf(popularHotel.getGia()));

        // Tải hình ảnh từ URL
        Glide.with(context)
                .load(popularHotel.getHinh())
                .placeholder(R.drawable.homescreen_haian) // Hình ảnh hiển thị trong khi tải
                .error(R.drawable.homescreen_haian) // Hình ảnh khi xảy ra lỗi
                .into(holder.imgHinh);

        // Đổi màu trái tim khi trạng thái thay đổi
        holder.heartImageView.setImageResource(popularHotel.isRedHeart() ? R.drawable.homescreen_heart_red : R.drawable.homescreen_heart_white);

        // Xử lý sự kiện khi click vào hình trái tim
        holder.heartImageView.setOnClickListener(v -> {
            boolean currentState = popularHotel.isRedHeart();
            popularHotel.setRedHeart(!currentState);

            // Cập nhật trạng thái hình trái tim
            holder.heartImageView.setImageResource(popularHotel.isRedHeart() ? R.drawable.homescreen_heart_red : R.drawable.homescreen_heart_white);

            // Thực hiện gọi API tương ứng
            if (popularHotel.isRedHeart()) {
                addToFavorites(popularHotel.getHotelId());
            } else {
                removeFromFavorites(popularHotel.getHotelId());
            }
        });

        return view;
    }


    private void addToFavorites(int hotelId) {
        Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtKey", null);

        Call<Home_HotelsApiResponse> call = hotelEndpoint.postFavoriteHotels(hotelId, "Bearer " + jwtToken);
        call.enqueue(new Callback<Home_HotelsApiResponse>() {
            @Override
            public void onResponse(Call<Home_HotelsApiResponse> call, Response<Home_HotelsApiResponse> response) {
                if (response.isSuccessful()) {
                    // Handle the success response, update your data model if needed
                } else {
                    // Handle the error response
                }
            }

            @Override
            public void onFailure(Call<Home_HotelsApiResponse> call, Throwable t) {
                // Handle the failure (e.g., network error)
            }
        });
    }

    private void removeFromFavorites(int hotelId) {
        Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtKey", null);

        Call<Home_HotelsApiResponse> call = hotelEndpoint.deleteFavoriteHotels(hotelId, "Bearer " + jwtToken);
        call.enqueue(new Callback<Home_HotelsApiResponse>() {
            @Override
            public void onResponse(Call<Home_HotelsApiResponse> call, Response<Home_HotelsApiResponse> response) {
                if (response.isSuccessful()) {
                    // Handle the success response, update your data model if needed
                } else {
                    // Handle the error response
                }
            }

            @Override
            public void onFailure(Call<Home_HotelsApiResponse> call, Throwable t) {
                // Handle the failure (e.g., network error)
            }
        });
    }
}

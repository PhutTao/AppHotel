package com.example.apphotel.Homescreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphotel.Homescreen.HotelApiService.Home_BookedApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Booked;
import com.example.apphotel.R;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homescreen_BookedAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Homescreen_Booked> bookedList;

    public Homescreen_BookedAdapter(Context context, int layout, List<Homescreen_Booked> bookedList) {
        this.context = context;
        this.layout = layout;
        this.bookedList = bookedList;
    }

    @Override
    public int getCount() {
        return bookedList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookedList.get(i); // Trả về đối tượng tại vị trí `i`
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        // Ánh xạ view
        TextView txtTen = view.findViewById(R.id.mybooking_name_booked);
        TextView txtDiaChi = view.findViewById(R.id.mybooking_location_booked);
        ImageView imgHinh = view.findViewById(R.id.mybooking_img_booked);
        TextView txtDanhGia = view.findViewById(R.id.mybooking_rate_booked);
        TextView txtSLDanhGia = view.findViewById(R.id.mybooking_SLdanhgia_booked);
        TextView txtGia = view.findViewById(R.id.mybooking_price_booked);
        TextView txtCheckIn = view.findViewById(R.id.mybooking_ngaycheckin_booked);
        TextView txtCheckOut = view.findViewById(R.id.mybooking_ngaycheckout_booked);

        // Gán giá trị từ đối tượng
        Homescreen_Booked bookedHotels = bookedList.get(i);

        txtTen.setText(bookedHotels.getTen());
        txtDiaChi.setText(bookedHotels.getDiaChi());
        imgHinh.setImageBitmap(bookedHotels.getHinh());
        txtDanhGia.setText(String.valueOf(bookedHotels.getDanhGia()));
        txtSLDanhGia.setText(String.valueOf(bookedHotels.getSoLuongDanhGia()));
        txtGia.setText(String.format("$%.2f / ngày", bookedHotels.getGia()));
        txtCheckIn.setText(new SimpleDateFormat("dd/MM/yyyy").format(bookedHotels.getNgayCheckIn()));
        txtCheckOut.setText(new SimpleDateFormat("dd/MM/yyyy").format(bookedHotels.getNgayCheckOut()));

        // Xử lý sự kiện nút "Hủy"


        return view;
    }
}
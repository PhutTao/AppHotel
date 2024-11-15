package com.example.apphotel.Booking.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.Booking.Activity.BookingActivity;
import com.example.apphotel.Booking.Item.BookingRoomType;
import com.example.apphotel.R;

import java.util.ArrayList;

public class BookingRoomTypeAdapter extends RecyclerView.Adapter<BookingRoomTypeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<BookingRoomType> roomTypeArrayList;

    public BookingRoomTypeAdapter(BookingActivity context, ArrayList<BookingRoomType> roomTypeArrayList) {
        this.context = context;
        this.roomTypeArrayList = roomTypeArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtRoomName, txtRoomDescription;
        private CheckBox ckbRoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.txtRoomName = itemView.findViewById(R.id.booking_room_type_name);
            this.txtRoomDescription = itemView.findViewById(R.id.booking_room_type_description);
            this.ckbRoom = itemView.findViewById(R.id.booking_room_type_checkbox);

            ckbRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((CheckBox) v).isChecked();

                    if (isChecked) {
                        roomTypeArrayList.get(getAdapterPosition()).setSelected(true);
                    } else {
                        roomTypeArrayList.get(getAdapterPosition()).setSelected(false);
                    }

                    notifyDataSetChanged();
                    for (int i = 0; i < roomTypeArrayList.size(); i++) {
                        Log.d("TAG", roomTypeArrayList.toString());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public BookingRoomTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.booking_room_type_select_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingRoomTypeAdapter.ViewHolder holder, int position) {
        BookingRoomType roomType = roomTypeArrayList.get(position);
        holder.txtRoomName.setText(roomType.getName());
        holder.txtRoomDescription.setText(roomType.getDescription());
        holder.ckbRoom.setChecked(roomType.isSelected());
    }

    @Override
    public int getItemCount() {
        return roomTypeArrayList.size();
    }
}

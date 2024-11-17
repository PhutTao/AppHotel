package com.example.apphotel.Booking.Adapter;

import static com.example.apphotel.Booking.Enum.PaymentMethod.*;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.Booking.Enum.PaymentMethod;
import com.example.apphotel.Booking.Item.BookingPaymentMethod;
import com.example.apphotel.Booking.Item.BookingRoomType;
import com.example.apphotel.R;

import java.util.ArrayList;
import java.util.List;

public class BookingPaymentMethodAdapter extends RecyclerView.Adapter<BookingPaymentMethodAdapter.ViewHolder> {
    private ArrayList<BookingPaymentMethod> bookingPaymentMethodList;
    private List<OnItemClickListener> clickListeners;

    public BookingPaymentMethodAdapter() {
        this.clickListeners = new ArrayList<>();
    }

    public BookingPaymentMethodAdapter(ArrayList<BookingPaymentMethod> bookingPaymentMethodList) {
        this.bookingPaymentMethodList = bookingPaymentMethodList;
        this.clickListeners = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onItemClick(BookingPaymentMethod item);
    }

    public void addOnItemClickListener(OnItemClickListener listener) {
        clickListeners.add(listener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCardName, txtCardNumber;
        private ImageView imgvCardLogo;
        private AppCompatButton selectCardBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCardName = itemView.findViewById(R.id.booking_payment_card_name);
            txtCardNumber = itemView.findViewById(R.id.booking_payment_card_number);
            imgvCardLogo = itemView.findViewById(R.id.booking_payment_card_logo);
            selectCardBtn = itemView.findViewById(R.id.user_payment_select_button);
        }
    }

    @NonNull
    @Override
    public BookingPaymentMethodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.booking_user_payment, parent, false);
        BookingPaymentMethodAdapter.ViewHolder viewHolder = new BookingPaymentMethodAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingPaymentMethodAdapter.ViewHolder holder, int position) {
        BookingPaymentMethod paymentMethod = bookingPaymentMethodList.get(position);

        holder.txtCardName.setText(paymentMethod.getCardName());
        holder.txtCardNumber.setText(paymentMethod.getCardNumber());

        switch(paymentMethod.getPaymentMethod().name()) {
            case "Credit_Card":
                holder.imgvCardLogo.setImageResource(R.drawable.payment_credit_card_logo);
                break;
            case "Momo":
                holder.imgvCardLogo.setImageResource(R.drawable.payment_momo_logo);
                break;
            case "PayPal":
                holder.imgvCardLogo.setImageResource(R.drawable.payment_paypal_logo);
                break;
            case "Other":
                holder.imgvCardLogo.setImageResource(R.drawable.payment_other_card_logo);
                break;
        }

        holder.selectCardBtn.setOnClickListener(v -> {
            for (OnItemClickListener listener : clickListeners) {
                listener.onItemClick(paymentMethod);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingPaymentMethodList.size();
    }
}

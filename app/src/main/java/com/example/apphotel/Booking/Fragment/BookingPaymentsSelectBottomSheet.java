package com.example.apphotel.Booking.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphotel.Booking.Activity.BookingAddCardActivity;
import com.example.apphotel.Booking.Adapter.BookingPaymentMethodAdapter;
import com.example.apphotel.Booking.AsyncTask.DeletePaymentApi;
import com.example.apphotel.Booking.AsyncTask.GetAllPaymentsApi;
import com.example.apphotel.Booking.Constants.Constants;
import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Enum.PaymentMethod;
import com.example.apphotel.Booking.Interface.PaymentSelectionListener;
import com.example.apphotel.Booking.Item.BookingPaymentMethod;
import com.example.apphotel.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookingPaymentsSelectBottomSheet extends BottomSheetDialogFragment
        implements GetAllPaymentsApi.ApiCallListener, BookingPaymentMethodAdapter.OnItemClickListener,
        YesNoDialogFragment.YesNoDialogListener {

    private static final String TAG = "BookingPayments";

    private RecyclerView rvPaymentMethod;
    private AppCompatButton addPaymentBtn;
    private BookingPaymentMethodAdapter bookingPaymentMethodAdapter;
    private List<BookingPaymentMethod> paymentMethodList;
    private int swipedPosition = RecyclerView.NO_POSITION;

    private BookingFormDetailData bookingFormDetailData;

    public BookingPaymentsSelectBottomSheet() {
        // Required empty public constructor
    }

    public BookingPaymentsSelectBottomSheet(BookingFormDetailData bookingFormDetailData) {
        this.bookingFormDetailData = bookingFormDetailData;
    }
    public static class ViewHolder {

        TextView paymentMethodTextView;
        TextView cardNameTextView;

        TextView cardNumberTextView;


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.booking_payments_select_bottom_sheet, null);
        bottomSheetDialog.setContentView(contentView);

        rvPaymentMethod = contentView.findViewById(R.id.booking_payment_recycler_view);
        addPaymentBtn = contentView.findViewById(R.id.booking_add_payment_button);

        setupRecyclerView();
        setUpNavigateToAddPayment();

        // Trigger API call to load payment methods
        new GetAllPaymentsApi(this).execute();

        return bottomSheetDialog;
    }

    private void setupRecyclerView() {
        paymentMethodList = new ArrayList<>();
        bookingPaymentMethodAdapter = new BookingPaymentMethodAdapter((ArrayList<BookingPaymentMethod>) paymentMethodList);
        bookingPaymentMethodAdapter.addOnItemClickListener(this);

        rvPaymentMethod.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPaymentMethod.setItemAnimator(new DefaultItemAnimator());
        rvPaymentMethod.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        rvPaymentMethod.setAdapter(bookingPaymentMethodAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvPaymentMethod);
    }

    private void setUpNavigateToAddPayment() {
        addPaymentBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), BookingAddCardActivity.class);
            intent.setAction(Constants.ACTION_CHECKOUT_TO_ADDCARD);

            Bundle bundle = new Bundle();
            bundle.putSerializable("bookingFormData", (Serializable) bookingFormDetailData);
            intent.putExtras(bundle);

            startActivity(intent);
        });
    }

    @Override
    public void onGetAllPaymentsCompleted(List<BookingPaymentMethod> payments) {
        if (payments != null && !payments.isEmpty()) {
            paymentMethodList.clear();
            for (BookingPaymentMethod payment : payments) {
                if (payment.getPaymentMethod() == null) {
                    Log.e("PaymentDataError", "Payment method is null for card: " + payment.getCardName());
                    payment.setPaymentMethod(PaymentMethod.UNKNOWN); // Hoáº·c giĂ¡ trá»‹ máº·c Ä‘á»‹nh phĂ¹ há»£p
                }
            }
            paymentMethodList.addAll(payments);
            bookingPaymentMethodAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(requireContext(), "No payment methods available.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetAllPaymentsFailure(String errorMessage) {
        Log.e(TAG, "Error fetching payments: " + errorMessage);
        Toast.makeText(requireContext(), "Failed to load payment methods.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(BookingPaymentMethod item) {
        if (getActivity() instanceof PaymentSelectionListener) {
            ((PaymentSelectionListener) requireActivity()).onPaymentSelected(item);
        }
        dismiss();
    }

    private final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            swipedPosition = viewHolder.getAdapterPosition();
            if (swipedPosition != RecyclerView.NO_POSITION) {
                String message = "Do you want to delete this payment?";
                YesNoDialogFragment dialog = new YesNoDialogFragment(message);
                dialog.setListener(BookingPaymentsSelectBottomSheet.this);
                dialog.show(getParentFragmentManager(), "YesNoDialog");
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(requireContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.booking_red))
                    .addSwipeLeftActionIcon(R.drawable.booking_bin_delete_icon)
                    .addSwipeLeftLabel("Delete payment")
                    .setSwipeLeftLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .setActionIconTint(ContextCompat.getColor(requireContext(), R.color.white))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onYesClicked() {
        if (swipedPosition != RecyclerView.NO_POSITION) {
            BookingPaymentMethod deletedPayment = paymentMethodList.get(swipedPosition);

            try {
                boolean success = new DeletePaymentApi(requireContext(), deletedPayment.getId()).execute().get();

                if (success) {
                    paymentMethodList.remove(swipedPosition);
                    bookingPaymentMethodAdapter.notifyItemRemoved(swipedPosition);
                    Toast.makeText(requireContext(), "Payment deleted successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to delete payment.", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error deleting payment: " + e.getMessage(), e);
                Toast.makeText(requireContext(), "An error occurred.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNoClicked() {
        if (swipedPosition != RecyclerView.NO_POSITION) {
            bookingPaymentMethodAdapter.notifyItemChanged(swipedPosition);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingPaymentMethod paymentMethod = paymentMethodList.get(position);

        if (paymentMethod != null) {
            if (paymentMethod.getPaymentMethod() != null) {
                holder.paymentMethodTextView.setText(paymentMethod.getPaymentMethod().name());
            } else {
                holder.paymentMethodTextView.setText("Unknown"); // Hoáº·c giĂ¡ trá»‹ máº·c Ä‘á»‹nh khĂ¡c
            }
            holder.cardNameTextView.setText(paymentMethod.getCardName());
            holder.cardNumberTextView.setText(paymentMethod.getCardNumber());
        } else {
            holder.paymentMethodTextView.setText("Invalid Data");
            holder.cardNameTextView.setText("");
            holder.cardNumberTextView.setText("");
        }
    }






}

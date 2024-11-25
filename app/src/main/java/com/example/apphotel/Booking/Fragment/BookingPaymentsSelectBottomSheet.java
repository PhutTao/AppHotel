package com.example.apphotel.Booking.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apphotel.Booking.Activity.BookingAddCardActivity;
import com.example.apphotel.Booking.Activity.BookingCheckoutActivity;
import com.example.apphotel.Booking.Adapter.BookingPaymentMethodAdapter;
import com.example.apphotel.Booking.AsyncTask.DeletePaymentApi;
import com.example.apphotel.Booking.AsyncTask.GetAllPaymentsApi;
import com.example.apphotel.Booking.Constants.Constants;
import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Interface.PaymentSelectionListener;
import com.example.apphotel.Booking.Item.BookingPaymentMethod;
import com.example.apphotel.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookingPaymentsSelectBottomSheet extends BottomSheetDialogFragment
        implements
        GetAllPaymentsApi.ApiCallListener,
        BookingPaymentMethodAdapter.OnItemClickListener,
        YesNoDialogFragment.YesNoDialogListener {
    private View contentView;
    private RecyclerView rvPaymentMethod;
    private AppCompatButton addPaymentBtn;
    public BookingPaymentMethodAdapter bookingPaymentMethodAdapter;
    private List<BookingPaymentMethod> paymentMethodList;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    private BookingCheckoutActivity bookingCheckoutActivity;
    private BookingFormDetailData bookingFormDetailData;

    private ItemTouchHelper itemTouchHelper;
    private int swipedPosition = RecyclerView.NO_POSITION;

    public interface SwipedPositionListener {
        void onSwipedPosition(int position);
    }

    public BookingPaymentsSelectBottomSheet() {
        // Required empty public constructor
    }

    public BookingPaymentsSelectBottomSheet(BookingFormDetailData bookingFormDetailData) {
        this.bookingFormDetailData = bookingFormDetailData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

        contentView = LayoutInflater.from(getContext()).inflate(R.layout.booking_payments_select_bottom_sheet, null);
        bottomSheetDialog.setContentView(contentView);

        // Bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from((View) contentView.getParent());
//        bottomSheetBehavior.setPeekHeight(1400); // Set the initial peek height

        return bottomSheetDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPaymentMethod = view.findViewById(R.id.booking_payment_recycler_view);
        addPaymentBtn = view.findViewById(R.id.booking_add_payment_button);

        // Initialize the list and adapter
        paymentMethodList = new ArrayList<>();
        bookingPaymentMethodAdapter = new BookingPaymentMethodAdapter((ArrayList<BookingPaymentMethod>) paymentMethodList);

        // Add click listener
        bookingCheckoutActivity = new BookingCheckoutActivity();
        bookingPaymentMethodAdapter.addOnItemClickListener(this);

        // Set up RecyclerView
        rvPaymentMethod.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvPaymentMethod.setItemAnimator(new DefaultItemAnimator());
        rvPaymentMethod.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        rvPaymentMethod.setAdapter(bookingPaymentMethodAdapter);
        // Add swipe event to item of RecyclerView
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvPaymentMethod);

        // Set up click listener for add payment button
        setUpNavigateToAddPayment();

        // Trigger the API call
        new GetAllPaymentsApi(requireContext(), this).execute();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.booking_payments_select_bottom_sheet, container, false);
    }

    @Override
    public void onGetAllPaymentsCompleted(List<BookingPaymentMethod> payments) {
        if (payments != null) {
            paymentMethodList.addAll(payments);
            bookingPaymentMethodAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetAllPaymentsFailure(String errorMessage) {
        Log.e("API Error! Fail to get all payment methods", errorMessage);
    }

    @Override
    public void onItemClick(BookingPaymentMethod item) {
        if (getActivity() instanceof PaymentSelectionListener) {
            ((PaymentSelectionListener) getActivity()).onPaymentSelected(item);
        }

        dismiss();
    }

    // Handle swipe item to delete
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            swipedPosition = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {
                String customMessage = "Do you want to delete this payment";
                YesNoDialogFragment dialog = new YesNoDialogFragment(customMessage);
                dialog.setListener(BookingPaymentsSelectBottomSheet.this);
                dialog.show(getParentFragmentManager(), "CustomYesNoDialog");
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

    // Handle click on dialog
    @Override
    public void onYesClicked() {
        BookingPaymentMethod deletedPayment = paymentMethodList.get(swipedPosition);
        int deletedPaymentId = deletedPayment.getId();
        SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = preferences.getString("jwtKey", null);

        boolean deletionPaymentSuccessful;
        try {
            deletionPaymentSuccessful = new DeletePaymentApi(requireContext(), authToken, deletedPaymentId).execute().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (deletionPaymentSuccessful) {
            paymentMethodList.remove(swipedPosition);
            bookingPaymentMethodAdapter.notifyItemRemoved(swipedPosition);
            Toast.makeText(getContext(), "Deleted payment successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Deleted payment failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNoClicked() {
        refreshSwipeState();
    }

    private void refreshSwipeState() {
        if (swipedPosition != RecyclerView.NO_POSITION) {
            // Trigger the swipe state programmatically
            itemTouchHelper.startSwipe(rvPaymentMethod.findViewHolderForAdapterPosition(swipedPosition));
        }
    }
}

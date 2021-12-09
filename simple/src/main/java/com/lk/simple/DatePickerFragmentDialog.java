package com.lk.simple;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author wangjunjie 2021-12-08
 */
public class DatePickerFragmentDialog extends DialogFragment {
    private DatePickerAdapter adapter = null;
    private RecyclerView rv;
    private TextView txtLift, txtRight, txtDateTitle, txtLiftYear, txtRightDay;
    private View btnCancel, btnOk;
    Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    private int selectYear = year;
    private int selectMonth = month + 1;
    private int selectDay = day;

    public void initDate(int year, int month, int day) {
        this.year = year;
        this.month = month - 1;
        this.day = day;
        selectYear = year;
        selectMonth = month + 1;
        selectDay = day;
        adapter = new DatePickerAdapter(year, month, day);

    }

    private int width = 550;

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.simple_dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null)//禁止导航栏弹出
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) return;
        Window window = getDialog().getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        if (params == null) {
            params = new WindowManager.LayoutParams();
        }
        params.width = dp2Px(getDialog().getContext(), width);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        window.setAttributes(params);
        fullScreenImmersive(window.getDecorView());
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }


    //隐藏 状态栏
    private void fullScreenImmersive(View view) {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_simple_date_picker_layout, container, false);
        rv = inflate.findViewById(R.id.rv_date);
        txtRight = inflate.findViewById(R.id.txt_right);
        txtLift = inflate.findViewById(R.id.txt_lift);
        txtDateTitle = inflate.findViewById(R.id.txt_on_date);
        txtLiftYear = inflate.findViewById(R.id.txt_year);
        txtRightDay = inflate.findViewById(R.id.txt_day);
        btnCancel = inflate.findViewById(R.id.txt_cancel);
        btnOk = inflate.findViewById(R.id.txt_ok);
        return inflate;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        final FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(this, this.getClass().getCanonicalName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtLiftYear.setText(String.valueOf(year + "-" + (month + 1)));
        txtRightDay.setText(String.valueOf(day));
        adapter.setDateListener(new DatePickerAdapter.OnDateListener() {
            @Override
            public void onDate(int year, int month, int day) {
                selectYear = year;
                selectMonth = month;
                selectDay = day;
                txtLiftYear.setText(String.valueOf(year + "-" + month));
                txtRightDay.setText(String.valueOf(day));
            }
        });

        rv.setLayoutManager(new GridLayoutManager(getContext(), 7));
        if (adapter != null)
            rv.setAdapter(adapter);
        txtRight.setOnClickListener(v -> {
            month++;
            List<DatePickerPack> date = getDate(year, month, day);
            adapter.setDate(date);
        });
        txtLift.setOnClickListener(v -> {
            month--;
            List<DatePickerPack> date = getDate(year, month, day);
            adapter.setDate(date);
        });

        List<DatePickerPack> date = getDate(year, month, day);
        adapter.setDate(date);

        btnCancel.setOnClickListener(v -> {
            dismiss();
        });
        btnOk.setOnClickListener(v -> {
            if (onDateListener != null)
                onDateListener.onDate(selectYear, selectMonth, selectDay);
            dismiss();
        });
    }

    public List<DatePickerPack> getDate(int year, int month, int day) {
        List<DatePickerPack> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int realYear = calendar.get(Calendar.YEAR);
        int realMonth = calendar.get(Calendar.MONTH) + 1;
        int realDay = calendar.get(Calendar.DAY_OF_MONTH);

        txtDateTitle.setText(String.valueOf(realYear + "-" + realMonth));
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (week > 0) {
            for (int i = 0; i < week - 1; i++) {
                dateList.add(new DatePickerPack(0));
            }
        }
        for (int i = 1; i <= dayOfMonth; i++) {
            DatePickerPack datePickerPack = new DatePickerPack(i);
            datePickerPack.year = realYear;
            datePickerPack.mouth = realMonth;
            datePickerPack.day = realDay;
            dateList.add(datePickerPack);
        }
        return dateList;
    }

    public int dp2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private DatePickerAdapter.OnDateListener onDateListener;

    public void setOnDateListener(DatePickerAdapter.OnDateListener onDateListener) {
        this.onDateListener = onDateListener;
    }
}

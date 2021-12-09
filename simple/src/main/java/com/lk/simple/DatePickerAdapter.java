package com.lk.simple;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author wangjunjie 2021-12-08
 */
public class DatePickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public DatePickerAdapter(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        this.year =year;
        this.month = month;
        this.day = day;
    }

    private List<DatePickerPack> list = new ArrayList<>();
    private int year;
    private int month;
    private int day;
    private int selectIndex = -1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_picker_day_layout, parent, false);
        return new DayViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DayViewHolder) {
            DayViewHolder vh = (DayViewHolder) holder;
            if (list.get(position).code > 0) {
                vh.txt.setText(String.valueOf(list.get(position).code));
                if (list.get(position).code == day && list.get(position).mouth == month && list.get(position).year == year) {
                    vh.txt.setTextColor(Color.parseColor("#3abbfd"));
                    if (selectIndex == -1) selectIndex = position;
                } else {
                    vh.txt.setTextColor(Color.parseColor("#000000"));
                }
                if (selectIndex == position) {
                    vh.txt.setTextColor(Color.WHITE);
                    vh.vb.setSelected(true);
                } else {
                    vh.vb.setSelected(false);
                }

                vh.itemView.setOnClickListener(v -> {
                    selectIndex = position;
                    if (dateListener != null){
                        dateListener.onDate(list.get(position).year, list.get(position).mouth, list.get(position).code);
                    }

                    notifyDataSetChanged();
                });
            } else {
                vh.vb.setSelected(false);
                vh.txt.setText("");
                vh.itemView.setOnClickListener(v -> {
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDate(List<DatePickerPack> list) {
        if (list != null) {
            this.selectIndex = -1;
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        private TextView txt;
        private View vb;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            vb = itemView.findViewById(R.id.v_b);
        }
    }

    public interface OnDateListener {
        void onDate(int year, int month, int day);
    }

    private OnDateListener dateListener;

    public void setDateListener(OnDateListener dateListener) {
        this.dateListener = dateListener;
    }
}

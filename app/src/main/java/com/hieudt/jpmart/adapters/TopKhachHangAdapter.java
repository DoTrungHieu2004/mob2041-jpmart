package com.hieudt.jpmart.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.entity.TopKhachHang;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TopKhachHangAdapter extends RecyclerView.Adapter<TopKhachHangAdapter.ViewHolder> {
    private Context context;
    private List<TopKhachHang> topKhachHangList;
    TopKhachHang topKhachHangModel;
    NumberFormat currencyFormat;

    public TopKhachHangAdapter(Context context, List<TopKhachHang> topKhachHangList) {
        this.context = context;
        this.topKhachHangList = topKhachHangList;
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top_khach_hang, parent, false);
        return new TopKhachHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        topKhachHangModel = topKhachHangList.get(position);

        holder.tvTenKhachHang.setText(topKhachHangModel.getTenKhachHang());
        holder.tvTongChiTieu.setText("Tổng chi tiêu: " + currencyFormat.format(topKhachHangModel.getTongChiTieu()));
    }

    @Override
    public int getItemCount() {
        return topKhachHangList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenKhachHang, tvTongChiTieu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenKhachHang = itemView.findViewById(R.id.tv_ten_khach_hang);
            tvTongChiTieu = itemView.findViewById(R.id.tv_tong_chi_tieu);
        }
    }
}
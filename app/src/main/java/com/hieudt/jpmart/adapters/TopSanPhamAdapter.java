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
import com.hieudt.jpmart.entity.TopSanPham;

import java.util.List;

public class TopSanPhamAdapter extends RecyclerView.Adapter<TopSanPhamAdapter.ViewHolder> {
    private Context context;
    private List<TopSanPham> topSanPhamList;
    TopSanPham topSanPhamModel;

    public TopSanPhamAdapter(Context context, List<TopSanPham> topSanPhamList) {
        this.context = context;
        this.topSanPhamList = topSanPhamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top_san_pham, parent, false);
        return new TopSanPhamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        topSanPhamModel = topSanPhamList.get(position);

        holder.tvTenSanPham.setText(topSanPhamModel.getTenSanPham());
        holder.tvTongSoLuong.setText("Tổng số lượng: " + topSanPhamModel.getTongSoLuong());
    }

    @Override
    public int getItemCount() {
        return topSanPhamList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPham, tvTongSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSanPham = itemView.findViewById(R.id.tv_ten_san_pham);
            tvTongSoLuong = itemView.findViewById(R.id.tv_tong_so_luong);
        }
    }
}
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
import com.hieudt.jpmart.entity.HoaDonChiTiet;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HDCTAdapter extends RecyclerView.Adapter<HDCTAdapter.ViewHolder> {
    private Context context;
    private List<HoaDonChiTiet> hoaDonChiTietList;
    NumberFormat currencyFormat;
    HoaDonChiTiet hdctModel;

    public HDCTAdapter(Context context, List<HoaDonChiTiet> hoaDonChiTietList) {
        this.context = context;
        this.hoaDonChiTietList = hoaDonChiTietList;
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoa_don_chi_tiet, parent, false);
        return new HDCTAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        hdctModel = hoaDonChiTietList.get(position);

        holder.tvTenSanPham.setText(hdctModel.getTenSanPham());
        holder.tvGiaSanPham.setText(currencyFormat.format(hdctModel.getDonGia()));
        holder.tvSoLuong.setText("Số lượng: " + hdctModel.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return hoaDonChiTietList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPham, tvGiaSanPham, tvSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSanPham = itemView.findViewById(R.id.tv_ten_san_pham);
            tvGiaSanPham = itemView.findViewById(R.id.tv_gia_san_pham);
            tvSoLuong = itemView.findViewById(R.id.tv_so_luong);
        }
    }
}
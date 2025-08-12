package com.hieudt.jpmart.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.entity.HoaDon;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context context;
    private List<HoaDon> danhSachHoaDon;
    private HoaDon hoaDonModel;
    private OnHoaDonClickListener listener;

    public HoaDonAdapter(Context context, List<HoaDon> danhSachHoaDon) {
        this.context = context;
        this.danhSachHoaDon = danhSachHoaDon;
    }

    public interface OnHoaDonClickListener {
        void onDeleteHoaDon(HoaDon hoaDon);
        void onOpenHDCT(HoaDon hoaDon);
    }

    public void setOnHoaDonClickListener(OnHoaDonClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoa_don, parent, false);
        return new HoaDonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        hoaDonModel = danhSachHoaDon.get(position);

        holder.tvMaHoaDon.setText(hoaDonModel.getMaHoaDon());
        holder.tvTenNhanVien.setText(hoaDonModel.getMaNhanVien());
        holder.tvTenKhachHang.setText(hoaDonModel.getMaKhachHang());
        holder.tvNgayLap.setText(hoaDonModel.getNgayLap());
        holder.tvTongTien.setText(String.valueOf(hoaDonModel.getTongTien()));

        holder.btnXoaHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteHoaDon(hoaDonModel);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOpenHDCT(hoaDonModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSachHoaDon.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHoaDon, tvTenNhanVien, tvTenKhachHang, tvNgayLap, tvTongTien;
        ImageButton btnXoaHoaDon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaHoaDon = itemView.findViewById(R.id.tv_ma_hoa_don);
            tvTenNhanVien = itemView.findViewById(R.id.tv_ten_nhan_vien);
            tvTenKhachHang = itemView.findViewById(R.id.tv_ten_khach_hang);
            tvNgayLap = itemView.findViewById(R.id.tv_ngay_lap);
            tvTongTien = itemView.findViewById(R.id.tv_tong_tien);
            btnXoaHoaDon = itemView.findViewById(R.id.btn_xoa_hoa_don);
        }
    }
}
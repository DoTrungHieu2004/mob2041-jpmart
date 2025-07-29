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
import com.hieudt.jpmart.dao.NhanVienDAO;
import com.hieudt.jpmart.entity.NhanVien;
import com.hieudt.jpmart.interfaces.OnNhanVienClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {
    private final Context context;
    private final List<NhanVien> danhSachNhanVien;
    private OnNhanVienClickListener listener;
    NumberFormat currencyFormat;
    NhanVien nhanVienModel;
    NhanVienDAO dao;

    public NhanVienAdapter(Context context, List<NhanVien> danhSachNhanVien) {
        this.context = context;
        this.danhSachNhanVien = danhSachNhanVien;
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        dao = new NhanVienDAO(context);
    }

    public void setNhanVienListener(OnNhanVienClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_nhan_vien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        nhanVienModel = danhSachNhanVien.get(position);

        holder.tvMaNhanVien.setText(nhanVienModel.getMaNhanVien());
        holder.tvTenNhanVien.setText(nhanVienModel.getTenNhanVien());
        holder.tvDiaChiNhanVien.setText(nhanVienModel.getDiaChi());
        holder.tvLuong.setText(currencyFormat.format(nhanVienModel.getLuong()));
        holder.tvChucVu.setText(nhanVienModel.getChucVu() == 0 ? "Nhân viên" : "Quản lý");

        holder.btnSuaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEditNhanVien(nhanVienModel);
            }
        });

        holder.btnXoaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteNhanVien(nhanVienModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSachNhanVien.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaNhanVien, tvTenNhanVien, tvDiaChiNhanVien, tvLuong, tvChucVu;
        ImageButton btnSuaNhanVien, btnXoaNhanVien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaNhanVien = itemView.findViewById(R.id.tv_ma_nhan_vien);
            tvTenNhanVien = itemView.findViewById(R.id.tv_ten_nhan_vien);
            tvDiaChiNhanVien = itemView.findViewById(R.id.tv_dia_chi_nhan_vien);
            tvLuong = itemView.findViewById(R.id.tv_luong);
            tvChucVu = itemView.findViewById(R.id.tv_chuc_vu);
            btnSuaNhanVien = itemView.findViewById(R.id.btn_sua_nhan_vien);
            btnXoaNhanVien = itemView.findViewById(R.id.btn_xoa_nhan_vien);
        }
    }
}
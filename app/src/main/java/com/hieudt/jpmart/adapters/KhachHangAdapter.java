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
import com.hieudt.jpmart.dao.KhachHangDAO;
import com.hieudt.jpmart.entity.KhachHang;
import com.hieudt.jpmart.interfaces.OnKhachHangClickListener;

import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    private Context context;
    private List<KhachHang> khachHangList;
    private OnKhachHangClickListener listener;
    KhachHang khachHangModel;
    KhachHangDAO dao;

    public KhachHangAdapter(Context context, List<KhachHang> khachHangList) {
        this.context = context;
        this.khachHangList = khachHangList;
        dao = new KhachHangDAO(context);
    }

    public void setOnKhachHangClickListener(OnKhachHangClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khach_hang, parent, false);
        return new KhachHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        khachHangModel = khachHangList.get(position);

        holder.tvMaKhachHang.setText(khachHangModel.getMaKhachHang());
        holder.tvTenKhachHang.setText(khachHangModel.getTenKhachHang());
        holder.tvDiaChiKH.setText(khachHangModel.getDiaChi());
        holder.tvSoDienThoai.setText(khachHangModel.getSoDienThoai());
        holder.tvEmail.setText(khachHangModel.getEmail());

        holder.btnSuaKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEditKhachHang(khachHangModel);
            }
        });

        holder.btnXoaKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteKhachHang(khachHangModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return khachHangList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaKhachHang, tvTenKhachHang, tvDiaChiKH, tvSoDienThoai, tvEmail;
        ImageButton btnSuaKhachHang, btnXoaKhachHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaKhachHang = itemView.findViewById(R.id.tv_ma_khach_hang);
            tvTenKhachHang = itemView.findViewById(R.id.tv_ten_khach_hang);
            tvDiaChiKH = itemView.findViewById(R.id.tv_dia_chi_kh);
            tvSoDienThoai = itemView.findViewById(R.id.tv_so_dien_thoai);
            tvEmail = itemView.findViewById(R.id.tv_email);
            btnSuaKhachHang = itemView.findViewById(R.id.btn_sua_khach_hang);
            btnXoaKhachHang = itemView.findViewById(R.id.btn_xoa_khach_hang);
        }
    }
}
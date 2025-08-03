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
import com.hieudt.jpmart.dao.DanhMucDAO;
import com.hieudt.jpmart.entity.DanhMuc;

import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    private Context context;
    private List<DanhMuc> danhSachDanhMuc;
    private OnDanhMucClickListener listener;
    DanhMuc danhMucModel;
    DanhMucDAO dao;

    public DanhMucAdapter(Context context, List<DanhMuc> danhSachDanhMuc) {
        this.context = context;
        this.danhSachDanhMuc = danhSachDanhMuc;
        dao = new DanhMucDAO(context);
    }

    public interface OnDanhMucClickListener {
        void onEditDahMuc(DanhMuc danhMuc);
        void onDeleteDanhMuc(DanhMuc danhMuc);
    }

    public void setOnDanhMucClickListener(OnDanhMucClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danh_muc, parent, false);
        return new DanhMucAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        danhMucModel = danhSachDanhMuc.get(position);

        holder.tvMaDanhMuc.setText(danhMucModel.getMaDanhMuc());
        holder.tvTenDanhMuc.setText(danhMucModel.getTenDanhMuc());

        holder.btnSuaDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEditDahMuc(danhMucModel);
            }
        });

        holder.btnXoaDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteDanhMuc(danhMucModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSachDanhMuc.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaDanhMuc, tvTenDanhMuc;
        ImageButton btnSuaDanhMuc, btnXoaDanhMuc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaDanhMuc = itemView.findViewById(R.id.tv_ma_danh_muc);
            tvTenDanhMuc = itemView.findViewById(R.id.tv_ten_danh_muc);
            btnSuaDanhMuc = itemView.findViewById(R.id.btn_sua_danh_muc);
            btnXoaDanhMuc = itemView.findViewById(R.id.btn_xoa_danh_muc);
        }
    }
}
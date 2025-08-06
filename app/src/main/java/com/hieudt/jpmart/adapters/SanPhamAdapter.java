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
import com.hieudt.jpmart.entity.SanPham;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private List<SanPham> danhSachSanPham;
    private OnSanPhamClickListener listener;
    SanPham sanPhamModel;
    NumberFormat currencyFormat;

    public interface OnSanPhamClickListener {
        void onAddToCartSanPham(SanPham sanPham, View iconGioHangItem);
        void onEditSanPham(SanPham sanPham);
        void onDeleteSanPham(SanPham sanPham);
    }

    public SanPhamAdapter(Context context, List<SanPham> danhSachSanPham) {
        this.context = context;
        this.danhSachSanPham = danhSachSanPham;
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    public void setOnSanPhamClickListener(OnSanPhamClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_san_pham, parent, false);
        return new SanPhamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        sanPhamModel = danhSachSanPham.get(position);

        holder.tvTenSanPham.setText(sanPhamModel.getTenSanPham());
        holder.tvGiaSanPham.setText(currencyFormat.format(sanPhamModel.getGiaSanPham()));
        holder.tvTonKho.setText("Tồn kho: " + sanPhamModel.getSoLuong());

        holder.btnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddToCartSanPham(sanPhamModel, holder.btnThemVaoGioHang);
            }
        });

        holder.btnSuaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEditSanPham(sanPhamModel);
            }
        });

        holder.btnXoaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteSanPham(sanPhamModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSachSanPham.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPham, tvGiaSanPham, tvTonKho;
        ImageButton btnThemVaoGioHang, btnSuaSanPham, btnXoaSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSanPham = itemView.findViewById(R.id.tv_ten_san_pham);
            tvGiaSanPham = itemView.findViewById(R.id.tv_gia_san_pham);
            tvTonKho = itemView.findViewById(R.id.tv_ton_kho);
            btnThemVaoGioHang = itemView.findViewById(R.id.btn_them_vao_gio_hang);
            btnSuaSanPham = itemView.findViewById(R.id.btn_sua_san_pham);
            btnXoaSanPham = itemView.findViewById(R.id.btn_xoa_san_pham);
        }
    }
}
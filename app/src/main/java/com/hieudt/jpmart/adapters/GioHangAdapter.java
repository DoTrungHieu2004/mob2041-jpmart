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
import com.hieudt.jpmart.entity.GioHangItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private Context context;
    private List<GioHangItem> gioHangItems;
    private OnSanPhamClickListener listener;
    NumberFormat currencyFormat;
    GioHangItem gioHangItemModel;

    public GioHangAdapter(Context context, List<GioHangItem> gioHangItems) {
        this.context = context;
        this.gioHangItems = gioHangItems;
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    public interface OnSanPhamClickListener {
        void onDeleteSanPham(GioHangItem gioHangItem);
        void onIncrease(GioHangItem gioHangItem);
        void onDecrease(GioHangItem gioHangItem);
    }

    public void setOnSanPhamClickListener(OnSanPhamClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_gio_hang, parent, false);
        return new GioHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        gioHangItemModel = gioHangItems.get(position);

        holder.tvTenSanPhamCart.setText(gioHangItemModel.getSanPham().getMaSanPham());
        holder.tvGiaSanPhamCart.setText(currencyFormat.format(gioHangItemModel.getSanPham().getGiaSanPham()));
        holder.tvSoLuongGioHang.setText(String.valueOf(gioHangItemModel.getSoLuong()));

        holder.btnXoaGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteSanPham(gioHangItemModel);
            }
        });

        holder.btnUpGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIncrease(gioHangItemModel);
            }
        });

        holder.btnDownGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDecrease(gioHangItemModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPhamCart, tvGiaSanPhamCart, tvSoLuongGioHang;
        ImageButton btnXoaGioHang, btnUpGioHang, btnDownGioHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSanPhamCart = itemView.findViewById(R.id.tv_ten_san_pham_cart);
            tvGiaSanPhamCart = itemView.findViewById(R.id.tv_gia_san_pham_cart);
            tvSoLuongGioHang = itemView.findViewById(R.id.tv_so_luong_gio_hang);
            btnXoaGioHang = itemView.findViewById(R.id.btn_xoa_gio_hang);
            btnUpGioHang = itemView.findViewById(R.id.btn_up_gio_hang);
            btnDownGioHang = itemView.findViewById(R.id.btn_down_gio_hang);
        }
    }
}
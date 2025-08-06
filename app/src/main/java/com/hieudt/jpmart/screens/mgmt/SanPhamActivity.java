package com.hieudt.jpmart.screens.mgmt;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hieudt.jpmart.R;
import com.hieudt.jpmart.adapters.SanPhamAdapter;
import com.hieudt.jpmart.common.Common;
import com.hieudt.jpmart.dao.SanPhamDAO;
import com.hieudt.jpmart.entity.GioHang;
import com.hieudt.jpmart.entity.SanPham;
import com.hieudt.jpmart.screens.cart.GioHangActivity;
import com.hieudt.jpmart.screens.edit.EditSanPhamActivity;

import java.util.List;
import java.util.Objects;

public class SanPhamActivity extends AppCompatActivity implements SanPhamAdapter.OnSanPhamClickListener {
    private EditText edtTimKiemSP;
    private FrameLayout flItemCart;
    private RecyclerView rcSanPham;
    TextView tvSoLuong;
    private FloatingActionButton fabThemSanPham;
    private Toolbar toolbar;
    public static final String SAN_PHAM = "SAN_PHAM";
    private List<SanPham> danhSachSanPham;
    private SanPhamAdapter adapter;
    private SanPhamDAO dao;
    GioHang gioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        edtTimKiemSP = findViewById(R.id.edt_tim_kiem_sp);
        flItemCart = findViewById(R.id.fl_item_cart);
        rcSanPham = findViewById(R.id.rc_san_pham);
        tvSoLuong = findViewById(R.id.tv_so_luong);
        fabThemSanPham = findViewById(R.id.fab_them_san_pham);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gioHang = Common.getGioHang();

        dao = new SanPhamDAO(this);

        danhSachSanPham = dao.timKiemSanPham("");

        adapter = new SanPhamAdapter(this, danhSachSanPham);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcSanPham.setLayoutManager(llm);
        adapter.setOnSanPhamClickListener(this);
        rcSanPham.setAdapter(adapter);

        fabThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SanPhamActivity.this, EditSanPhamActivity.class);
                intent.putExtra("Type", 1);
                startActivity(intent);
            }
        });

        edtTimKiemSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tuKhoa = charSequence.toString();
                timKiemSanPham(tuKhoa);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        flItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhatDanhSachSanPham();
        tvSoLuong.setText(String.valueOf(gioHang.getDanhSachGioHang().size()));
    }

    @Override
    public void onAddToCartSanPham(SanPham sanPham, View iconGioHangItem) {
        gioHang.addSanPham(sanPham);
        tvSoLuong.setText(String.valueOf(gioHang.getDanhSachGioHang().size()));

        // Gọi hàm thực hiện hiệu ứng animation
        animateAddToCart(iconGioHangItem, flItemCart);
    }

    @Override
    public void onEditSanPham(SanPham sanPham) {
        Intent intent = new Intent(this, EditSanPhamActivity.class);
        intent.putExtra("Type", 0);
        intent.putExtra(SAN_PHAM, sanPham);

        startActivity(intent);
    }

    @Override
    public void onDeleteSanPham(SanPham sanPham) {
        dao = new SanPhamDAO(this);
        boolean isDeleted = dao.xoaSanPham(sanPham.getMaSanPham());
        if (isDeleted) {
            danhSachSanPham.remove(sanPham);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void timKiemSanPham(String tuKhoa) {
        danhSachSanPham.clear();
        danhSachSanPham.addAll(dao.timKiemSanPham(tuKhoa));
        adapter.notifyDataSetChanged();
    }

    private void capNhatDanhSachSanPham() {
        danhSachSanPham.clear();
        danhSachSanPham.addAll(dao.timKiemSanPham("")); // Lấy dữ liệu mới từ SQLite
        adapter.notifyDataSetChanged();
    }

    private void animateAddToCart(View startView, View cartIcon) {
        // Lấy vị trí của icon giỏ hàng và sản phẩm
        int[] startLoc = new int[2];
        int[] endLoc = new int[2];
        startView.getLocationOnScreen(startLoc);
        cartIcon.getLocationOnScreen(endLoc);

        // Tạo ImageView giả lập sản phẩm
        ImageView animationView = new ImageView(this);
        animationView.setImageDrawable(((ImageView) startView).getDrawable());

        // LayoutParams cho animation
        ViewGroup rootLayout = (ViewGroup) getWindow().getDecorView();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(startView.getWidth(), startView.getHeight());
        params.leftMargin = startLoc[0];
        params.topMargin = startLoc[1];
        rootLayout.addView(animationView, params);

        // Tạo Animation phóng to ban đầu
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(animationView, "scaleX", 1f, 5f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(animationView, "scaleY", 1f, 5f);
        AnimatorSet scaleUpSet = new AnimatorSet();
        scaleUpSet.playTogether(scaleUpX, scaleUpY);
        scaleUpSet.setDuration(300);
    }
}
package com.hieudt.jpmart.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.screens.mgmt.DanhMucActivity;
import com.hieudt.jpmart.screens.mgmt.KhachHangActivity;
import com.hieudt.jpmart.screens.mgmt.NhanVienActivity;
import com.hieudt.jpmart.screens.mgmt.SanPhamActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    int chucVu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ln_nhan_vien).setOnClickListener(this);
        findViewById(R.id.ln_khach_hang).setOnClickListener(this);
        findViewById(R.id.ln_danh_muc).setOnClickListener(this);
        findViewById(R.id.ln_san_pham).setOnClickListener(this);
        findViewById(R.id.ln_doi_mat_khau).setOnClickListener(this);
        findViewById(R.id.ln_dang_xuat).setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Nhận dữ liệu từ Intent
        chucVu = getIntent().getIntExtra("CHUC_VU", 0);
        if (chucVu == 0) {
            findViewById(R.id.ln_thong_ke).setVisibility(View.GONE);
            findViewById(R.id.ln_nhan_vien).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (chucVu == 0) {
            MenuItem doanhThuItem = menu.findItem(R.id.menu_doanh_thu);
            if (doanhThuItem != null) {
                doanhThuItem.setVisible(false);
            }

            MenuItem topSanPhamItem = menu.findItem(R.id.menu_top_san_pham);
            if (topSanPhamItem != null) {
                topSanPhamItem.setVisible(false);
            }

            MenuItem topKhachHangItem = menu.findItem(R.id.menu_top_khach_hang);
            if (topKhachHangItem != null) {
                topKhachHangItem.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_doi_mat_khau) {
            startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
            return true;
        } else if (id == R.id.menu_dang_xuat) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ln_nhan_vien) {
            startActivity(new Intent(MainActivity.this, NhanVienActivity.class));
        } else if (id == R.id.ln_khach_hang) {
            startActivity(new Intent(MainActivity.this, KhachHangActivity.class));
        } else if (id == R.id.ln_danh_muc) {
            startActivity(new Intent(MainActivity.this, DanhMucActivity.class));
        } else if (id == R.id.ln_san_pham) {
            startActivity(new Intent(MainActivity.this, SanPhamActivity.class));
        } else if (id == R.id.ln_doi_mat_khau) {
            startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
        } else if (id == R.id.ln_dang_xuat) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}
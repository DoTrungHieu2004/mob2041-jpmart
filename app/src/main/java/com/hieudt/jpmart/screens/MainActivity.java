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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    int chucVu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
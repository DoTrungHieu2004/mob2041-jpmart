package com.hieudt.jpmart.screens.mgmt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.adapters.HDCTAdapter;
import com.hieudt.jpmart.dao.HoaDonChiTietDAO;

import java.util.Objects;

public class HoaDonChiTietActivity extends AppCompatActivity {
    private RecyclerView rcHoaDonChiTiet;
    private Toolbar toolbar;
    private HoaDonChiTietDAO dao;
    private HDCTAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);

        rcHoaDonChiTiet = findViewById(R.id.rc_hoa_don_chi_tiet);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Hóa đơn chi tiết");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new HoaDonChiTietDAO(this);
        String maHoaDon = getIntent().getStringExtra("ma_hoa_don");

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcHoaDonChiTiet.setLayoutManager(llm);
        adapter = new HDCTAdapter(this, dao.layTatCaHDCT(maHoaDon));
        rcHoaDonChiTiet.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
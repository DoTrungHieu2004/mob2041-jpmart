package com.hieudt.jpmart.screens.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.adapters.HoaDonAdapter;
import com.hieudt.jpmart.dao.HoaDonDAO;
import com.hieudt.jpmart.entity.HoaDon;

import java.util.List;
import java.util.Objects;

public class HoaDonActivity extends AppCompatActivity implements HoaDonAdapter.OnHoaDonClickListener {
    private RecyclerView rcHoaDon;
    private Toolbar toolbar;
    private HoaDonAdapter adapter;
    private List<HoaDon> danhSachHoaDon;
    private HoaDonDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        rcHoaDon = findViewById(R.id.rc_hoa_don);
        toolbar = findViewById(R.id.toolbar);

        dao = new HoaDonDAO(this);
        danhSachHoaDon = dao.layTatCaHoaDon();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý hóa đơn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcHoaDon.setLayoutManager(llm);
        adapter = new HoaDonAdapter(this, danhSachHoaDon);
        adapter.setOnHoaDonClickListener(this);
        rcHoaDon.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDeleteHoaDon(HoaDon hoaDon) {
        dao = new HoaDonDAO(this);
        boolean isDeleted = dao.xoaHoaDon(hoaDon.getMaHoaDon());
        if (isDeleted) {
            danhSachHoaDon.remove(hoaDon);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa hóa đơn thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa hóa đơn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOpenHDCT(HoaDon hoaDon) {
        Intent intent = new Intent(this, HoaDonChiTietActivity.class);
        intent.putExtra("ma_hoa_don", hoaDon.getMaHoaDon());
        startActivity(intent);
    }
}
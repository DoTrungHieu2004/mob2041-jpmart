package com.hieudt.jpmart.screens.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hieudt.jpmart.R;
import com.hieudt.jpmart.adapters.KhachHangAdapter;
import com.hieudt.jpmart.dao.KhachHangDAO;
import com.hieudt.jpmart.entity.KhachHang;
import com.hieudt.jpmart.interfaces.OnKhachHangClickListener;
import com.hieudt.jpmart.screens.edit.EditKhachHangActivity;

import java.util.List;
import java.util.Objects;

public class KhachHangActivity extends AppCompatActivity implements OnKhachHangClickListener {
    private RecyclerView rcKhachHang;
    private FloatingActionButton fabThemKhachHang;
    private Toolbar toolbar;
    public static final String KHACH_HANG = "KHACH_HANG";
    private KhachHangAdapter adapter;
    private List<KhachHang> danhSachKhachHang;
    private KhachHangDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        rcKhachHang = findViewById(R.id.rc_khach_hang);
        fabThemKhachHang = findViewById(R.id.fab_them_khach_hang);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý khách hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new KhachHangDAO(this);
        danhSachKhachHang = dao.layTatCaKhachHang();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcKhachHang.setLayoutManager(llm);
        adapter = new KhachHangAdapter(this, danhSachKhachHang);
        adapter.setOnKhachHangClickListener(this);
        rcKhachHang.setAdapter(adapter);

        fabThemKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhachHangActivity.this, EditKhachHangActivity.class);
                intent.putExtra("Type", 1);
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
        danhSachKhachHang.clear();
        danhSachKhachHang.addAll(dao.layTatCaKhachHang());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEditKhachHang(KhachHang khachHang) {
        Intent intent = new Intent(KhachHangActivity.this, EditKhachHangActivity.class);
        intent.putExtra("Type", 0);
        intent.putExtra(KHACH_HANG, khachHang);

        startActivity(intent);
    }

    @Override
    public void onDeleteKhachHang(KhachHang khachHang) {
        boolean isDeleted = dao.xoaKhachHang(khachHang.getMaKhachHang());
        if (isDeleted) {
            danhSachKhachHang.remove(khachHang);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa khách hàng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa khách hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
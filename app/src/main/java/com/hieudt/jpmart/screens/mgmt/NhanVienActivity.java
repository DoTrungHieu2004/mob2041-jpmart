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
import com.hieudt.jpmart.adapters.NhanVienAdapter;
import com.hieudt.jpmart.dao.NhanVienDAO;
import com.hieudt.jpmart.entity.NhanVien;
import com.hieudt.jpmart.screens.edit.EditNhanVienActivity;

import java.util.List;
import java.util.Objects;

public class NhanVienActivity extends AppCompatActivity implements NhanVienAdapter.OnNhanVienClickListener {
    private RecyclerView rcNhanVien;
    private FloatingActionButton fabThemNhanVien;
    private Toolbar toolbar;
    public static final String NHAN_VIEN = "NHAN_VIEN";
    private NhanVienAdapter adapter;
    private List<NhanVien> danhSachNhanVien;
    private NhanVienDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        rcNhanVien = findViewById(R.id.rc_nhan_vien);
        fabThemNhanVien = findViewById(R.id.fab_them_nhan_vien);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý nhân viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new NhanVienDAO(this);

        danhSachNhanVien = dao.layTatCaNhanVien();

        adapter = new NhanVienAdapter(this, danhSachNhanVien);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcNhanVien.setLayoutManager(llm);
        adapter.setNhanVienListener(this);
        rcNhanVien.setAdapter(adapter);

        fabThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhanVienActivity.this, EditNhanVienActivity.class);
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
        danhSachNhanVien.clear();
        danhSachNhanVien.addAll(dao.layTatCaNhanVien());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEditNhanVien(NhanVien nhanVien) {
        Intent intent = new Intent(this, EditNhanVienActivity.class);
        intent.putExtra("Type", 0);
        intent.putExtra(NHAN_VIEN, nhanVien);

        startActivity(intent);
    }

    @Override
    public void onDeleteNhanVien(NhanVien nhanVien) {
        dao = new NhanVienDAO(this);
        boolean isDeleted = dao.xoaNhanVien(nhanVien.getMaNhanVien());
        if (isDeleted) {
            danhSachNhanVien.remove(nhanVien);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa nhân viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
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
import com.hieudt.jpmart.adapters.DanhMucAdapter;
import com.hieudt.jpmart.dao.DanhMucDAO;
import com.hieudt.jpmart.entity.DanhMuc;
import com.hieudt.jpmart.screens.edit.EditDanhMucActivity;

import java.util.List;
import java.util.Objects;

public class DanhMucActivity extends AppCompatActivity implements DanhMucAdapter.OnDanhMucClickListener {
    private RecyclerView rcDanhMuc;
    private FloatingActionButton fabThemDanhMuc;
    private Toolbar toolbar;
    public static final String DANH_MUC = "DANH_MUC";
    private DanhMucAdapter adapter;
    private List<DanhMuc> danhSachDanhMuc;
    private DanhMucDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

        rcDanhMuc = findViewById(R.id.rc_danh_muc);
        fabThemDanhMuc = findViewById(R.id.fab_them_danh_muc);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Quản lý daạh mục");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new DanhMucDAO(this);
        danhSachDanhMuc = dao.layTatCaDanhMuc();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcDanhMuc.setLayoutManager(llm);
        adapter = new DanhMucAdapter(this, danhSachDanhMuc);
        adapter.setOnDanhMucClickListener(this);
        rcDanhMuc.setAdapter(adapter);

        fabThemDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhMucActivity.this, EditDanhMucActivity.class);
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
        danhSachDanhMuc.clear();
        danhSachDanhMuc.addAll(dao.layTatCaDanhMuc());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEditDahMuc(DanhMuc danhMuc) {
        Intent intent = new Intent(DanhMucActivity.this, EditDanhMucActivity.class);
        intent.putExtra("Type", 0);
        intent.putExtra(DANH_MUC, danhMuc);

        startActivity(intent);
    }

    @Override
    public void onDeleteDanhMuc(DanhMuc danhMuc) {
        dao = new DanhMucDAO(this);
        boolean isDeleted = dao.xoaDanhMuc(danhMuc.getMaDanhMuc());
        if (isDeleted) {
            danhSachDanhMuc.remove(danhMuc);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa danh mục thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa danh mục thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
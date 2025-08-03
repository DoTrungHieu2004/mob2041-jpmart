package com.hieudt.jpmart.screens.edit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.hieudt.jpmart.R;
import com.hieudt.jpmart.dao.DanhMucDAO;
import com.hieudt.jpmart.entity.DanhMuc;
import com.hieudt.jpmart.screens.mgmt.DanhMucActivity;

public class EditDanhMucActivity extends AppCompatActivity {
    private EditText edtMaDanhMuc;
    private TextInputEditText edtTenDanhMuc;
    private AppCompatButton btnLuuItemDM, btnHuyItemDM;
    private LinearLayout layoutMaDanhMuc;
    private DanhMucDAO dao;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_danh_muc);

        edtMaDanhMuc = findViewById(R.id.edt_ma_danh_muc);
        edtTenDanhMuc = findViewById(R.id.edt_ten_danh_muc);
        btnLuuItemDM = findViewById(R.id.btn_luu_item_dm);
        btnHuyItemDM = findViewById(R.id.btn_huy_item_dm);
        layoutMaDanhMuc = findViewById(R.id.layout_ma_danh_muc);

        dao = new DanhMucDAO(this);

        type = getIntent().getIntExtra("Type", -1);
        if (type == 0) {    // Sửa
            edtMaDanhMuc.setEnabled(false);
            DanhMuc danhMuc = getIntent().getParcelableExtra(DanhMucActivity.DANH_MUC);
            edtMaDanhMuc.setText(danhMuc.getMaDanhMuc());
            edtTenDanhMuc.setText(danhMuc.getTenDanhMuc());
        } else if (type == 1) { // Tạo mới
            layoutMaDanhMuc.setVisibility(View.GONE);
        }

        btnLuuItemDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luuDanhMuc();
            }
        });

        btnHuyItemDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void luuDanhMuc() {
        String maDanhMuc = edtMaDanhMuc.getText().toString().trim();
        String tenDanhMuc = edtTenDanhMuc.getText().toString().trim();

        boolean isOK;
        if (type == 0) {    // Sửa
            DanhMuc danhMuc = new DanhMuc(maDanhMuc, tenDanhMuc);
            isOK = dao.suaDanhMuc(danhMuc);
        } else {    // Thêm mới
            maDanhMuc = dao.taoMaDanhMucMoi();
            DanhMuc danhMuc = new DanhMuc(maDanhMuc, tenDanhMuc);
            isOK = dao.themDanhMuc(danhMuc);
        }

        if (isOK) {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + "danh mục thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + "danh mục thất bại", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
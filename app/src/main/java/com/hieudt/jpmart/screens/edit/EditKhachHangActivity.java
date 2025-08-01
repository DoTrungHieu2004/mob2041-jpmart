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
import com.hieudt.jpmart.dao.KhachHangDAO;
import com.hieudt.jpmart.entity.KhachHang;
import com.hieudt.jpmart.screens.mgmt.KhachHangActivity;

public class EditKhachHangActivity extends AppCompatActivity {
    private EditText edtMaKhachHang;
    private TextInputEditText edtTenKhachHang, edtDiaChiKH, edtSoDienThoai, edtEmail;
    private AppCompatButton btnLuuItemKH, btnHuyItemKH;
    private LinearLayout layoutMaKhachHang;
    int type;
    private KhachHangDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_khach_hang);

        edtMaKhachHang = findViewById(R.id.edt_ma_khach_hang);
        edtTenKhachHang = findViewById(R.id.edt_ten_khach_hang);
        edtDiaChiKH = findViewById(R.id.edt_dia_chi_kh);
        edtSoDienThoai = findViewById(R.id.edt_so_dien_thoai);
        edtEmail = findViewById(R.id.edt_email);
        btnLuuItemKH = findViewById(R.id.btn_luu_item_kh);
        btnHuyItemKH = findViewById(R.id.btn_huy_item_kh);
        layoutMaKhachHang = findViewById(R.id.layout_ma_khach_hang);

        dao = new KhachHangDAO(this);

        type = getIntent().getIntExtra("Type", -1);
        if (type == 0) {    // Sửa
            edtMaKhachHang.setEnabled(false);
            KhachHang khachHang = getIntent().getParcelableExtra(KhachHangActivity.KHACH_HANG);
            edtMaKhachHang.setText(khachHang.getMaKhachHang());
            edtTenKhachHang.setText(khachHang.getTenKhachHang());
            edtDiaChiKH.setText(khachHang.getDiaChi());
            edtSoDienThoai.setText(khachHang.getSoDienThoai());
            edtEmail.setText(khachHang.getEmail());
        } else if (type == 1) { // Thêm
            layoutMaKhachHang.setVisibility(View.GONE);
        }

        btnLuuItemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luuKhachHang();
            }
        });

        btnHuyItemKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void luuKhachHang() {
        String maKhachHang = edtMaKhachHang.getText().toString().trim();
        String tenKhachHang = edtTenKhachHang.getText().toString().trim();
        String diaChi = edtDiaChiKH.getText().toString().trim();
        String soDienThoai = edtSoDienThoai.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        boolean isOK;
        if (type == 0) {
            KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, diaChi, soDienThoai, email);
            isOK = dao.suaKhachHang(khachHang);
        } else {
            maKhachHang = dao.taoMaKhachHangMoi();
            KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, diaChi, soDienThoai, email);
            isOK = dao.themKhachHang(khachHang);
        }

        if (isOK) {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + "khách hàng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + "khách hàng thất bại", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
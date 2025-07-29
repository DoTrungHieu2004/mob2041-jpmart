package com.hieudt.jpmart.screens.edit;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.hieudt.jpmart.R;
import com.hieudt.jpmart.dao.NhanVienDAO;
import com.hieudt.jpmart.entity.ChucVu;
import com.hieudt.jpmart.entity.NhanVien;
import com.hieudt.jpmart.screens.mgmt.NhanVienActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditNhanVienActivity extends AppCompatActivity {
    private EditText edtMaNhanVien;
    private TextInputEditText edtTenNhanVien, edtDiaChiNV, edtLuong, edtMatKhau;
    private Spinner spChucVu;
    private LinearLayout layoutMaNhanVien;
    private AppCompatButton btnLuuItemNV, btnHuyItemNV;
    private List<ChucVu> chucVuList;
    private NhanVienDAO dao;
    int type;
    NumberFormat currencyFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nhan_vien);

        edtMaNhanVien = findViewById(R.id.edt_ma_nhan_vien);
        edtTenNhanVien = findViewById(R.id.edt_ten_nhan_vien);
        edtDiaChiNV = findViewById(R.id.edt_dia_chi_nv);
        edtLuong = findViewById(R.id.edt_luong);
        edtMatKhau = findViewById(R.id.edt_mat_khau);
        spChucVu = findViewById(R.id.sp_chuc_vu);
        layoutMaNhanVien = findViewById(R.id.layout_ma_nhan_vien);
        btnLuuItemNV = findViewById(R.id.btn_luu_item_nv);
        btnHuyItemNV = findViewById(R.id.btn_huy_item_nv);

        dao = new NhanVienDAO(this);
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));

        chucVuList = new ArrayList<>();
        chucVuList.add(new ChucVu(0, "Quản lý"));
        chucVuList.add(new ChucVu(1, "Nhân viên"));
        ArrayAdapter<ChucVu> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, chucVuList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChucVu.setAdapter(adapter);

        type = getIntent().getIntExtra("Type", -1);
        if (type == 0) {
            edtMaNhanVien.setEnabled(false);
            NhanVien nhanVien = getIntent().getParcelableExtra(NhanVienActivity.NHAN_VIEN);
            edtMaNhanVien.setText(nhanVien.getMaNhanVien());
            edtTenNhanVien.setText(nhanVien.getTenNhanVien());
            edtDiaChiNV.setText(nhanVien.getDiaChi());
            edtLuong.setText(currencyFormat.format(nhanVien.getLuong()));
            edtMatKhau.setText(nhanVien.getMatKhau());
            setSelectedChucVu(nhanVien.getChucVu());
        } else if (type == 1) { // Thêm
            layoutMaNhanVien.setVisibility(View.GONE);
        }

        btnLuuItemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luuNhanVien();
            }
        });

        btnHuyItemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setSelectedChucVu(int chucVu) {
        for (int i = 0; i < chucVuList.size(); i++) {
            if (chucVuList.get(i).getChucVuCode() == chucVu) {
                spChucVu.setSelection(i);
                break;
            }
        }
    }

    private void luuNhanVien() {
        String maNhanVien = edtMaNhanVien.getText().toString().trim();
        String tenNhanVien = edtTenNhanVien.getText().toString().trim();
        String diaChi = edtDiaChiNV.getText().toString().trim();
        int chucVu = ((ChucVu) spChucVu.getSelectedItem()).getChucVuCode();
        double luong = Double.parseDouble(edtLuong.getText().toString().replaceAll("[^\\d]", "").trim());
        String matKhau = edtMatKhau.getText().toString().trim();

        boolean isOK = false;
        if (type == 0) { // Sửa
            NhanVien nhanVien = new NhanVien(maNhanVien, tenNhanVien, diaChi, chucVu, luong, matKhau);
            isOK = dao.suaNhanVien(nhanVien);
        } else {
            maNhanVien = dao.taoMaNhanVienMoi();
            NhanVien nhanVien = new NhanVien(maNhanVien, tenNhanVien, diaChi, chucVu, luong, matKhau);
            isOK = dao.themNhanVien(nhanVien);
        }

        if (isOK) {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + " nhân viên thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + " nhân viên thất bại", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
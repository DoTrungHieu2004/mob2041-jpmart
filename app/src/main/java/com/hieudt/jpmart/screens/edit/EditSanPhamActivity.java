package com.hieudt.jpmart.screens.edit;

import android.content.Intent;
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
import com.hieudt.jpmart.dao.DanhMucDAO;
import com.hieudt.jpmart.dao.SanPhamDAO;
import com.hieudt.jpmart.entity.DanhMuc;
import com.hieudt.jpmart.entity.SanPham;
import com.hieudt.jpmart.screens.mgmt.SanPhamActivity;

import java.util.List;

public class EditSanPhamActivity extends AppCompatActivity {
    private EditText edtMaSanPham;
    private Spinner spDanhMuc;
    private TextInputEditText edtTenSanPham, edtGiaSanPham, edtSoLuong, edtDonViTinh, edtNgayNhap;
    private LinearLayout layoutMaSanPham;
    private AppCompatButton btnLuuItemSP, btnHuyItemSP;
    private List<DanhMuc> danhMucList;
    private SanPhamDAO sanPhamDAO;
    private DanhMucDAO danhMucDAO;
    private String maSanPham;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_san_pham);

        edtMaSanPham = findViewById(R.id.edt_ma_san_pham);
        spDanhMuc = findViewById(R.id.sp_danh_muc);
        edtTenSanPham = findViewById(R.id.edt_ten_san_pham);
        edtGiaSanPham = findViewById(R.id.edt_gia_san_pham);
        edtSoLuong = findViewById(R.id.edt_so_luong);
        edtDonViTinh = findViewById(R.id.edt_don_vi_tinh);
        edtNgayNhap = findViewById(R.id.edt_ngay_nhap);
        layoutMaSanPham = findViewById(R.id.layout_ma_san_pham);
        btnLuuItemSP = findViewById(R.id.btn_luu_item_sp);
        btnHuyItemSP = findViewById(R.id.btn_huy_item_sp);

        sanPhamDAO = new SanPhamDAO(this);
        danhMucDAO = new DanhMucDAO(this);

        danhMucList = danhMucDAO.layTatCaDanhMuc();
        ArrayAdapter<DanhMuc> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhMucList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDanhMuc.setAdapter(adapter);

        Intent intent = getIntent();
        type = intent.getIntExtra("Type", -1);
        if (type == 0) {    // Sửa
            edtMaSanPham.setEnabled(false);
            SanPham sanPham = intent.getParcelableExtra(SanPhamActivity.SAN_PHAM);
            edtMaSanPham.setText(sanPham.getMaSanPham());
            edtTenSanPham.setText(sanPham.getTenSanPham());
            edtGiaSanPham.setText(String.valueOf(sanPham.getGiaSanPham()));
            edtSoLuong.setText(String.valueOf(sanPham.getSoLuong()));
            edtDonViTinh.setText(sanPham.getDonViTinh());
            edtNgayNhap.setText(sanPham.getNgayNhap());
            setSelectedDanhMuc(sanPham.getMaDanhMuc());
        } else if (type == 1) { // Thêm
            layoutMaSanPham.setVisibility(View.GONE);
        }

        btnLuuItemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luuSanPham();
            }
        });

        btnHuyItemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setSelectedDanhMuc(String maDanhMucHienTai) {
        for (int i = 0; i < danhMucList.size(); i++) {
            if (danhMucList.get(i).getMaDanhMuc().equals(maDanhMucHienTai)) {
                spDanhMuc.setSelection(i);
                break;
            }
        }
    }

    private void luuSanPham() {
        maSanPham = edtMaSanPham.getText().toString().trim();
        String tenSanPham = edtTenSanPham.getText().toString().trim();
        int giaSanPham = Integer.parseInt(edtGiaSanPham.getText().toString().trim());
        int soLuong = Integer.parseInt(edtSoLuong.getText().toString().trim());
        String donViTinh = edtDonViTinh.getText().toString().trim();
        String ngayNhap = edtNgayNhap.getText().toString().trim();
        String maDanhMuc = ((DanhMuc) spDanhMuc.getSelectedItem()).getMaDanhMuc();

        boolean isOK;
        if (type == 0) {    // Sửa
            SanPham sanPham = new SanPham(maSanPham, tenSanPham, giaSanPham, soLuong, donViTinh, ngayNhap, maDanhMuc);
            isOK = sanPhamDAO.suaSanPham(sanPham);
        } else {    // Thêm mới
            maSanPham = sanPhamDAO.taoMaSanPhamMoi();
            SanPham sanPham = new SanPham(maSanPham, tenSanPham, giaSanPham, soLuong, donViTinh, ngayNhap, maDanhMuc);
            isOK = sanPhamDAO.themSanPham(sanPham);
        }

        if (isOK) {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + " sản phẩm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, (type == 0) ? "Cập nhật" : "Thêm" + " sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
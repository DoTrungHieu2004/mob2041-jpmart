package com.hieudt.jpmart.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.hieudt.jpmart.R;
import com.hieudt.jpmart.common.Common;
import com.hieudt.jpmart.sqlite.DBHelper;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {
    private TextInputEditText edtMatKhauCu, edtMatKhauMoi, edtNhapLaiMatKhau;
    private AppCompatButton btnLuuMatKhau, btnHuy;
    private Toolbar toolbar;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Ánh xạ các thành phần giao diện
        edtMatKhauCu = findViewById(R.id.edt_mat_khau_cu);
        edtMatKhauMoi = findViewById(R.id.edt_mat_khau_moi);
        edtNhapLaiMatKhau = findViewById(R.id.edt_nhap_lai_mat_khau);
        btnLuuMatKhau = findViewById(R.id.btn_luu_mat_khau);
        btnHuy = findViewById(R.id.btn_huy);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Đổi mật khẩu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new DBHelper(this);

        btnLuuMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiMatKhau();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMatKhauCu.setText("");
                edtMatKhauMoi.setText("");
                edtNhapLaiMatKhau.setText("");
            }
        });
    }

    private void doiMatKhau() {
        String oldPass = edtMatKhauCu.getText().toString().trim();
        String newPass = edtMatKhauMoi.getText().toString().trim();
        String confirmPass = edtNhapLaiMatKhau.getText().toString().trim();

        // Kiểm tra thông tin không được bỏ trống
        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu cũ có đúng không
        if (!helper.kiemTraMatKhauCu(Common.maNhanVien, oldPass)) {
            Toast.makeText(this, "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận phải giống nhau
        if (!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
        }

        // Nếu đúng, tiến hành cập nhật mật khẩu mới vào SQLite
        if (helper.capNhatMatKhauMoi(Common.maNhanVien, newPass)) {
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Đổi mật khẩu thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.hieudt.jpmart.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.hieudt.jpmart.R;
import com.hieudt.jpmart.common.Common;
import com.hieudt.jpmart.dao.NhanVienDAO;
import com.hieudt.jpmart.entity.NhanVien;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edtUsername, edtPassword;
    private CheckBox chkRemember;
    private AppCompatButton btnLogin;
    private SharedPreferences preferences;
    private NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ view
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        chkRemember = findViewById(R.id.chk_remember);
        btnLogin = findViewById(R.id.btn_login);

        nhanVienDAO = new NhanVienDAO(this);

        preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean isInit = preferences.getBoolean("init", false);
        if (!isInit) {
//            taoDuLieuNhanVien();
            preferences.edit().putBoolean("init", false);
        }

        preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        ghiNhoThongTinNguoiDung();  // Tự động lấy và điền username/password

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDangNhap();
            }
        });
    }

    private void ghiNhoThongTinNguoiDung() {
        String savedUsername = preferences.getString("username", "");
        String savedPassword = preferences.getString("password", "");
        boolean isRemembered = preferences.getBoolean("remember", false);

        edtUsername.setText(savedUsername);
        edtPassword.setText(savedPassword);
        chkRemember.setChecked(isRemembered);
    }

    private void checkDangNhap() {
        String maNhanVien = edtUsername.getText().toString().trim();
        String matKhau = edtPassword.getText().toString().trim();
        NhanVien nhanVien = nhanVienDAO.layNhanVienBangMaNV(maNhanVien);

        if (nhanVien != null) {
            if (nhanVien.getMatKhau().equals(matKhau)) {
                if (chkRemember.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", maNhanVien);
                    editor.putString("password", matKhau);
                    editor.putBoolean("remember", true);
                    editor.apply();
                } else {
                    preferences.edit().clear().apply();
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("CHUC_VU", nhanVien.getChucVu());
                startActivity(intent);
                Common.maNhanVien = maNhanVien;
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("error", "checkDangNhap: null" + maNhanVien);
            Toast.makeText(this, "Không tìm thấy nhân viên", Toast.LENGTH_SHORT).show();
        }
    }

    public void taoDuLieuNhanVien() {
        nhanVienDAO.themNhanVien(new NhanVien("NV1", "Đỗ Trung Hiếu", "Phường Từ Liêm, Hà Nội", 1, 25000000, "admin"));
        nhanVienDAO.themNhanVien(new NhanVien("NV2", "Trần Thị Cúc", "34 Lê Lợi, Quận 3, TP.HCM", 0, 12000000, "sales"));
    }
}
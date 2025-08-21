package com.hieudt.jpmart.screens.statistic;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.adapters.TopSanPhamAdapter;
import com.hieudt.jpmart.dao.ThongKeDAO;
import com.hieudt.jpmart.entity.TopSanPham;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class TopSanPhamActivity extends AppCompatActivity {
    private EditText edtTuNgay, edtDenNgay, edtSoLuong;
    private AppCompatButton btnTopSanPham;
    private TextView tvTopSanPham;
    private RecyclerView rcTopSanPham;
    private Toolbar toolbar;
    private ThongKeDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_san_pham);

        edtTuNgay = findViewById(R.id.edt_tu_ngay);
        edtDenNgay = findViewById(R.id.edt_den_ngay);
        edtSoLuong = findViewById(R.id.edt_so_luong);
        btnTopSanPham = findViewById(R.id.btn_top_san_pham);
        tvTopSanPham = findViewById(R.id.tv_top_san_pham);
        rcTopSanPham = findViewById(R.id.rc_top_san_pham);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Thống kê sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new ThongKeDAO(this);

        edtTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(edtTuNgay);
            }
        });

        edtDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(edtDenNgay);
            }
        });

        btnTopSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTuNgay.getText().toString().isEmpty()
                        || edtDenNgay.getText().toString().isEmpty()
                        || edtSoLuong.getText().toString().isEmpty()) {
                    tvTopSanPham.setText("Vui lòng nhập đầy đủ thông tin.");
                    tvTopSanPham.setVisibility(View.VISIBLE);
                    rcTopSanPham.setVisibility(View.GONE);
                } else {
                    tvTopSanPham.setVisibility(View.GONE);
                    rcTopSanPham.setVisibility(View.VISIBLE);
                }
                hienThiTopSanPham(Integer.parseInt(edtSoLuong.getText().toString().trim()));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void hienThiTopSanPham(int n) {
        List<TopSanPham> topSanPhamList = dao.thongKeTopSanPham(n);

        if (topSanPhamList != null && !topSanPhamList.isEmpty()) {
            TopSanPhamAdapter adapter = new TopSanPhamAdapter(this, topSanPhamList);
            rcTopSanPham.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Không có dữ liệu sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear
                            + "-" + String.format("%02d", selectedMonth + 1)
                            + "-" + String.format("%02d", selectedDay);
                    editText.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
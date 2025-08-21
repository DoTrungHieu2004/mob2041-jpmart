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
import com.hieudt.jpmart.adapters.TopKhachHangAdapter;
import com.hieudt.jpmart.dao.ThongKeDAO;
import com.hieudt.jpmart.entity.TopKhachHang;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class TopKhachHangActivity extends AppCompatActivity {
    private EditText edtTuNgay, edtDenNgay, edtSoLuong;
    private AppCompatButton btnTopKhachHang;
    private TextView tvTopKhachHang;
    private RecyclerView rcTopKhachHang;
    private Toolbar toolbar;
    private ThongKeDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_khach_hang);

        edtTuNgay = findViewById(R.id.edt_tu_ngay);
        edtDenNgay = findViewById(R.id.edt_den_ngay);
        edtSoLuong = findViewById(R.id.edt_so_luong);
        btnTopKhachHang = findViewById(R.id.btn_top_khach_hang);
        tvTopKhachHang = findViewById(R.id.tv_top_khach_hang);
        rcTopKhachHang = findViewById(R.id.rc_top_khach_hang);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Thống kê top khách hàng");
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

        btnTopKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTuNgay.getText().toString().isEmpty()
                        || edtDenNgay.getText().toString().isEmpty()
                        || edtSoLuong.getText().toString().isEmpty()) {
                    tvTopKhachHang.setText("Vui lòng nhập đầy đủ thông tin.");
                    tvTopKhachHang.setVisibility(View.VISIBLE);
                    rcTopKhachHang.setVisibility(View.GONE);
                } else {
                    tvTopKhachHang.setVisibility(View.GONE);
                    rcTopKhachHang.setVisibility(View.VISIBLE);
                }
                hienThiTopKhachHang(Integer.parseInt(edtSoLuong.getText().toString().trim()));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void hienThiTopKhachHang(int n) {
        List<TopKhachHang> topKhachHangList = dao.thongKeTopKhachHang(n);

        if (topKhachHangList != null && !topKhachHangList.isEmpty()) {
            TopKhachHangAdapter adapter = new TopKhachHangAdapter(this, topKhachHangList);
            rcTopKhachHang.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Không có dữ liệu khách hàng!", Toast.LENGTH_SHORT).show();
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
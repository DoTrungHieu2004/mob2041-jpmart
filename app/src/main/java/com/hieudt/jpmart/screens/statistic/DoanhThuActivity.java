package com.hieudt.jpmart.screens.statistic;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.dao.ThongKeDAO;

import java.util.Calendar;
import java.util.Objects;

public class DoanhThuActivity extends AppCompatActivity {
    private EditText edtTuNgay, edtDenNgay;
    private TextView tvDoanhThu;
    private AppCompatButton btnDoanhThu;
    private Toolbar toolbar;
    private ThongKeDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);

        edtTuNgay = findViewById(R.id.edt_tu_ngay);
        edtDenNgay = findViewById(R.id.edt_den_ngay);
        tvDoanhThu = findViewById(R.id.tv_doanh_thu);
        btnDoanhThu = findViewById(R.id.btn_doanh_thu);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Thống kê doanh thu");
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

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngayBatDau = edtTuNgay.getText().toString().trim();
                String ngayKetThuc = edtDenNgay.getText().toString().trim();

                if (ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
                    tvDoanhThu.setText("Vui lòng nhập đầy đủ ngày bắt ầu và ngày kết thúc.");
                    return;
                }

                int doanhThu = dao.layDoanhThu(ngayBatDau, ngayKetThuc);
                tvDoanhThu.setText(doanhThu + " VNĐ");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
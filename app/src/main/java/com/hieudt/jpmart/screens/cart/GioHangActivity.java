package com.hieudt.jpmart.screens.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hieudt.jpmart.R;
import com.hieudt.jpmart.adapters.GioHangAdapter;
import com.hieudt.jpmart.common.Common;
import com.hieudt.jpmart.dao.HoaDonChiTietDAO;
import com.hieudt.jpmart.dao.HoaDonDAO;
import com.hieudt.jpmart.dao.KhachHangDAO;
import com.hieudt.jpmart.entity.GioHangItem;
import com.hieudt.jpmart.entity.HoaDon;
import com.hieudt.jpmart.entity.HoaDonChiTiet;
import com.hieudt.jpmart.entity.KhachHang;
import com.hieudt.jpmart.entity.SanPham;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GioHangActivity extends AppCompatActivity implements GioHangAdapter.OnSanPhamClickListener {
    private Spinner spKhachHang;
    private RecyclerView rcGioHang;
    private TextView tvTongTien;
    private AppCompatButton btnThanhToan;
    private Toolbar toolbar;
    private GioHangAdapter adapter;
    private KhachHangDAO khachHangDAO;
    private HoaDonDAO hoaDonDAO;
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private List<KhachHang> listKhachHang;
    NumberFormat currencyFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        spKhachHang = findViewById(R.id.sp_khach_hang);
        rcGioHang = findViewById(R.id.rc_gio_hang);
        tvTongTien = findViewById(R.id.tv_tong_tien);
        btnThanhToan = findViewById(R.id.btn_thanh_toan);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Giỏ hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        khachHangDAO = new KhachHangDAO(this);
        hoaDonDAO = new HoaDonDAO(this);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);

        listKhachHang = khachHangDAO.layTatCaKhachHang();
        ArrayAdapter<KhachHang> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listKhachHang);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spKhachHang.setAdapter(arrayAdapter);

        adapter = new GioHangAdapter(this, Common.getGioHang().getDanhSachGioHang());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rcGioHang.setLayoutManager(llm);
        adapter.setOnSanPhamClickListener(this);
        rcGioHang.setAdapter(adapter);

        // Định dạng tiền tệ Việt Nam
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        updateTongTien();

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maHoaDon = hoaDonDAO.taoMaHoaDonMoi();
                String maKhachHang = ((KhachHang) spKhachHang.getSelectedItem()).getMaKhachHang();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                int tongTien = 0;
                for (GioHangItem item : Common.getGioHang().getDanhSachGioHang()) {
                    tongTien += item.getSanPham().getGiaSanPham() * item.getSoLuong();
                }
                hoaDonDAO.themHoaDon(new HoaDon(maHoaDon, Common.maNhanVien, maKhachHang, dateFormat.format(new Date()), tongTien));

                for (GioHangItem item : Common.getGioHang().getDanhSachGioHang()) {
                    String maHDCT = hoaDonChiTietDAO.taoMaHDCTMoi();
                    SanPham sanPham = item.getSanPham();
                    hoaDonChiTietDAO.themHDCT(new HoaDonChiTiet(maHDCT, maHoaDon, sanPham.getMaSanPham(), item.getSoLuong(), sanPham.getGiaSanPham()));
                }
                Toast.makeText(GioHangActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                Common.getGioHang().getDanhSachGioHang().clear();
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDeleteSanPham(GioHangItem gioHangItem) {
        Common.getGioHang().getDanhSachGioHang().remove(gioHangItem);
        adapter.notifyDataSetChanged();
        updateTongTien();
    }

    @Override
    public void onIncrease(GioHangItem gioHangItem) {
        gioHangItem.setSoLuong(gioHangItem.getSoLuong() + 1);
        adapter.notifyDataSetChanged();
        updateTongTien();
    }

    @Override
    public void onDecrease(GioHangItem gioHangItem) {
        if (gioHangItem.getSoLuong() > 1) {
            gioHangItem.setSoLuong(gioHangItem.getSoLuong() - 1);
            adapter.notifyDataSetChanged();
            updateTongTien();
        } else {
            Toast.makeText(this, "Số lượng đang là 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTongTien() {
        int tongTien = 0;
        for (GioHangItem item : Common.getGioHang().getDanhSachGioHang()) {
            tongTien += item.getSanPham().getGiaSanPham() * item.getSoLuong();
        }
        tvTongTien.setText("Tổng tiền: " + currencyFormat.format(tongTien));
    }
}
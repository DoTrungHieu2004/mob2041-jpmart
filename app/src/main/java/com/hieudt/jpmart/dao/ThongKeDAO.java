package com.hieudt.jpmart.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.hieudt.jpmart.entity.TopKhachHang;
import com.hieudt.jpmart.entity.TopSanPham;
import com.hieudt.jpmart.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private Context context;
    private SQLiteDatabase db;
    private DBHelper helper;

    public ThongKeDAO(Context context) {
        this.context = context;
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thống kê doanh thu
    public int layDoanhThu(String ngayBatDau, String ngayKetThuc) {
        db = helper.getReadableDatabase();
        int doanhThu = 0;

        String lenhTruyVan = "SELECT SUM(tong_tien) from tb_hoadon WHERE ngay_lap BETWEEN ? AND ?";
        try {
            SQLiteStatement statement = db.compileStatement(lenhTruyVan);
            statement.bindString(1, ngayBatDau);
            statement.bindString(2, ngayKetThuc);
            doanhThu = (int) statement.simpleQueryForLong();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return doanhThu;
    }

    // Thống kê top sản phẩm
    public List<TopSanPham> thongKeTopSanPham(int n) {
        db = helper.getReadableDatabase();

        String sql = "SELECT sp.ma_sp, sp.ten_sp, SUM(hdct.so_luong) as tong_so_luong FROM tb_hoadonchitiet hdct " +
                "JOIN tb_sanpham sp ON hdct.ma_sp = sp.ma_sp " +
                "GROUP BY sp.ma_sp, sp.ten_sp " +
                "ORDER BY tong_so_luong DESC " +
                "LIMIT ?";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(n)});
        List<TopSanPham> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            String maSanPham = cursor.getString(cursor.getColumnIndexOrThrow("ma_sp"));
            String tenSanPham = cursor.getString(cursor.getColumnIndexOrThrow("ten_sp"));
            int tongSoLuong = cursor.getInt(cursor.getColumnIndexOrThrow("tong_so_luong"));

            TopSanPham thongKeSanPham = new TopSanPham(maSanPham, tenSanPham, tongSoLuong);
            list.add(thongKeSanPham);
        }
        cursor.close();
        db.close();
        return list;
    }

    // Thống kê top khách hàng
    public List<TopKhachHang> thongKeTopKhachHang(int n) {
        db = helper.getReadableDatabase();

        String sql = "SELECT kh.ma_kh, kh.ten_kh, COUNT(hd.ma_hoadon) as so_lan_mua, SUM(hd.tong_tien) as tong_chi_tieu " +
                "FROM tb_hoadon hd " +
                "JOIN tb_khachhang kh ON hd.ma_kh = kh.ma_kh " +
                "GROUP BY kh.ma_kh, kh.ten_kh " +
                "ORDER BY tong_chi_tieu DESC " +
                "LIMIT ?";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(n)});
        List<TopKhachHang> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            String maKhachHang = cursor.getString(cursor.getColumnIndexOrThrow("ma_kh"));
            String tenKhachHang = cursor.getString(cursor.getColumnIndexOrThrow("ten_kh"));
            int soLanMua = cursor.getInt(cursor.getColumnIndexOrThrow("so_lan_mua"));
            double tongChiTieu = cursor.getDouble(cursor.getColumnIndexOrThrow("tong_chi_tieu"));

            TopKhachHang topKhachHang = new TopKhachHang(maKhachHang, tenKhachHang, soLanMua, tongChiTieu);
            list.add(topKhachHang);
        }
        cursor.close();
        db.close();
        return list;
    }
}
package com.hieudt.jpmart.dao;

import static com.hieudt.jpmart.sqlite.DBHelper.COT_DIA_CHI_KH;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_EMAIL;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_KHACH_HANG;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_SO_DIEN_THOAI;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_TEN_KHACH_HANG;
import static com.hieudt.jpmart.sqlite.DBHelper.TB_KHACHHANG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudt.jpmart.entity.KhachHang;
import com.hieudt.jpmart.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public KhachHangDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm khách hàng
    public boolean themKhachHang(KhachHang khachHang) {
        ContentValues values = new ContentValues();

        values.put(COT_MA_KHACH_HANG, khachHang.getMaKhachHang());
        values.put(COT_TEN_KHACH_HANG, khachHang.getTenKhachHang());
        values.put(COT_DIA_CHI_KH, khachHang.getDiaChi());
        values.put(COT_SO_DIEN_THOAI, khachHang.getSoDienThoai());
        values.put(COT_EMAIL, khachHang.getEmail());

        long rows = db.insert(TB_KHACHHANG, null, values);
        db.close();
        return rows > 0;
    }

    // Xóa khách hàng
    public boolean xoaKhachHang(String maKhachHang) {
        int rows = db.delete(TB_KHACHHANG, COT_MA_KHACH_HANG + " = ?", new String[]{maKhachHang});
        db.close();
        return rows > 0;
    }

    // Sửa thông tin khách hàng
    public boolean suaKhachHang(KhachHang khachHang) {
        ContentValues values = new ContentValues();

        values.put(COT_TEN_KHACH_HANG, khachHang.getTenKhachHang());
        values.put(COT_DIA_CHI_KH, khachHang.getDiaChi());
        values.put(COT_SO_DIEN_THOAI, khachHang.getSoDienThoai());
        values.put(COT_EMAIL, khachHang.getEmail());

        int rows = db.update(TB_KHACHHANG, values, COT_MA_KHACH_HANG + " = ?", new String[]{khachHang.getMaKhachHang()});
        db.close();
        return rows > 0;
    }

    // Tìm kiếm khách hàng theo tên
    public List<KhachHang> timKiemKhachHang(String tenKhachHang) {
        List<KhachHang> khachHangList = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_khachhang WHERE ten_kh LIKE ?", new String[]{"%" + tenKhachHang + "%"});
        if (cursor.moveToFirst()) {
            do {
                KhachHang kh = new KhachHang(
                        cursor.getString(0),    // Mã khách hàng
                        cursor.getString(1),    // Tên khách hàng
                        cursor.getString(2),    // Địa chỉ
                        cursor.getString(3),    // Số điên thoại
                        cursor.getString(4)    // Email
                );
                khachHangList.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return khachHangList;
    }
}
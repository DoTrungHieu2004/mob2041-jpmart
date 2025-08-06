package com.hieudt.jpmart.dao;

import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_HOA_DON;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_KHACH_HANG;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_NHAN_VIEN;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_NGAY_LAP;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_TONG_TIEN;
import static com.hieudt.jpmart.sqlite.DBHelper.TB_HOADON;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudt.jpmart.entity.HoaDon;
import com.hieudt.jpmart.sqlite.DBHelper;

public class HoaDonDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public HoaDonDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm hóa đơn
    public boolean themHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();

        values.put(COT_MA_HOA_DON, hoaDon.getMaHoaDon());
        values.put(COT_MA_NHAN_VIEN, hoaDon.getMaNhanVien());
        values.put(COT_MA_KHACH_HANG, hoaDon.getMaKhachHang());
        values.put(COT_NGAY_LAP, hoaDon.getNgayLap());
        values.put(COT_TONG_TIEN, hoaDon.getTongTien());

        long rows = db.insert(TB_HOADON, null, values);
        db.close();
        return rows > 0;
    }

    // Xóa hóa đon
    public boolean xoaHoaDon(String maHoaDon) {
        int rows = db.delete(TB_HOADON, COT_MA_HOA_DON + " = ?", new String[]{maHoaDon});
        db.close();
        return rows > 0;
    }

    // Tạo mã hóa đơn mới
    public String taoMaHoaDonMoi() {
        db = helper.getReadableDatabase();
        String maHoaDonMoi = "HD1";

        String query = "SELECT " + COT_MA_HOA_DON + " FROM " + TB_HOADON + " ORDER BY " + COT_MA_HOA_DON + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String lastMaHoaDon = cursor.getString(0);
            int lastNumber = Integer.parseInt(lastMaHoaDon.replace("HD", ""));
            maHoaDonMoi = "HD" + (lastNumber + 1);
        }

        cursor.close();
        return maHoaDonMoi;
    }
}
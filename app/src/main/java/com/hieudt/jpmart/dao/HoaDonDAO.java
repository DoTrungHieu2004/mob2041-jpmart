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

import java.util.ArrayList;
import java.util.List;

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
        return rows > 0;
    }

    // Xóa hóa đon
    public boolean xoaHoaDon(String maHoaDon) {
        int rows = db.delete(TB_HOADON, COT_MA_HOA_DON + " = ?", new String[]{maHoaDon});
        return rows > 0;
    }

    // Lây tất cả hóa đơn
    public List<HoaDon> layTatCaHoaDon() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_HOADON, null);

        if (cursor.moveToFirst()) {
            do {
                HoaDon hoaDon = new HoaDon(
                        cursor.getString(0),    // Mã hóa đơn
                        cursor.getString(1),    // Mã nhân viên
                        cursor.getString(2),    // Mã khách hàng
                        cursor.getString(3),    // Ngày lập
                        cursor.getDouble(4)     // Tổng tiền
                );
                hoaDonList.add(hoaDon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return hoaDonList;
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
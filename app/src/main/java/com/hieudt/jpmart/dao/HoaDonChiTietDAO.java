package com.hieudt.jpmart.dao;

import static com.hieudt.jpmart.sqlite.DBHelper.COT_DON_GIA;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_HDCT;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_HOA_DON;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_SAN_PHAM;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_SO_LUONG;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_TEN_SAN_PHAM;
import static com.hieudt.jpmart.sqlite.DBHelper.TB_HOADONCHITIET;
import static com.hieudt.jpmart.sqlite.DBHelper.TB_SANPHAM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudt.jpmart.entity.HoaDonChiTiet;
import com.hieudt.jpmart.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public HoaDonChiTietDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm hóa đơn chi tết
    public void themHDCT(HoaDonChiTiet hdct) {
        ContentValues values = new ContentValues();

        values.put(COT_MA_HDCT, hdct.getMaCTHD());
        values.put(COT_MA_HOA_DON, hdct.getMaSanPham());
        values.put(COT_MA_SAN_PHAM, hdct.getMaSanPham());
        values.put(COT_SO_LUONG, hdct.getSoLuong());
        values.put(COT_DON_GIA, hdct.getDonGia());

        db.insert(TB_HOADONCHITIET, null, values);
    }

    // Lấy tất cả hóa đơn chi tiết
    public List<HoaDonChiTiet> layTatCaHDCT(String maHoaDon) {
        List<HoaDonChiTiet> HDCTlist = new ArrayList<>();
        db = helper.getReadableDatabase();

        // Truy vấn để lấy tất cả chi tiết hóa đơn cho một mã hóa đơn
        String query = "SELECT hdct." + COT_MA_HDCT + ", " +
                "hdct." + COT_MA_HOA_DON + ", " +
                "hdct." + COT_MA_SAN_PHAM + ", " +
                "sp." + COT_TEN_SAN_PHAM + ", " +
                "hdct." + COT_SO_LUONG + ", " +
                "hdct." + COT_DON_GIA +
                " FROM " + TB_HOADONCHITIET + " hdct " +
                "INNER JOIN " + TB_SANPHAM + " sp ON hdct." + COT_MA_SAN_PHAM + " = sp." + COT_MA_SAN_PHAM + " " +
                "WHERE hdct." + COT_MA_HOA_DON + " = ?"; // Điều kiện theo mã hóa đơn

        Cursor cursor = db.rawQuery(query, new String[]{maHoaDon});
        if (cursor.moveToFirst()) {
            do {
                HoaDonChiTiet hdct = new HoaDonChiTiet(
                        cursor.getString(0),    // Mã hóa đơn chi tiết
                        cursor.getString(1),    // Mã hóa đơn
                        cursor.getString(2),    // Mã sản phẩm
                        cursor.getString(3),    // Tên sản phẩm
                        cursor.getInt(4),       // Số lượng
                        cursor.getDouble(5)     // Đơn giá
                );
                HDCTlist.add(hdct);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return HDCTlist;
    }

    // Tạo mã hóa đơn chi tiết mới
    public String taoMaHDCTMoi() {
        db = helper.getReadableDatabase();
        String maHDCTMoi = "HDCT1";

        String query = "SELECT " + COT_MA_HDCT + " FROM " + TB_HOADONCHITIET + " ORDER BY " + COT_MA_HDCT + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String lastMaHDCT = cursor.getString(0);
            int lastNumber = Integer.parseInt(lastMaHDCT.replace("HDCT", ""));
            maHDCTMoi = "HDCT" + (lastNumber + 1);
        }

        cursor.close();
        return maHDCTMoi;
    }
}
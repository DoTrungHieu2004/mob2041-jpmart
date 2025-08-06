package com.hieudt.jpmart.dao;

import static com.hieudt.jpmart.sqlite.DBHelper.COT_DON_GIA;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_DON_VI_TINH;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_DANH_MUC;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_SAN_PHAM;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_NGAY_NHAP;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_SO_LUONG;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_TEN_SAN_PHAM;
import static com.hieudt.jpmart.sqlite.DBHelper.TB_SANPHAM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudt.jpmart.entity.SanPham;
import com.hieudt.jpmart.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public SanPhamDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm sản phẩm
    public boolean themSanPham(SanPham sanPham) {
        ContentValues values = new ContentValues();

        values.put(COT_MA_SAN_PHAM, sanPham.getMaSanPham());
        values.put(COT_TEN_SAN_PHAM, sanPham.getTenSanPham());
        values.put(COT_DON_GIA, sanPham.getGiaSanPham());
        values.put(COT_SO_LUONG, sanPham.getSoLuong());
        values.put(COT_DON_VI_TINH, sanPham.getDonViTinh());
        values.put(COT_NGAY_NHAP, sanPham.getNgayNhap());
        values.put(COT_MA_DANH_MUC, sanPham.getMaDanhMuc());

        long rows = db.insert(TB_SANPHAM, null, values);
        db.close();
        return rows > 0;
    }

    // Xóa sản phẩm
    public boolean xoaSanPham(String maSanPham) {
        int rows = db.delete(TB_SANPHAM, COT_MA_SAN_PHAM = " = ?", new String[]{maSanPham});
        db.close();
        return rows > 0;
    }

    // Sửa thông tin sản phẩm
    public boolean suaSanPham(SanPham sanPham) {
        ContentValues values = new ContentValues();

        values.put(COT_TEN_SAN_PHAM, sanPham.getTenSanPham());
        values.put(COT_DON_GIA, sanPham.getGiaSanPham());
        values.put(COT_SO_LUONG, sanPham.getSoLuong());
        values.put(COT_DON_VI_TINH, sanPham.getDonViTinh());
        values.put(COT_NGAY_NHAP, sanPham.getNgayNhap());
        values.put(COT_MA_DANH_MUC, sanPham.getMaDanhMuc());

        int rows = db.update(TB_SANPHAM, values, COT_MA_SAN_PHAM + " = ?", new String[]{sanPham.getMaSanPham()});
        db.close();
        return rows > 0;
    }

    // Tìm kiếm sản phẩm
    public List<SanPham> timKiemSanPham(String tenSP) {
        List<SanPham> sanPhamList = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_sanpham WHERE ten_sp LIKE ?", new String[]{"%" + tenSP + "%"});
        if (cursor.moveToFirst()) {
            do {
                SanPham sp = new SanPham(
                        cursor.getString(0),    // Mã sản phẩm
                        cursor.getString(1),    // Tên sản phẩm
                        cursor.getInt(2),       // Giá
                        cursor.getInt(3),       // Số lượng
                        cursor.getString(4),    // Đơn vị tính
                        cursor.getString(5),    // Ngày nhập
                        cursor.getString(6)     // Mã danh mục
                );
                sanPhamList.add(sp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sanPhamList;
    }

    // Tạo mã sản phẩm mới
    public String taoMaSanPhamMoi() {
        db = helper.getReadableDatabase();
        String maSanPhamMoi = "SP1";

        String query = "SELECT " + COT_MA_SAN_PHAM + " FROM " + TB_SANPHAM + " ORDER BY " + COT_MA_SAN_PHAM + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String lastMaSanPham = cursor.getString(0);
            int lastNumber = Integer.parseInt(lastMaSanPham.replace("SP", ""));
            maSanPhamMoi = "SP" + (lastNumber + 1);
        }

        cursor.close();
        return maSanPhamMoi;
    }
}
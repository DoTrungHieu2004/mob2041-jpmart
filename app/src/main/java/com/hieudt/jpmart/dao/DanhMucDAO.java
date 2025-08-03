package com.hieudt.jpmart.dao;

import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_DANH_MUC;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_TEN_DANH_MUC;
import static com.hieudt.jpmart.sqlite.DBHelper.TB_DANHMUC;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudt.jpmart.entity.DanhMuc;
import com.hieudt.jpmart.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public DanhMucDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm danh mục
    public boolean themDanhMuc(DanhMuc danhMuc) {
        ContentValues values = new ContentValues();

        values.put(COT_MA_DANH_MUC, danhMuc.getMaDanhMuc());
        values.put(COT_TEN_DANH_MUC, danhMuc.getTenDanhMuc());

        long rows = db.insert(TB_DANHMUC, null, values);
        return rows > 0;
    }

    // Xóa danh mục
    public boolean xoaDanhMuc(String maDanhMuc) {
        int rows = db.delete(TB_DANHMUC, COT_MA_DANH_MUC + " = ?", new String[]{maDanhMuc});
        return rows > 0;
    }

    // Sửa thông tin danh mục
    public boolean suaDanhMuc(DanhMuc danhMuc) {
        ContentValues values = new ContentValues();
        values.put(COT_TEN_DANH_MUC, danhMuc.getTenDanhMuc());

        int rows = db.update(TB_DANHMUC, values, COT_MA_DANH_MUC + " = ?", new String[]{danhMuc.getMaDanhMuc()});
        return rows > 0;
    }

    // Tìm kiếm danh mục
    public List<DanhMuc> timKiemDanhMuc(String tenDanhMuc) {
        List<DanhMuc> danhMucList = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_danhmuc WHERE ten_danhmuc LIKE ?", new String[]{"%" + tenDanhMuc + "%"});
        if (cursor.moveToFirst()) {
            do {
                DanhMuc dm = new DanhMuc(
                        cursor.getString(0),    // Mã danh mục
                        cursor.getString(1)     // Tên danh mục
                );
                danhMucList.add(dm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhMucList;
    }

    // Lấy tất cả danh mục
    public List<DanhMuc> layTatCaDanhMuc() {
        List<DanhMuc> danhMucList = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_DANHMUC, null);

        if (cursor.moveToFirst()) {
            do {
                DanhMuc dm = new DanhMuc(
                        cursor.getString(0),    // Mã danh mục
                        cursor.getString(1)     // Tên danh mục
                );
                danhMucList.add(dm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhMucList;
    }

    // Tạo mã danh mục mới
    public String taoMaDanhMucMoi() {
        db = helper.getReadableDatabase();
        String maDanhMucMoi = "DM1";

        String query = "SELECT " + COT_MA_DANH_MUC + " FROM " + TB_DANHMUC + " ORDER BY " + COT_MA_DANH_MUC + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String lastMaDanhMuc = cursor.getString(0);
            int lastNumber = Integer.parseInt(lastMaDanhMuc.replace("DM", ""));
            maDanhMucMoi = "DM" + (lastNumber + 1);
        }

        cursor.close();
        return maDanhMucMoi;
    }
}
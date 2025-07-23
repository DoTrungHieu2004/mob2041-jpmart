package com.hieudt.jpmart.dao;

import static com.hieudt.jpmart.sqlite.DBHelper.COT_CHUC_VU;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_DIA_CHI_NV;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_LUONG;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MAT_KHAU;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_MA_NHAN_VIEN;
import static com.hieudt.jpmart.sqlite.DBHelper.COT_TEN_NHAN_VIEN;
import static com.hieudt.jpmart.sqlite.DBHelper.TB_NHANVIEN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hieudt.jpmart.entity.NhanVien;
import com.hieudt.jpmart.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public NhanVienDAO(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm nhân viên
    public boolean themNhanVien(NhanVien nhanVien) {
        ContentValues values = new ContentValues();

        values.put(COT_MA_NHAN_VIEN, nhanVien.getMaNhanVien());
        values.put(COT_TEN_NHAN_VIEN, nhanVien.getTenNhanVien());
        values.put(COT_DIA_CHI_NV, nhanVien.getDiaChi());
        values.put(COT_CHUC_VU, nhanVien.getChucVu());
        values.put(COT_LUONG, nhanVien.getLuong());
        values.put(COT_MAT_KHAU, nhanVien.getMatKhau());

        long rows = db.insert(TB_NHANVIEN, null, values);
        db.close();
        return rows > 0;
    }

    // Xóa nhân viên
    public boolean xoaNhanVien(String maNhanVien) {
        int rows = db.delete(TB_NHANVIEN, COT_MA_NHAN_VIEN + " = ?", new String[]{maNhanVien});
        db.close();
        return rows > 0;
    }

    // Sửa thông tin nhân viên
    public boolean swaNhanVien(NhanVien nhanVien) {
        ContentValues values = new ContentValues();

        values.put(COT_TEN_NHAN_VIEN, nhanVien.getTenNhanVien());
        values.put(COT_DIA_CHI_NV, nhanVien.getDiaChi());
        values.put(COT_CHUC_VU, nhanVien.getChucVu());
        values.put(COT_LUONG, nhanVien.getLuong());
        values.put(COT_MAT_KHAU, nhanVien.getMatKhau());

        long rows = db.update(TB_NHANVIEN, values, COT_MA_NHAN_VIEN + " = ?", new String[]{nhanVien.getMaNhanVien()});
        db.close();
        return rows > 0;
    }

    // Tìm kiếm nhân viên theo tên
    public List<NhanVien> timKiemNhanVien(String tenNV) {
        List<NhanVien> nhanVienList = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_nhanvien WHERE ten_nv LIKE ?", new String[]{"%" + tenNV + "%"});
        if (cursor.moveToFirst()) {
            do {
                NhanVien nv = new NhanVien(
                        cursor.getString(0),    // Mã nhân viên
                        cursor.getString(1),    // Tên nhân viên
                        cursor.getString(2),    // Địa chỉ
                        cursor.getInt(3),       // Chức vụ (0: quản lý, 1: nhân viên)
                        cursor.getDouble(4),    // Lương
                        cursor.getString(5)     // Mật khẩu
                );
                nhanVienList.add(nv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return nhanVienList;
    }
}
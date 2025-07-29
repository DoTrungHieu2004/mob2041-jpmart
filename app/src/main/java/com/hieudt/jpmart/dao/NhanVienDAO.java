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
        return rows > 0;
    }

    // Xóa nhân viên
    public boolean xoaNhanVien(String maNhanVien) {
        int rows = db.delete(TB_NHANVIEN, COT_MA_NHAN_VIEN + " = ?", new String[]{maNhanVien});
        return rows > 0;
    }

    // Sửa thông tin nhân viên
    public boolean suaNhanVien(NhanVien nhanVien) {
        ContentValues values = new ContentValues();

        values.put(COT_TEN_NHAN_VIEN, nhanVien.getTenNhanVien());
        values.put(COT_DIA_CHI_NV, nhanVien.getDiaChi());
        values.put(COT_CHUC_VU, nhanVien.getChucVu());
        values.put(COT_LUONG, nhanVien.getLuong());
        values.put(COT_MAT_KHAU, nhanVien.getMatKhau());

        long rows = db.update(TB_NHANVIEN, values, COT_MA_NHAN_VIEN + " = ?", new String[]{nhanVien.getMaNhanVien()});
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
        return nhanVienList;
    }

    // Lấy danh sách nhân viên
    public List<NhanVien> layTatCaNhanVien() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_NHANVIEN, null);

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
        return nhanVienList;
    }

    // Lấy nhân viên bằng mã nhân viên
    public NhanVien layNhanVienBangMaNV(String maNhanVien) {
        db = helper.getReadableDatabase();
        NhanVien nhanVien = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_NHANVIEN + " WHERE " + COT_MA_NHAN_VIEN + " = ?", new String[]{maNhanVien});
        if (cursor.moveToFirst()) {
            String tenNhanVien = cursor.getString(cursor.getColumnIndexOrThrow(COT_TEN_NHAN_VIEN));
            String diaChi = cursor.getString(cursor.getColumnIndexOrThrow(COT_DIA_CHI_NV));
            int chucVu = cursor.getInt(cursor.getColumnIndexOrThrow(COT_CHUC_VU));
            double luong = cursor.getDouble(cursor.getColumnIndexOrThrow(COT_LUONG));
            String matKhau = cursor.getString(cursor.getColumnIndexOrThrow(COT_MAT_KHAU));

            nhanVien = new NhanVien(tenNhanVien, diaChi, chucVu, luong, matKhau);
        }
        cursor.close();
        return nhanVien;
    }

    // Tạo mã nhân viên mới
    public String taoMaNhanVienMoi() {
        db = helper.getReadableDatabase();
        String maNhanVienMoi = "NV1";

        String query = "SELECT " + COT_MA_NHAN_VIEN + " FROM " + TB_NHANVIEN + " ORDER BY " + COT_MA_NHAN_VIEN + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String lastMaNhanVien = cursor.getString(0);
            int lastNumber = Integer.parseInt(lastMaNhanVien.replace("NV", ""));
            maNhanVienMoi = "NV" + (lastNumber + 1);
        }

        cursor.close();
        return maNhanVienMoi;
    }
}
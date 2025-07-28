package com.hieudt.jpmart.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "JPMart.db";
    private static final int DB_VERSION = 1;

    // Bảng danh mục
    public static final String TB_DANHMUC = "tb_danhmuc";
    public static final String COT_MA_DANH_MUC = "ma_danhmuc";
    public static final String COT_TEN_DANH_MUC = "ten_danhmuc";

    // Bảng sản phẩm
    public static final String TB_SANPHAM = "tb_sanpham";
    public static String COT_MA_SAN_PHAM = "ma_sp";
    public static final String COT_TEN_SAN_PHAM = "ten_sp";
    public static final String COT_DON_GIA = "don_gia";
    public static final String COT_DON_VI_TINH = "don_vi_tinh";
    public static final String COT_NGAY_NHAP = "ngay_nhap";

    // Bảng nhân viên
    public static final String TB_NHANVIEN = "tb_nhanvien";
    public static final String COT_MA_NHAN_VIEN = "ma_nv";
    public static final String COT_TEN_NHAN_VIEN = "ten_nv";
    public static final String COT_DIA_CHI_NV = "dia_chi";
    public static final String COT_CHUC_VU = "chuc_vu";
    public static final String COT_LUONG = "luong";
    public static final String COT_MAT_KHAU = "mat_khau";

    // Bảng khách hàng
    public static final String TB_KHACHHANG = "tb_khachhang";
    public static final String COT_MA_KHACH_HANG = "ma_kh";
    public static final String COT_TEN_KHACH_HANG = "ten_kh";
    public static final String COT_DIA_CHI_KH = "dia_chi";
    public static final String COT_SO_DIEN_THOAI = "so_dien_thoai";
    public static final String COT_EMAIL = "email";

    // Bảng hóa đơn
    public static final String TB_HOADON = "tb_hoadon";
    public static String COT_MA_HOA_DON = "ma_hoadon";
    public static final String COT_NGAY_LAP = "ngay_lap";
    public static final String COT_TONG_TIEN = "tong_tien";

    // Bảng hóa đơn chi tiết
    public static final String TB_HOADONCHITIET = "tb_hoadonchitiet";
    public static final String COT_MA_HDCT = "ma_hdct";
    public static final String COT_SO_LUONG = "so_luong";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bản sản phẩm
        String TB_SANPHAM_QUERY = "CREATE TABLE IF NOT EXISTS " + TB_SANPHAM + " ("
                    + COT_MA_SAN_PHAM + " TEXT PRIMARY KEY,"
                    + COT_TEN_SAN_PHAM + " TEXT,"
                    + COT_DON_GIA + " INTEGER,"
                    + COT_SO_LUONG + " INTEGER,"
                    + COT_DON_VI_TINH + " TEXT,"
                    + COT_NGAY_NHAP + " TEXT,"
                    + COT_MA_DANH_MUC + " TEXT,"
                    + "FOREIGN KEY (" + COT_MA_DANH_MUC + ") REFERENCES " + TB_DANHMUC + "(" + COT_MA_DANH_MUC + ")"
                    + ")";
        db.execSQL(TB_SANPHAM_QUERY);

        // Tạo bảng danh mục
        String TB_DANHMUC_QUERY = "CREATE TABLE IF NOT EXISTS " + TB_DANHMUC + "("
                    + COT_MA_DANH_MUC + " TEXT PRIMARY KEY,"
                    + COT_TEN_DANH_MUC + " TEXT"
                    + ")";
        db.execSQL(TB_DANHMUC_QUERY);

        // Tạo bảng nhân viên
        String TB_NHANVIEN_QUERY = "CREATE TABLE IF NOT EXISTS " + TB_NHANVIEN + "("
                    + COT_MA_NHAN_VIEN + " TEXT PRIMARY KEY,"   // Mã nhân viên là khóa chính
                    + COT_TEN_NHAN_VIEN + " TEXT NOT NULL,"     // Tên nhân viên, không được null
                    + COT_DIA_CHI_NV + " TEXT,"                 // Địa chỉ có thể null
                    + COT_CHUC_VU + " INT,"                     // 1 là Admin, 0 là nhân viên
                    + COT_LUONG + " REAL NOT NULL,"             // Lương kiểu REAL (số thực)
                    + COT_MAT_KHAU + " TEXT NOT NULL"           // Mật khẩu
                    + ")";
        db.execSQL(TB_NHANVIEN_QUERY);

        // Tạo bảng khách hàng
        String TB_KHACHHANG_QUERY = "CREATE TABLE IF NOT EXISTS " + TB_KHACHHANG + "("
                + COT_MA_KHACH_HANG + " TEXT PRIMARY KEY,"      // Mã khách hàng là khóa chính
                + COT_TEN_KHACH_HANG + " TEXT NOT NULL,"        // Tên khách hàng, không được null
                + COT_DIA_CHI_KH + " TEXT,"                     // Địa chỉ có thể null
                + COT_SO_DIEN_THOAI + " TEXT NOT NULL,"         // Số điện thoại bắt buộc nhập
                + COT_EMAIL + " TEXT NOT NULL"                  // Email bắt buộc nhập
                + ")";
        db.execSQL(TB_KHACHHANG_QUERY);

        // Tạo bảng hóa đơn
        String TB_HOADON_QUERY = "CREATE TABLE IF NOT EXISTS " + TB_HOADON + "("
                + COT_MA_HOA_DON + " TEXT PRIMARY KEY,"
                + COT_MA_NHAN_VIEN + " TEXT,"
                + COT_MA_KHACH_HANG + " TEXT,"
                + COT_NGAY_LAP + " TEXT,"
                + COT_TONG_TIEN + " INTEGER,"
                + "FOREIGN KEY (" + COT_MA_NHAN_VIEN + ") REFERENCES " + TB_NHANVIEN + "(" + COT_MA_NHAN_VIEN + "),"
                + "FOREIGN KEY (" + COT_MA_KHACH_HANG + ") REFERENCES " + TB_KHACHHANG + "(" + COT_MA_KHACH_HANG + ")"
                + ")";
        db.execSQL(TB_HOADON_QUERY);

        // Tạo bảng hóa đơn chi tiết
        String TB_HOADONCHITIET_QUERY = "CREATE TABLE IF NOT EXISTS " + TB_HOADONCHITIET + "("
                + COT_MA_HDCT + " TEXT PRIMARY KEY,"
                + COT_MA_HOA_DON + " TEXT NOT NULL,"
                + COT_MA_SAN_PHAM + " TEXT NOT NULL,"
                + COT_SO_LUONG + " INTEGER NOT NULL,"
                + COT_DON_GIA + " REAL NOT NULL,"
                + "FOREIGN KEY (" + COT_MA_HOA_DON + ") REFERENCES " + TB_HOADON + "(" + COT_MA_HOA_DON + "),"
                + "FOREIGN KEY (" + COT_MA_SAN_PHAM + ") REFERENCES " + TB_SANPHAM + "(" + TB_SANPHAM + ")"
                + ")";
        db.execSQL(TB_HOADONCHITIET_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_SANPHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_DANHMUC);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NHANVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_KHACHHANG);
        db.execSQL("DROP TABLE IF EXISTS " + TB_HOADON);
        db.execSQL("DROP TABLE IF EXISTS " + TB_HOADONCHITIET);
        onCreate(db);
    }

    // Kiểm tra chức năng đổi mật khẩu
    public boolean kiemTraMatKhauCu(String maNhanVien, String matKhauCu) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT mat_khau FROM tb_nhanvien WHERE ma_nv = ?", new String[]{maNhanVien});

        if (cursor.moveToFirst()) {
            String matKhauHienTai = cursor.getString(0);
            cursor.close();
            return matKhauHienTai.equals(matKhauCu);
        }

        cursor.close();
        return false;
    }

    public boolean capNhatMatKhauMoi(String maNhanVIen, String matKhauMoi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COT_MAT_KHAU, matKhauMoi);   // Cập nhật cột mật khẩu mới

        int rowsAffected = db.update(TB_NHANVIEN, values, COT_MA_NHAN_VIEN + " = ?", new String[]{maNhanVIen});
        return rowsAffected > 0;
    }
}
package com.hieudt.jpmart.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NhanVien implements Parcelable {
    private String maNhanVien;
    private String tenNhanVien;
    private String diaChi;
    private int chucVu; // 1 là Quản lý; 0 là Nhân viên
    private double luong;
    private String matKhau;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String tenNhanVien, String diaChi, int chucVu, double luong, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.luong = luong;
        this.matKhau = matKhau;
    }

    public NhanVien(String tenNhanVien, String diaChi, int chucVu, double luong, String matKhau) {
        this.tenNhanVien = tenNhanVien;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.luong = luong;
        this.matKhau = matKhau;
    }

    protected NhanVien(Parcel in) {
        maNhanVien = in.readString();
        tenNhanVien = in.readString();
        diaChi = in.readString();
        chucVu = in.readInt();
        luong = in.readDouble();
        matKhau = in.readString();
    }

    public static final Creator<NhanVien> CREATOR = new Creator<NhanVien>() {
        @Override
        public NhanVien createFromParcel(Parcel in) {
            return new NhanVien(in);
        }

        @Override
        public NhanVien[] newArray(int size) {
            return new NhanVien[size];
        }
    };

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(maNhanVien);
        parcel.writeString(tenNhanVien);
        parcel.writeString(diaChi);
        parcel.writeInt(chucVu);
        parcel.writeDouble(luong);
        parcel.writeString(matKhau);
    }
}
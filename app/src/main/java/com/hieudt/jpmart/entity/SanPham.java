package com.hieudt.jpmart.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SanPham implements Parcelable {
    private String maSanPham;
    private String tenSanPham;
    private int giaSanPham;
    private int soLuong;
    private String donViTinh;
    private String ngayNhap;
    private String maDanhMuc;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, int giaSanPham, int soLuong, String donViTinh, String ngayNhap, String maDanhMuc) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
        this.ngayNhap = ngayNhap;
        this.maDanhMuc = maDanhMuc;
    }

    protected SanPham(Parcel in) {
        maSanPham = in.readString();
        tenSanPham = in.readString();
        giaSanPham = in.readInt();
        soLuong = in.readInt();
        donViTinh = in.readString();
        ngayNhap = in.readString();
        maDanhMuc = in.readString();
    }

    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham(in);
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(maSanPham);
        parcel.writeString(tenSanPham);
        parcel.writeInt(giaSanPham);
        parcel.writeInt(soLuong);
        parcel.writeString(donViTinh);
        parcel.writeString(ngayNhap);
        parcel.writeString(maDanhMuc);
    }
}
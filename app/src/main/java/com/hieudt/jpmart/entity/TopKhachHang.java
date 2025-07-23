package com.hieudt.jpmart.entity;

public class TopKhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private int soLanMua;
    private double tongChiTieu;

    public TopKhachHang() {
    }

    public TopKhachHang(String maKhachHang, String tenKhachHang, int soLanMua, double tongChiTieu) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soLanMua = soLanMua;
        this.tongChiTieu = tongChiTieu;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getSoLanMua() {
        return soLanMua;
    }

    public void setSoLanMua(int soLanMua) {
        this.soLanMua = soLanMua;
    }

    public double getTongChiTieu() {
        return tongChiTieu;
    }

    public void setTongChiTieu(double tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }
}
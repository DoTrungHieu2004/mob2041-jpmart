package com.hieudt.jpmart.entity;

public class GioHangItem {
    private SanPham sanPham;
    private int soLuong;

    public GioHangItem(SanPham sanPham, int soLuong) {
        this.sanPham = sanPham;
        this.soLuong = soLuong;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GioHangItem)) throw new IllegalArgumentException();
        GioHangItem gioHangItem = (GioHangItem) obj;
        return gioHangItem.getSanPham().getMaSanPham().equals(this.sanPham.getMaSanPham());
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
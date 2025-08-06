package com.hieudt.jpmart.common;

import com.hieudt.jpmart.entity.GioHang;

public class Common {
    public static String maNhanVien;
    private static GioHang gioHang;

    public static GioHang getGioHang() {
        if (gioHang == null) gioHang = new GioHang();
        return gioHang;
    }
}
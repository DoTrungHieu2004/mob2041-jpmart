package com.hieudt.jpmart.interfaces;

import com.hieudt.jpmart.entity.KhachHang;

public interface OnKhachHangClickListener {
    void onEditKhachHang(KhachHang khachHang);
    void onDeleteKhachHang(KhachHang khachHang);
}
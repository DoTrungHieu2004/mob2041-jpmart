package com.hieudt.jpmart.entity;

public class ChucVu {
    private int chucVuCode;
    private String chucVuName;

    public ChucVu(int chucVuCode, String chucVuName) {
        this.chucVuCode = chucVuCode;
        this.chucVuName = chucVuName;
    }

    public int getChucVuCode() {
        return chucVuCode;
    }

    public void setChucVuCode(int chucVuCode) {
        this.chucVuCode = chucVuCode;
    }

    public String getChucVuName() {
        return chucVuName;
    }

    public void setChucVuName(String chucVuName) {
        this.chucVuName = chucVuName;
    }

    @Override
    public String toString() {
        return chucVuName;
    }
}
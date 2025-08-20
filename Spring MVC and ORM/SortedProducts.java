package com.gl.sample.dto;

public class SortedProducts {
    private String BarCode;

    public SortedProducts(String a) {
        BarCode = a;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }
}

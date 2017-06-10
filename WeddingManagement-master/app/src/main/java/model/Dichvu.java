package model;

import java.io.Serializable;

/**
 * Created by Vu Khac Hoi on 3/7/2017.
 */

public class Dichvu implements Serializable {

   private String Tendichvu;
   private int Soluong;
    private  Boolean IsVisible;

    public Dichvu(String tendichvu, int soluong, Boolean isVisible, Boolean isXoa, String maKH, int dongia) {
        Tendichvu = tendichvu;
        Soluong = soluong;
        IsVisible = isVisible;
        IsXoa = isXoa;
        MaKH = maKH;
        Dongia = dongia;
    }

    public Boolean getXoa() {

        return IsXoa;
    }

    public void setXoa(Boolean xoa) {
        IsXoa = xoa;
    }

    public Boolean getVisible() {
        return IsVisible;
    }

    public void setVisible(Boolean visible) {
        IsVisible = visible;
    }

    private  Boolean IsXoa;

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    private  String MaKH;
    public Dichvu() {
    }

    public Dichvu(String tendichvu, int soluong, int dongia) {
        Tendichvu = tendichvu;

        Soluong = soluong;
        Dongia = dongia;
    }

    public Dichvu(String maKH,String tendichvu, int soluong, int dongia) {
        Tendichvu = tendichvu;
        Soluong = soluong;
        MaKH = maKH;
        Dongia = dongia;
    }

    public String getTendichvu() {
        return Tendichvu;
    }

    public void setTendichvu(String tendichvu) {
        Tendichvu = tendichvu;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public int getDongia() {
        return Dongia;
    }

    public void setDongia(int dongia) {
        Dongia = dongia;
    }

    private int Dongia;
}


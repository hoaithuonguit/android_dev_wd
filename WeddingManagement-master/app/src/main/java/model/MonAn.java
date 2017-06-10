package model;

import java.io.Serializable;

/**
 * Created by Vu Khac Hoi on 3/5/2017.
 */

public class MonAn implements Serializable {
    private  String TenMonAn;
    private int Gia;
    private String MaKH;
    private  Boolean IsXoa;

    public MonAn(String tenMonAn, int gia, String maKH, Boolean isXoa, Boolean isVisible) {
        TenMonAn = tenMonAn;
        Gia = gia;
        MaKH = maKH;
        IsXoa = isXoa;
        IsVisible = isVisible;
    }

    public Boolean getVisible() {

        return IsVisible;
    }

    public void setVisible(Boolean visible) {
        IsVisible = visible;
    }

    private  Boolean IsVisible;






    public Boolean getXoa() {

        return IsXoa;
    }

    public void setXoa(Boolean xoa) {
        IsXoa = xoa;
    }
    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }



    public MonAn() {
    }

    public MonAn(String tenMonAn, int gia) {

        TenMonAn = tenMonAn;
        Gia = gia;
    }

    public String getTenMonAn() {
        return TenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        TenMonAn = tenMonAn;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }


}

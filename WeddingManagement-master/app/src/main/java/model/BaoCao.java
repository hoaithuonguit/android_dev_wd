package model;

/**
 * Created by billy on 13-Mar-17.
 */

public class BaoCao {
    private int ngay,thang,sl;
    private float doanhthu;

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public float getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(int doanhthu) {
        this.doanhthu = doanhthu;
    }

    public BaoCao(int ngay, int sl, float doanhthu) {

        this.ngay = ngay;
        this.sl = sl;
        this.doanhthu = doanhthu;
    }
}

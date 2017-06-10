package model;

/**
 * Created by billy on 11-Mar-17.
 */

public class Hoadon {
    private String mahd,makh,ngthanhtoan;
    private  int slban,dongia,datcoc,tongtien;
    private int check=0;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getNgthanhtoan() {
        return ngthanhtoan;
    }

    public void setNgthanhtoan(String ngthanhtoan) {
        this.ngthanhtoan = ngthanhtoan;
    }

    public Hoadon(String mahd, String makh, String ngthanhtoan, int slban, int dongia, int datcoc, int tongtien) {

        this.mahd = mahd;
        this.makh = makh;
        this.ngthanhtoan = ngthanhtoan;
        this.slban = slban;
        this.dongia = dongia;
        this.datcoc = datcoc;
        this.tongtien = tongtien;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public int getSlban() {
        return slban;
    }

    public void setSlban(int slban) {
        this.slban = slban;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public int getDatcoc() {
        return datcoc;
    }

    public void setDatcoc(int datcoc) {
        this.datcoc = datcoc;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public Hoadon() {

    }

    public Hoadon(String mahd, String makh, int slban, int dongia, int datcoc, int tongtien) {

        this.mahd = mahd;
        this.makh = makh;
        this.slban = slban;
        this.dongia = dongia;
        this.datcoc = datcoc;
        this.tongtien = tongtien;
    }

    @Override
    public String toString() {
        return mahd + makh;
    }
}

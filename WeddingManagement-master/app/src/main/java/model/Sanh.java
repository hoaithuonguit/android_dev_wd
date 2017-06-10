package model;

/**
 * Created by billy on 07-Mar-17.
 */

public class Sanh {
    private String tensanh,loaisanh,ghichu;
    private int soluongbanmax,dongiamin;

    public String getTensanh() {
        return tensanh;
    }

    public void setTensanh(String tensanh) {
        this.tensanh = tensanh;
    }

    public String getLoaisanh() {
        return loaisanh;
    }

    public void setLoaisanh(String loaisanh) {
        this.loaisanh = loaisanh;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getSoluongbanmax() {
        return soluongbanmax;
    }

    public void setSoluongbanmax(int soluongbanmax) {
        this.soluongbanmax = soluongbanmax;
    }

    public int getDongiamin() {
        return dongiamin;
    }

    public void setDongiamin(int dongiamin) {
        this.dongiamin = dongiamin;
    }

    public Sanh() {

    }

    public Sanh(String tensanh, String loaisanh, String ghichu, int soluongbanmax, int dongiamin) {

        this.tensanh = tensanh;
        this.loaisanh = loaisanh;
        this.ghichu = ghichu;
        this.soluongbanmax = soluongbanmax;
        this.dongiamin = dongiamin;
    }
}

package model;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by billy on 03-Mar-17.
 */

public class TiecCuoi implements Serializable {
    private String makh,codau,chure,sanh,ngay,ca;
    private int tienban;
    private int tiendatcoc;

    public int getTiendatcoc() {
        return tiendatcoc;
    }

    public void setTiendatcoc(int tiendatcoc) {
        this.tiendatcoc = tiendatcoc;
    }

    private ArrayList<Dichvu>dv=new ArrayList<>();
    public void adddv(Dichvu dvtemp)
    {
        dv.add(dvtemp);
    }
    public int getTienban() {
        return tienban;
    }

    public void setTienban(int tienan) {
        this.tienban = tienan;
    }


    public int getCheckThanhToan() {
        return checkThanhToan;
    }

    public void setCheckThanhToan(int checkThanhToan) {
        this.checkThanhToan = checkThanhToan;
    }

    private int checkThanhToan=0;
    private int check=0;
    private int soban;

    protected TiecCuoi(Parcel in) {
        makh = in.readString();
        codau = in.readString();
        chure = in.readString();
        sanh = in.readString();
        ngay = in.readString();
        ca = in.readString();
        check = in.readInt();
        soban = in.readInt();
    }

    public TiecCuoi(String makh,String chure, String codau , String sanh, String ngay) {
        this.makh = makh;
        this.codau = codau;
        this.chure = chure;
        this.sanh = sanh;
        this.ngay = ngay;
        //this.checkThanhToan=checkThanhToan;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public TiecCuoi(String makh, String chure, String codau, String sanh, String ngay, String ca, int soban) {
        this.codau = codau;
        this.makh=makh;
        this.chure = chure;
        this.sanh = sanh;
        this.ngay = ngay;
        this.ca = ca;
        this.soban = soban;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getCodau() {
        return codau;
    }

    public void setCodau(String codau) {
        this.codau = codau;
    }

    public String getChure() {
        return chure;
    }

    public void setChure(String chure) {
        this.chure = chure;
    }

    public String getSanh() {
        return sanh;
    }

    public void setSanh(String sanh) {
        this.sanh = sanh;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }




    public int getSoban() {
        return soban;
    }

    public ArrayList<Dichvu> getDv() {
        return dv;
    }

    public void setDv(ArrayList<Dichvu> dv) {
        this.dv = dv;
    }

    public void setSoban(int soban) {
        this.soban = soban;
    }

    public TiecCuoi() {
    }

}

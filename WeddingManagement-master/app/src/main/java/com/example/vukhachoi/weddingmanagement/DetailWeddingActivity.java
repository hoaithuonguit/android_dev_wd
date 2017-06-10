package com.example.vukhachoi.weddingmanagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterDichVu;
import adapter.AdapterMonAn;
import model.Dichvu;
import model.MonAn;
import sqlite.Databasehelper;

public class DetailWeddingActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lsvMonAn,lsvdichvu;
    Button btndattiec,btncapnhat;
    SQLiteDatabase database;
    List<Dichvu>listdichvu;
    List<MonAn>lsv;
    AdapterMonAn adapter;
    AdapterDichVu adapterDichVu;
    String MaKH;
    EditText txtChuRe,txtCoDau,txtEditngay,txtEditca,txtEdittiendatcoc,txtEditLượngBan,txtEditDutru,txtdienthoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wedding);
        addControl();
        addEvent();



    }

    private void addControl() {
        lsvMonAn= (ListView) findViewById(R.id.lsv_MonAn);
        lsvdichvu= (ListView) findViewById(R.id.lsv_dichvu);
        btndattiec= (Button) findViewById(R.id.btndattiec);
        btncapnhat= (Button) findViewById(R.id.btncapnhat);
        txtChuRe= (EditText) findViewById(R.id.txtChuRe);
        txtCoDau= (EditText) findViewById(R.id.txtCoDau);

        txtEditngay= (EditText) findViewById(R.id.txtEditngay);
        txtEditca= (EditText) findViewById(R.id.txtEditca);
        txtEdittiendatcoc= (EditText) findViewById(R.id.txtEdittiendatcoc);
        txtEditLượngBan= (EditText) findViewById(R.id.txtEditLượngBan);
        txtEditDutru= (EditText) findViewById(R.id.txtEditDutru);
        txtdienthoai= (EditText) findViewById(R.id.txtEditsodienthooai);
        Databasehelper myDatabase = new Databasehelper(this);

       myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();




        Bundle extras=getIntent().getExtras();
        String Tensanh= extras.getString("Tensanh");

        // tra ve khach hang da dat tiec
        try {


            Cursor cursorThongTin =database.rawQuery("select * from ThongTin where  TenSanh=?", new String[]{Tensanh});
            cursorThongTin.moveToFirst();
            txtChuRe.setText(cursorThongTin.getString(1).toString());
            txtCoDau.setText(cursorThongTin.getString(2).toString());
            txtdienthoai.setText(cursorThongTin.getString(3).toString());
            txtEditngay.setText(cursorThongTin.getString(4).toString());
            txtEditca.setText(cursorThongTin.getString(5).toString());
            txtEditDutru.setText(cursorThongTin.getString(8).toString());
            txtEditLượngBan.setText(cursorThongTin.getString(7).toString());
            txtEdittiendatcoc.setText(cursorThongTin.getString(6).toString());
            MaKH=cursorThongTin.getString(0).toString();
        }catch (Exception e){

        }
//tim ma khach hang


        if(MaKH==null) {
            Cursor CsMaKH = database.rawQuery(" select Makh from thongtin", null);
            CsMaKH.moveToLast();
            MaKH = CsMaKH.getString(0);
            MaKH = MaKH.substring(2, MaKH.length());
            if (Integer.parseInt(MaKH) < 9) MaKH = "KH0" + (Integer.parseInt(MaKH) + 1);
            else MaKH = "KH" + (Integer.parseInt(MaKH) + 1);
        }


        lsv= new ArrayList<>();

        Cursor cursor=database.rawQuery("SELECT * FROM monan",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MonAn monAn=new MonAn();
            monAn.setTenMonAn(cursor.getString(3).toString());
            monAn.setGia(Integer.parseInt(cursor.getString(1).toString()));
            monAn.setMaKH(MaKH);

            lsv.add(monAn);
            cursor.moveToNext();

        }


        cursor.close();
        adapter=new AdapterMonAn(DetailWeddingActivity.this,R.layout.item_monan,lsv);
        lsvMonAn.setAdapter(adapter);



        listdichvu= new ArrayList<>();

        Cursor  cursor1=database.rawQuery("SELECT * FROM dichvu",null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast())
        {
            Dichvu dichvu=new Dichvu();
            dichvu.setTendichvu(cursor1.getString(2).toString());
            dichvu.setDongia(Integer.parseInt(cursor1.getString(1).toString()));
            dichvu.setMaKH(MaKH);

            listdichvu.add(dichvu);
            cursor1.moveToNext();

        }


        cursor1.close();
        adapterDichVu=new AdapterDichVu(DetailWeddingActivity.this,R.layout.item_dichvu,listdichvu);
        lsvdichvu.setAdapter(adapterDichVu);





    }


    private void addEvent() {
        btndattiec.setOnClickListener(this);
        btncapnhat.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {
        Bundle extras=getIntent().getExtras();
        String Tensanh= extras.getString("Tensanh");
        if(btndattiec.getId()==v.getId())
        {


            if(txtEdittiendatcoc.getText().toString().isEmpty()||txtCoDau.getText().toString().isEmpty()||txtdienthoai.getText().toString().isEmpty()||txtEditngay.getText().toString().isEmpty()||txtEditca.getText().toString().isEmpty()||txtEdittiendatcoc.getText().toString().isEmpty()||txtEditLượngBan.getText().toString().isEmpty()||txtEditDutru.getText().toString().isEmpty())
                Toast.makeText(this,"Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
            else {
                try {

                    ContentValues values = new ContentValues();
                    values.put("MaKH", MaKH);
                    values.put("TenChure", txtChuRe.getText().toString());
                    values.put("tenCoDau", txtCoDau.getText().toString());
                    values.put("Dienthoai", txtdienthoai.getText().toString());
                    values.put("ngay", txtEditngay.getText().toString());
                    values.put("ca", txtEditca.getText().toString());
                    values.put("tiendatcoc", Integer.parseInt(txtEdittiendatcoc.getText().toString()));
                    values.put("soluongBan", Integer.parseInt(txtEditLượngBan.getText().toString()));
                    values.put("sobanDutru", Integer.parseInt(txtEditDutru.getText().toString()));
                    values.put("tenSanh", Tensanh);

                    database.insertWithOnConflict("ThongTin", null, values, SQLiteDatabase.CONFLICT_FAIL);
                    Toast.makeText(this, "Thêm Tiệc Thành Công", Toast.LENGTH_SHORT).show();
                } catch (SQLiteConstraintException SQLe) {
                    Toast.makeText(this, "Khách hàng đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(btncapnhat.getId()==v.getId()) {
            if (txtEdittiendatcoc.getText().toString().isEmpty() || txtCoDau.getText().toString().isEmpty() || txtdienthoai.getText().toString().isEmpty() || txtEditngay.getText().toString().isEmpty() || txtEditca.getText().toString().isEmpty() || txtEdittiendatcoc.getText().toString().isEmpty() || txtEditLượngBan.getText().toString().isEmpty() || txtEditDutru.getText().toString().isEmpty())
                Toast.makeText(this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
            else {
                try {
                    ContentValues values = new ContentValues();
                    values.put("MaKH", MaKH);
                    values.put("TenChure", txtChuRe.getText().toString());
                    values.put("tenCoDau", txtCoDau.getText().toString());
                    values.put("Dienthoai", txtdienthoai.getText().toString());
                    values.put("ngay", txtEditngay.getText().toString());
                    values.put("ca", txtEditca.getText().toString());
                    values.put("tiendatcoc", Integer.parseInt(txtEdittiendatcoc.getText().toString()));
                    values.put("soluongBan", Integer.parseInt(txtEditLượngBan.getText().toString()));
                    values.put("sobanDutru", Integer.parseInt(txtEditDutru.getText().toString()));
                    database.updateWithOnConflict("ThongTin", values, "Tensanh=?", new String[]{Tensanh}, SQLiteDatabase.CONFLICT_FAIL);
                    Toast.makeText(this, "Cập nhật tiệc thành công", Toast.LENGTH_SHORT).show();
                } catch (SQLiteConstraintException SQLe) {
                    Toast.makeText(this, "Cập nhật tiệc thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



}

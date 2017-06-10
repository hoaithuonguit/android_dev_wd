package com.example.vukhachoi.weddingmanagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.Adapter_hoadon_dichvu;
import model.Dichvu;
import model.TiecCuoi;
import sqlite.Databasehelper;

public class LapHoaDon_ThanhToan extends AppCompatActivity {

    TextView txtSanh_Laphoadon;
    TextView txtChuRe_LapHoaDon;
    TextView txtCoDau_LapHoaDon;
    EditText txtSLBan;
    TextView txtDonGiaBan;
    TextView txt_NgayThanhToan;
    TextView txtTongTienBan;
    TiecCuoi temp;
    TextView txttien_dichvu;
    TextView txttien_hoadon;
    TextView txttien_datcoc;
    TextView txttien_conlai;
    TextView txtsongaytre;
    TextView txtphantram;
    ArrayList<Dichvu>dv;
    ListView lv_dv;
    float a=0;
    float tiendv=0;
    ArrayAdapter<Dichvu>adapter;
    DecimalFormat x=new DecimalFormat("#.##");
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    SQLiteDatabase database;
    Button btnLap;
    Button btnHuy;
    int check;
    Databasehelper myDatabase = new Databasehelper(this);
    View ly;
    int songaytre=0;
    float temp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_hoa_don__thanh_toan);


        addControls();

        addEvents();
    }
    private void addEvents() {

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values=new ContentValues();
                values.put("Makh",temp.getMakh());
                values.put("Soluongban",Integer.parseInt(txtSLBan.getText().toString()));
                values.put("Dongia",temp.getTienban());
                values.put("Tiendatcoc",temp.getTiendatcoc());
                values.put("Tongtien",temp1);
                values.put("Nghd",df.format(Calendar.getInstance().getTime()));

                DateFormat ng = new SimpleDateFormat("dd");
                values.put("ngay",ng.format(Calendar.getInstance().getTime()));

                DateFormat th = new SimpleDateFormat("MM");
                values.put("thang",th.format(Calendar.getInstance().getTime()));

                DateFormat n = new SimpleDateFormat("yyyy");
                values.put("nam",n.format(Calendar.getInstance().getTime()));

                database.insert("hoadon",null,values);
                database.delete("thongtin","makh='"+temp.getMakh()+"'",null);
                Toast.makeText(LapHoaDon_ThanhToan.this,"Lập hóa đon thành công",Toast.LENGTH_LONG).show();


                finish();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtSLBan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtTongTienBan.setText("Tổng tiền bàn(triệu đồng): ");
                txttien_hoadon.setText("Tổng tiền hóa đơn(triệu đồng): ");
                txttien_conlai.setText("Còn lại(triệu đồng): ");
                if(!txtSLBan.getText().toString().equals("")) {
                    a = ((float)temp.getTienban()/1000000) * Float.parseFloat(txtSLBan.getText().toString());
                    txtTongTienBan.setText(txtTongTienBan.getText().toString() + x.format(a));
                    temp1=(float)(a+tiendv)*(1+((float)songaytre/100));
                    txttien_hoadon.setText(txttien_hoadon.getText().toString()+x.format(temp1));
                    //
                    float conlai=(temp.getTiendatcoc()-temp1*1000000)/1000000;
                    txttien_conlai.setText(txttien_conlai.getText().toString()+x.format(conlai));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addControls() {

        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        Cursor cursorcheck=database.rawQuery("select datquydinh from quydinh",null);
        cursorcheck.moveToFirst();
        check=cursorcheck.getInt(0);

        ly=findViewById(R.id.ly);


        temp= (TiecCuoi) getIntent().getSerializableExtra("tieccuoi");
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_ThanhToan);
        toolbar.setTitle("Lập hóa đơn");
        setSupportActionBar(toolbar);

        lv_dv= (ListView) findViewById(R.id.lv_dichvu);
        dv=new ArrayList<>();
        dv=temp.getDv();
        adapter=new Adapter_hoadon_dichvu(LapHoaDon_ThanhToan.this,R.layout.item_hoadon_dichvu,dv);
        lv_dv.setAdapter(adapter);

        btnLap= (Button) findViewById(R.id.btnLap);
        btnHuy= (Button) findViewById(R.id.btnHuy);

        txtSanh_Laphoadon= (TextView) findViewById(R.id.txtSanh_LapHoaDon);
        txtChuRe_LapHoaDon= (TextView) findViewById(R.id.txtChuRe_LapHoaDon);
        txtCoDau_LapHoaDon= (TextView) findViewById(R.id.txtCoDau_LapHoaDon);
        txtSLBan= (EditText) findViewById(R.id.txtSLBan);
        txtDonGiaBan= (TextView) findViewById(R.id.txtDonGiaBan);
        txt_NgayThanhToan= (TextView) findViewById(R.id.txt_NgayThanhToan);
        txtTongTienBan= (TextView) findViewById(R.id.txtTongTienBan);
        txttien_dichvu= (TextView) findViewById(R.id.txttien_dichvu);
        txttien_hoadon= (TextView) findViewById(R.id.txttien_hoadon);
        txttien_datcoc= (TextView) findViewById(R.id.txttien_datcoc);
        txttien_conlai= (TextView) findViewById(R.id.txttien_conlai);


        String date = df.format(Calendar.getInstance().getTime());
        txt_NgayThanhToan.setText(txt_NgayThanhToan.getText()+date);
        txtChuRe_LapHoaDon.setText(temp.getChure());
        txtSanh_Laphoadon.setText(txtSanh_Laphoadon.getText()+temp.getSanh());
        txtCoDau_LapHoaDon.setText(temp.getCodau());



        for(Dichvu dv:temp.getDv())
        {
            tiendv+=(float)(dv.getDongia()*dv.getSoluong());
        }
        tiendv=(float)tiendv/1000000;


        float tien=(float)temp.getTienban()/1000000;
        txtDonGiaBan.setText(txtDonGiaBan.getText().toString()+tien);

        txttien_dichvu.setText(txttien_dichvu.getText().toString()+x.format(tiendv));


        txttien_datcoc.setText(txttien_datcoc.getText().toString()+(float)temp.getTiendatcoc()/1000000);


        if(check==1)
        {
            ly.setVisibility(View.VISIBLE);
            txtsongaytre= (TextView) findViewById(R.id.txtsongaytre);
            txtphantram= (TextView) findViewById(R.id.txtphantram);
            Calendar now=Calendar.getInstance();
            Calendar ngaycuoi=Calendar.getInstance();

            String ngaycuoitemp=temp.getNgay();
            String[] chia=ngaycuoitemp.split("/");
            ngaycuoi.set(Integer.parseInt(chia[2]),Integer.parseInt(chia[1]),Integer.parseInt(chia[0]));
            DateFormat chiangay = new SimpleDateFormat("dd/MM/yyyy");
            String ngayhientai=chiangay.format(now.getTime());
            String[] chia1=ngayhientai.split("/");
            now.set(Integer.parseInt(chia1[2]),Integer.parseInt(chia1[1]),Integer.parseInt(chia1[0]));
            long songaytretam=(now.getTimeInMillis()-ngaycuoi.getTimeInMillis());
            songaytre= (int) (songaytretam/(24 * 60 * 60 * 1000));
            txtsongaytre.setText(songaytre+"");
            txtphantram.setText(songaytre+"");
        }
    }
}

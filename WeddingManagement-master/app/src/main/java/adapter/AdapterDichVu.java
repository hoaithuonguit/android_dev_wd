package adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vukhachoi.weddingmanagement.R;

import java.util.List;

import model.Dichvu;
import sqlite.Databasehelper;

/**
 * Created by Vu Khac Hoi on 3/5/2017.
 */

public class AdapterDichVu extends ArrayAdapter<Dichvu> {
    Activity context; int resource; List<Dichvu> objects;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    public AdapterDichVu(Activity context, int resource, List<Dichvu> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View Row;
        myDatabase = new Databasehelper(getContext());
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        LayoutInflater inflater=this.context.getLayoutInflater();
        Row=inflater.inflate(this.resource,null);
        TextView txtTendichvu= (TextView) Row.findViewById(R.id.txtTendichvu);
        TextView txtGiaDichVu= (TextView) Row.findViewById(R.id.txtGiaDichvu);
        final TextView txtSoLuong= (TextView) Row.findViewById(R.id.txtSoLuong);
        final CheckBox ckb= (CheckBox) Row.findViewById(R.id.ckbLuachon);
        final Dichvu dichvu=this.objects.get(position);
        txtTendichvu.setText(dichvu.getTendichvu().toString());
        txtSoLuong.setText(dichvu.getSoluong()+"");
        txtGiaDichVu.setText(dichvu.getDongia()+"");


        try
        {
            Cursor cursorCheck=database.rawQuery("select * from DatDichVu join Dichvu on MaDatDichVu=MaDV where TenDV=? and  MaKH=?",new String[]{dichvu.getTendichvu().toString(),dichvu.getMaKH().toString()});
            cursorCheck.moveToFirst();
            if(cursorCheck.getString(0)!=null&&cursorCheck.getString(1).equals(dichvu.getMaKH())){
                ckb.setChecked(true);
                txtSoLuong.setText(cursorCheck.getString(2).toString());
            }
        }catch (Exception e)
        {

        }


        ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor= database.rawQuery("select Madatdichvu from DichVu where TenDV=?",new String[]{dichvu.getTendichvu().toString()});
                cursor.moveToFirst();
                if( Integer.parseInt(txtSoLuong.getText().toString())==0) ckb.setChecked(false);
                else {
                    if (((CheckBox) v).isChecked()) {

                        try {
                            ContentValues values = new ContentValues();
                            values.put("MaKH", dichvu.getMaKH());
                            values.put("MaDv", cursor.getString(0));
                            values.put("SoLuong", txtSoLuong.getText().toString());


                            database.insert("DatDichVu", null, values);
                        }
                        catch(Exception e)
                        {

                        }
                    } else {
                        database.delete("DatDichVu", "MaKH=? and MaDv=?", new String[]{dichvu.getMaKH(), cursor.getString(0).toString()});
                    }
                }
            }
        });



        txtSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Cursor cursor= database.rawQuery("select Madatdichvu from DichVu where TenDV=?",new String[]{dichvu.getTendichvu().toString()});
                cursor.moveToFirst();
                try {
                    if (Integer.parseInt(txtSoLuong.getText().toString()) == 0)
                        ckb.setChecked(false);
                    else {
                        ckb.setChecked(true);
                        if (ckb.isChecked()) {

                            try {
                                ContentValues values = new ContentValues();
                                values.put("MaKH", dichvu.getMaKH());
                                values.put("MaDv", cursor.getString(0));
                                values.put("SoLuong", txtSoLuong.getText().toString());

                                database.insertWithOnConflict("DatDichVu", null, values, SQLiteDatabase.CONFLICT_FAIL);
                            } catch (SQLiteConstraintException e) {
                                try {
                                    ContentValues values = new ContentValues();
                                    values.put("Soluong", txtSoLuong.getText().toString());
                                    database.updateWithOnConflict("DatDichVu", values, "MaKH=? and MaDV=?", new String[]{dichvu.getMaKH(), cursor.getString(0)}, SQLiteDatabase.CONFLICT_FAIL);
                                } catch (SQLiteConstraintException SQLe) {
                                    ckb.setChecked(false);
                                    txtSoLuong.setText("0");
                                }
                            }
                        }
                    }
                }catch (Exception e)
                {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return Row;
    }


}

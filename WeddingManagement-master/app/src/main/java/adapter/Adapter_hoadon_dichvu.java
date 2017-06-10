package adapter;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vukhachoi.weddingmanagement.R;

import java.util.List;

import model.Dichvu;
import sqlite.Databasehelper;

/**
 * Created by billy on 09-Mar-17.
 */

public class Adapter_hoadon_dichvu extends ArrayAdapter<Dichvu> {
    Activity context; int resource; List<Dichvu> objects;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    int stt=1;
    public Adapter_hoadon_dichvu(Activity context, int resource, List<Dichvu> objects) {
        super(context, resource, objects);
        this.objects=objects;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        TextView txtstt= (TextView) row.findViewById(R.id.txtstt);
        TextView txttendv= (TextView) row.findViewById(R.id.txttendv);
        TextView txt_hoadon_sl= (TextView) row.findViewById(R.id.txt_hoadon_sl);
        TextView txt_hoadon_dongia= (TextView) row.findViewById(R.id.txt_hoadon_dongia);
        TextView txt_hoadon_thanhtien= (TextView) row.findViewById(R.id.txt_hoadon_thanhtien);
        stt=position+1;
        Dichvu dv=this.objects.get(position);
        txtstt.setText(stt+"");
        txttendv.setText(dv.getTendichvu()+"");
        txt_hoadon_sl.setText(dv.getSoluong()+"");
        txt_hoadon_dongia.setText((float)dv.getDongia()/1000000+"");
        txt_hoadon_thanhtien.setText((float)(dv.getDongia()*dv.getSoluong())/1000000+"");
        return row;
    }
}

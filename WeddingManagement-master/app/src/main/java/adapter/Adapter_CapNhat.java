package adapter;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vukhachoi.weddingmanagement.R;

import java.util.List;

import model.MonAn;
import sqlite.Databasehelper;

/**
 * Created by Vu Khac Hoi on 3/14/2017.
 */

public class Adapter_CapNhat extends ArrayAdapter<MonAn> {

    Activity context;
    int resource;
    List<MonAn> objects;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    public Adapter_CapNhat(Activity context, int resource, List<MonAn> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        myDatabase = new Databasehelper(context);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        ImageView imgHinhDaiDien= (ImageView) row.findViewById(R.id.imgHinhDaiDien);
        TextView txtTenMonAn= (TextView) row.findViewById(R.id.txtTenMonAn);
        TextView txtGiaMonAn=(TextView) row.findViewById(R.id.txtGiaMonAn);
        CheckBox ckbMonAn= (CheckBox) row.findViewById(R.id.ckbMonAn);
        MonAn monAn=this.objects.get(position);
        if(monAn.getVisible())ckbMonAn.setVisibility(View.VISIBLE);
        else ckbMonAn.setVisibility(View.GONE);
        txtTenMonAn.setText(monAn.getTenMonAn().toString());
        txtGiaMonAn.setText(monAn.getGia()+"$");
        imgHinhDaiDien.setImageResource(R.drawable.imgmonan);
        ckbMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objects.get(position).setXoa(((CheckBox)v).isChecked());
            }
        });
        return row;
    }

    public void Remove()
    {
        int i=0;
        for(;i<objects.size();i++)
        {
            if(objects.get(i).getXoa())
            {
                database.delete("MonAn","TenMonAn=?",new String[]{objects.get(i).getTenMonAn()});
                objects.remove(i);
               notifyDataSetChanged();
                i=0;
            }
        }
    }
}

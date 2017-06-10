package adapter;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vukhachoi.weddingmanagement.R;

import java.util.List;

import model.Dichvu;
import sqlite.Databasehelper;

import static com.example.vukhachoi.weddingmanagement.R.id.ckbMonAn;

/**
 * Created by Vu Khac Hoi on 3/14/2017.
 */

public class Adapter_CapNhat_DichVu extends ArrayAdapter<Dichvu> {
    Activity context;
    int resource;
    List<Dichvu> objects;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    public Adapter_CapNhat_DichVu(Activity context, int resource, List<Dichvu> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        myDatabase = new Databasehelper(context);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        ImageView imgHinhDaiDien= (ImageView) row.findViewById(R.id.imgHinhDaiDien);
        TextView txtTenMonAn= (TextView) row.findViewById(R.id.txtTenMonAn);
        TextView txtGiaMonAn=(TextView) row.findViewById(R.id.txtGiaMonAn);
        CheckBox ckbDichVu= (CheckBox) row.findViewById(ckbMonAn);
        Dichvu dichvu=this.objects.get(position);
        txtTenMonAn.setText(dichvu.getTendichvu().toString());
        txtGiaMonAn.setText(dichvu.getDongia()+"$");
        imgHinhDaiDien.setImageResource(R.drawable.imgdichvu);
        if(dichvu.getVisible())ckbDichVu.setVisibility(View.VISIBLE);
        else ckbDichVu.setVisibility(View.GONE);
        ckbDichVu.setOnClickListener(new View.OnClickListener() {
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
                database.delete("DichVu","TenDV=?",new String[]{objects.get(i).getTendichvu()});
                objects.remove(i);
                notifyDataSetChanged();
                i=0;
            }
        }
    }
}

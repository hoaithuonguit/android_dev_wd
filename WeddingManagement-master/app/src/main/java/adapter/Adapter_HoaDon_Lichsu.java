package adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vukhachoi.weddingmanagement.R;

import java.util.List;

import model.Hoadon;

/**
 * Created by billy on 12-Mar-17.
 */

public class Adapter_HoaDon_Lichsu extends ArrayAdapter<Hoadon>{

    Activity context;
    int resource;
    List<Hoadon> objects;
    public Adapter_HoaDon_Lichsu(Activity context, int resource, List<Hoadon> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);

        TextView txtMaKH_hoadon_lichsu=(TextView) row.findViewById(R.id.txtMaKH_hoadon_lichsu);
        TextView txtNgay_hoadon_lichsu=(TextView) row.findViewById(R.id.txtNgay_hoadon_lichsu);
        TextView txtMaHD= (TextView) row.findViewById(R.id.txtMaHD);
        Hoadon hoadon =this.objects.get(position);
        txtMaKH_hoadon_lichsu.setText(txtMaKH_hoadon_lichsu.getText()+hoadon.getMakh());
        txtNgay_hoadon_lichsu.setText(txtNgay_hoadon_lichsu.getText()+hoadon.getNgthanhtoan());
        txtMaHD.setText(txtMaHD.getText()+hoadon.getMahd());
        if(hoadon.getCheck()==1)
            row.setBackgroundColor(0xff00ffff);

        return row;
    }
}

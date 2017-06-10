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

import model.TiecCuoi;

/**
 * Created by billy on 07-Mar-17.
 */

public class Adapter_HoaDon extends ArrayAdapter<TiecCuoi> {

    Activity context;
    int resource;
    List<TiecCuoi> objects;
    public Adapter_HoaDon(Activity context, int resource, List<TiecCuoi> objects) {
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
        TextView chure= (TextView) row.findViewById(R.id.txtChuRe_hoadon);
        TextView codau=(TextView) row.findViewById(R.id.txtCoDau_hoadon);
        TextView sanh=(TextView) row.findViewById(R.id.txtSanh_hoadon);
        TextView ngay=(TextView) row.findViewById(R.id.txtNgay_hoadon);
        TextView makh=(TextView) row.findViewById(R.id.txtMaKH_hoadon);
        TiecCuoi tc=this.objects.get(position);

        chure.setText(chure.getText()+tc.getChure());
        codau.setText(codau.getText()+tc.getCodau());
        sanh.setText(sanh.getText()+tc.getSanh());
        ngay.setText(ngay.getText()+tc.getNgay());
        makh.setText(makh.getText()+tc.getMakh());
        if(tc.getCheck()==1)
            row.setBackgroundColor(0xff00ffff);
        return row;
    }
}

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
 * Created by billy on 03-Mar-17.
 */

public class adapterTraCuu extends ArrayAdapter<TiecCuoi>{
    Activity context;
    int resource;
    List<TiecCuoi> objects;
    public adapterTraCuu(Activity context, int resource, List<TiecCuoi> objects) {
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
        TextView chure= (TextView) row.findViewById(R.id.txtChuRe);
        TextView codau=(TextView) row.findViewById(R.id.txtCoDau);
        TextView sanh=(TextView) row.findViewById(R.id.txtSanh);
        TextView ngay=(TextView) row.findViewById(R.id.txtNgay);
        TextView ca=(TextView) row.findViewById(R.id.txtCa);
        TextView soban=(TextView) row.findViewById(R.id.txtSoBan);

        //TextView makh= (TextView) row.findViewById(R.id.txtMaKH);

        TiecCuoi tc=this.objects.get(position);
        //makh.setText(makh.getText()+tc.getMakh());
        if(tc.getCheck()==1)
            row.setBackgroundColor(0xff00ffff);
        chure.setText(chure.getText()+tc.getChure());
        codau.setText(codau.getText()+tc.getCodau());
        sanh.setText(sanh.getText()+tc.getSanh());
        ngay.setText(ngay.getText()+tc.getNgay());
        ca.setText(ca.getText()+tc.getCa());
        String temp=tc.getSoban()+"";
        soban.setText(soban.getText()+temp);
        return row;

    }

}

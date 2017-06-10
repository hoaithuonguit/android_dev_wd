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

import model.BaoCao;


/**
 * Created by billy on 13-Mar-17.
 */

public class AdapterBaoCao extends ArrayAdapter<BaoCao> {
    Activity context;
    int resource;
    List<BaoCao> objects;
    public AdapterBaoCao(Activity context, int resource, List<BaoCao> objects) {
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
        TextView txtstt_baocao= (TextView) row.findViewById(R.id.txtstt_baocao);
        TextView txtNgay_baocao=(TextView) row.findViewById(R.id.txtNgay_baocao);
        TextView txtsl_baocao=(TextView) row.findViewById(R.id.txtsl_baocao);
        TextView txtDoanhThu=(TextView) row.findViewById(R.id.txtDoanhThu);

        //TextView makh= (TextView) row.findViewById(R.id.txtMaKH);

        BaoCao baoCao=this.objects.get(position);
        txtstt_baocao.setText(position+1+"");
        txtNgay_baocao.setText(baoCao.getNgay()+"");
        txtsl_baocao.setText(baoCao.getSl()+"");
        txtDoanhThu.setText(baoCao.getDoanhthu()+"");
        return row;

    }
}

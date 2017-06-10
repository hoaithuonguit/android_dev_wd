package adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vukhachoi.weddingmanagement.R;

import java.util.List;

import hall.wedding.management.NameHall;
import sqlite.Databasehelper;

/**
 * Created by Vu Khac Hoi on 3/3/2017.
 */

public class RecyclerNameHallAdapter extends RecyclerView.Adapter<RecyclerNameHallAdapter.DataViewHolde> implements View.OnClickListener {

    public List<NameHall> hallName;
    public Context context;
    RecyclerViewItemClickInterface listener;

    public RecyclerNameHallAdapter(List<NameHall> hallDetails, Context context) {
        this.hallName = hallDetails;
        this.context = context;
    }



    @Override
    public RecyclerNameHallAdapter.DataViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View Itemview;
        Itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name_hall,parent,false);
        Itemview.setOnClickListener(this);


        return  new RecyclerNameHallAdapter.DataViewHolde(Itemview);
    }



    @Override
    public void onBindViewHolder(RecyclerNameHallAdapter.DataViewHolde holder, final int position) {
        holder.itemView.setTag(hallName.get(position));
        holder.txtbantoida.setText(hallName.get(position).getBanToiDa()+"");
        holder.txtgiatoithieu.setText(hallName.get(position).getGiaToiThieu()+"");
        holder.txtNamehall.setText(hallName.get(position).getNamehall()+"");
        holder.imgActive.setImageResource(hallName.get(position).getImgActive());
        if(hallName.get(position).isVisible())
            holder.ckbDelete.setVisibility(View.VISIBLE);
        else  holder.ckbDelete.setVisibility(View.INVISIBLE);
        holder.ckbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hallName.size()>1)
                    hallName.get(position).setCheck(((CheckBox)v).isChecked());
                else ((CheckBox)v).setChecked(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hallName==null ? 0:hallName.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(hallName.get(position).isActive())
            return 1;
        else return 2;
    }



    public void setOnItemClickListener(RecyclerViewItemClickInterface listener){

        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if (listener != null) {
            NameHall people = (NameHall) v.getTag();

            listener.onItemclick(v, people);
        }
    }
    public void remove() {
        Databasehelper myDatabase = new Databasehelper(context);

        myDatabase.Khoitai();
        SQLiteDatabase database = myDatabase.getMyDatabase();

        int i=0;
        for (;i<hallName.size();i++) {
            if(hallName.get(i).isCheck()) {
                database.delete("Sanh","TenSanh=?", new String[]{hallName.get(i).getNamehall().toString()});
                database.delete("ThongTin","TenSanh=?",new String[]{hallName.get(i).getNamehall().toString()});
                hallName.remove(i);

                notifyItemRemoved(i);
                i=0;
            }

        }

    }
    public  class DataViewHolde extends RecyclerView.ViewHolder {
        TextView txtbantoida,txtgiatoithieu,txtNamehall;
        CheckBox ckbDelete;
        ImageView imgActive;
        public DataViewHolde(View itemView) {
            super(itemView);
            txtbantoida= (TextView) itemView.findViewById(R.id.txtbantoida);
            txtgiatoithieu= (TextView) itemView.findViewById(R.id.txtgiatoithieu);
            txtNamehall= (TextView) itemView.findViewById(R.id.txtNameHall);
            imgActive= (ImageView) itemView.findViewById(R.id.imgActive);
            ckbDelete= (CheckBox) itemView.findViewById(R.id.ckbDelete);

        }
    }
}

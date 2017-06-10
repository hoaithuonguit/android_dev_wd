package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vukhachoi.weddingmanagement.NameHalllActivity;
import com.example.vukhachoi.weddingmanagement.R;

import java.util.List;

import hall.wedding.management.HallDetail;

/**
 * Created by Vu Khac Hoi on 2/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolde> {

    public List<HallDetail> hallDetails;
    public Context context;

    public RecyclerViewAdapter(List<HallDetail> hallDetails, Context context) {
        this.hallDetails = hallDetails;
        this.context = context;
    }



    @Override
    public DataViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View Itemview;
        switch (viewType)
        {
            case 1:
                Itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active,parent,false);
                break;
            case 2:
                Itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemstop,parent,false);
                break;
            default:
                Itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active,parent,false);
                break;
        }




        return  new DataViewHolde(Itemview);
    }



    @Override
    public void onBindViewHolder(DataViewHolde holder, int position) {
        holder.txtTitle.setText(hallDetails.get(position).getNameHall());


    }

    @Override
    public int getItemCount() {
        return hallDetails==null ? 0:hallDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(hallDetails.get(position).isActive())
            return 2;
        else return 1;
    }

    public  class DataViewHolde extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public DataViewHolde(View itemView) {
            super(itemView);
            txtTitle= (TextView) itemView.findViewById(R.id.txtTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Position=getAdapterPosition();
                    if(Position!=RecyclerView.NO_POSITION) {
                        HallDetail item = hallDetails.get(Position);
                        Intent intent=new Intent(context, NameHalllActivity.class);
                        intent.putExtra("NameHall",item.getNameHall());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}

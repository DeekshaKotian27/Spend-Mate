package com.example.deeksha.spendmate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.viewHolder>{

    private Context context;
    ArrayList<incomedata> data;

    public Myadapter(Context context,ArrayList<incomedata> data) {
        this.context=context;
        this.data = data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        String sign=data.get(i).getSign();//sign of the amount
        viewHolder.trdate.setText(data.get(i).getTrdate());
        viewHolder.amt.setText(sign+"₹"+data.get(i).getAmt());
        viewHolder.type.setText(data.get(i).getType());
        final int cust_id=data.get(i).getCust_id();
        final Integer tr_id= Integer.valueOf(data.get(i).getTr_id());
        final Integer flag=data.get(i).getFlag();
        if(flag==0){
            viewHolder.cardView.setCardBackgroundColor(Color.argb(255, 105, 187, 105));
        }
        else{
            viewHolder.cardView.setCardBackgroundColor(Color.argb(255, 239, 100, 100));
        }
        viewHolder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),edit.class);
                intent.putExtra("cust_id",cust_id);
                intent.putExtra("tr_id",tr_id);
                intent.putExtra("flag",flag);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView trdate,type,amt;
        CardView cardView;
        View v;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            trdate=(TextView)itemView.findViewById(R.id.date);
            type=(TextView)itemView.findViewById(R.id.type);
            amt=(TextView)itemView.findViewById(R.id.amt);
            cardView=(CardView)itemView.findViewById(R.id.cardView);
            v=itemView;

        }
    }
}
package com.hmdapp.finaltailor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmdapp.finaltailor.Models.Model;
import com.hmdapp.finaltailor.Models.Payment;
import com.hmdapp.finaltailor.R;

import java.util.List;

public class Adaptar_show_payes extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Payment> items ;

    private Context ctx;
    private  OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Payment obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public Adaptar_show_payes(Context context, List<Payment> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView amount;


        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);

            date = (TextView) v.findViewById(R.id.txt_date_pay);
            amount = (TextView) v.findViewById(R.id.txt_amount_pay);


            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pay_item, parent, false);
        vh = new Adaptar_show_payes.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Adaptar_show_payes.OriginalViewHolder) {
            Adaptar_show_payes.OriginalViewHolder view = (Adaptar_show_payes.OriginalViewHolder) holder;

            Payment model = items.get(position);
            view.date.setText(model.getDate());
            view.amount.setText(model.getAmount()+"");


            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
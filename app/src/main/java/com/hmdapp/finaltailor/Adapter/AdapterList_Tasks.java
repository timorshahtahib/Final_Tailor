package com.hmdapp.finaltailor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmdapp.finaltailor.Models.Order;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterList_Tasks extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Order obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterList_Tasks(Context context, List<Order> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView date;
        public TextView model;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.img_status);
            name = v.findViewById(R.id.txt_namee);
            date = v.findViewById(R.id.txt_date);
            model = v.findViewById(R.id.txt_model);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_2, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Order order = items.get(position);
            view.name.setText(order.getCloth().getCustomer().getName() + "   ");
            view.date.setText(order.getDeliverDate());
            view.model.setText(order.getCloth().getDes());
            if (order.getCom_state() == 0) {
                Tools.displayImageRound(ctx, view.image, R.drawable.delet);
            } else {
                Tools.displayImageRound(ctx, view.image, R.drawable.ok);
            }


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
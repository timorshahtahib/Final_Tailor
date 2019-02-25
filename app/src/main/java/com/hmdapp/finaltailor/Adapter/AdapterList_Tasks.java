package com.hmdapp.finaltailor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmdapp.finaltailor.Models.Task;
import com.hmdapp.finaltailor.R;
import com.hmdapp.finaltailor.Utlity.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterList_Tasks extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Task> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Task obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterList_Tasks(Context context, List<Task> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView date;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.img_status);
            name = (TextView) v.findViewById(R.id.txt_namee);
            date = (TextView) v.findViewById(R.id.txt_date);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Task task = items.get(position);
            view.name.setText(task.getCustomer().getName()+"   ");
            view.date.setText(task.getDeliverDate());
            if(task.getState() == 0){
                Tools.displayImageRound(ctx, view.image, R.drawable.delet);
            }else{
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
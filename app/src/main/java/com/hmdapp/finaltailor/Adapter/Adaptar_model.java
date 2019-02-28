package com.hmdapp.finaltailor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmdapp.finaltailor.Models.Model;
import com.hmdapp.finaltailor.R;

import java.util.ArrayList;
import java.util.List;

public class Adaptar_model  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Model> items ;

    private Context ctx;
    private  OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Model obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public Adaptar_model(Context context, List<Model> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name;


        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);

            name = (TextView) v.findViewById(R.id.name_model);


            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model_cloth, parent, false);
        vh = new Adaptar_model.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Adaptar_model.OriginalViewHolder) {
            Adaptar_model.OriginalViewHolder view = (Adaptar_model.OriginalViewHolder) holder;

            Model model = items.get(position);
            view.name.setText(model.getName());


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
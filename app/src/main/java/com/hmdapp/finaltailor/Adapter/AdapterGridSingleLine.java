package com.hmdapp.finaltailor.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmdapp.finaltailor.R;


public class AdapterGridSingleLine extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private  String na[];
  private Object va[];

    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;




    public AdapterGridSingleLine(Context context, String [] na,Object va[]) {
        this.na=na;
        this.va=va;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView name,value;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);

            name = (TextView) v.findViewById(R.id.txt_name);
            value = (TextView) v.findViewById(R.id.txt_value);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.name.setText(na[position]+"");
            view.value.setText(va[position]+"");

        }
    }

    @Override
    public int getItemCount() {
        return na.length;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

}
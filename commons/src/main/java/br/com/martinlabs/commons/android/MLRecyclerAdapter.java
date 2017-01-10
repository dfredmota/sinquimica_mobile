package br.com.martinlabs.commons.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gil on 10/3/16.
 */

public class MLRecyclerAdapter extends RecyclerView.Adapter<MLRecyclerAdapter.MLViewHolder>  {

    int layoutRes;
    Context context;
    int itemCount;
    ViewHolderBinder binder;
    RecyclerItemClickListener clickListener;

    public MLRecyclerAdapter(Context context, int layoutRes, ViewHolderBinder binder){
        this.layoutRes = layoutRes;
        this.context = context;
        this.binder = binder;
    }

    public interface ViewHolderBinder {
        void onBindViewHolder(MLViewHolder holder, int position);
    }

    public interface RecyclerItemClickListener {
        void onClick(View v, int position);
    }

    @Override
    public MLViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        return new MLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MLViewHolder holder, int position) {
        binder.onBindViewHolder(holder, position);
    }

    public void setOnItemClickListener(RecyclerItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){

        return itemCount;
    }

    public static MLRecyclerAdapter getInstance(View recyV) {
        RecyclerView.Adapter adapter;

        if (recyV != null && recyV instanceof RecyclerView) {
            adapter = ((RecyclerView) recyV).getAdapter();
        } else {
            return null;
        }

        if (adapter != null && adapter instanceof MLRecyclerAdapter) {
            return (MLRecyclerAdapter) adapter;
        } else {
            return null;
        }
    }

    public class MLViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View view;

        public MLViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }

        public View f(int resId)
        {
            return view.findViewById(resId);
        }

        public TextView fText(int resId)
        {
            return (TextView) f(resId);
        }

        public ImageView fImg(int resId)
        {
            return (ImageView) f(resId);
        }

        public EditText fEdit(int resId)
        {
            return (EditText) f(resId);
        }
    }

}

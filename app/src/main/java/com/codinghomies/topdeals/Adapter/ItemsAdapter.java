package com.codinghomies.topdeals.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codinghomies.topdeals.Model.Items;
import com.codinghomies.topdeals.R;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public class ItemsAdapter extends FirestorePagingAdapter<Items, ItemsAdapter.ItemsViewHolder> {

    Context context;

    private OnListItemClick onListItemClick;

    public ItemsAdapter(@NonNull FirestorePagingOptions<Items> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemsViewHolder holder, int position, @NonNull Items items) {

        context=holder.itemView.getContext();

        holder.tv_title.setText(items.getTitle());
        holder.tv_price.setText(items.getCurrentPrice());
        Glide.with(context).load(items.getThumbnail()).into(holder.iv_image);
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_row, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state){
            case LOADING_INITIAL:
                //add toast message loading initial data
                break;
            case LOADING_MORE:
                //add toast message loading next page
                break;
            case FINISHED:
                //add toast message all data loaded
                Toast.makeText(context, "You've caught all data", Toast.LENGTH_LONG).show();
                break;
            case ERROR:
                //add toast message error loading data
                Toast.makeText(context, "Error getting data", Toast.LENGTH_LONG).show();
                break;
            case LOADED:
                //add toast message total items loaded count
                break;
        }
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_title, tv_price, tv_url;  //Fields of TextView
        private ImageView iv_image;

        public ItemsViewHolder(@NonNull View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.text_title_tv);
            tv_price = (TextView) view.findViewById(R.id.tv_price_tv);
            tv_url = (TextView) view.findViewById(R.id.tv_url_tv);
            iv_image = (ImageView) view.findViewById(R.id.item_image);

            tv_url.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()), getAdapterPosition());
        }

    }

    public interface OnListItemClick{
        //void onItemClick(Items documentSnapshot, int position);
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

}

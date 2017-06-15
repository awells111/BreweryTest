package com.android.wellsandwhistles.brewerytest.data;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wellsandwhistles.brewerytest.R;

import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_DESCRIPTION;
import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_LABEL_ICON;
import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_LABEL_LARGE;
import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_LABEL_MEDIUM;
import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_TITLE;
/**
 * Created by Owner on 6/14/2017.
 */

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerHolder> {

    public interface OnItemClickListener {
        void onItemClick(View v,int position);
    }

    public class BeerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleView;
        public TextView descriptionView;
        public ImageView labelView;

        public BeerHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.beer_title);
            descriptionView = (TextView) itemView.findViewById(R.id.beer_description);
            labelView = (ImageView) itemView.findViewById(R.id.beer_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            postItemClick(this);
        }
    }

    private Cursor mCursor;
    private OnItemClickListener mOnItemClickListener;

    public BeerAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private void postItemClick(BeerHolder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
        }
    }

    @Override
    public BeerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_beer, parent, false);

        return new BeerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BeerHolder holder, int position) {
        mCursor.moveToPosition(position);

        String title = mCursor.getString(INDEX_BEER_TITLE);
        String description = mCursor.getString(INDEX_BEER_DESCRIPTION);

        holder.titleView.setText(title);
        holder.descriptionView.setText(description);
        //todo set label
        //icon is 64x64, medium is 256x256, large is 512x512
        System.out.println("label_icon " + position + ": " + mCursor.getString(INDEX_BEER_LABEL_ICON));
        System.out.println("label_medium " + position + ": " + mCursor.getString(INDEX_BEER_LABEL_MEDIUM));
        System.out.println("label_large " + position + ": " + mCursor.getString(INDEX_BEER_LABEL_LARGE));
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;
        notifyDataSetChanged();
    }
}

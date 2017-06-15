package com.android.wellsandwhistles.brewerytest.data;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.wellsandwhistles.brewerytest.R;
import com.squareup.picasso.Picasso;

import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_DESCRIPTION;
import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_LABEL_ICON;
import static com.android.wellsandwhistles.brewerytest.MainActivity.INDEX_BEER_TITLE;
/**
 * Created by Owner on 6/14/2017.
 */

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerHolder> {

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public class BeerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleView;
        public TextView descriptionView;
        public ImageView labelView;

        public BeerHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.beer_title_list);
            descriptionView = (TextView) itemView.findViewById(R.id.beer_description_list);
            labelView = (ImageView) itemView.findViewById(R.id.beer_label_list);

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

        //Our description will leave blank space if we set its text to ""
        if (!description.equals("")) {
            holder.descriptionView.setText(description);
        } else {
            holder.descriptionView.setVisibility(View.GONE);
        }

        if (!mCursor.getString(INDEX_BEER_LABEL_ICON).equals("")) {
            Picasso.with(holder.labelView.getContext()).load(mCursor.getString(INDEX_BEER_LABEL_ICON)).into(holder.labelView);
        } else {
            holder.labelView.setImageResource(R.drawable.ic_beer_placeholder_icon);
        }
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    public String getItemName(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getString(INDEX_BEER_TITLE);
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;
        notifyDataSetChanged();
    }
}

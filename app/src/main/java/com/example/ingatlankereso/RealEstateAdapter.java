package com.example.ingatlankereso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RealEstateAdapter extends RecyclerView.Adapter<com.example.ingatlankereso.RealEstateAdapter.ViewHolder>
        implements Filterable {
    private ArrayList<Estate> mEstatesData;
    private ArrayList<Estate> mEstatesDataAll;
    private Context mContext;
    private int lastPosition = -1;

    RealEstateAdapter(Context context, ArrayList<Estate> estatesData) {
        this.mEstatesData = estatesData;
        this.mEstatesDataAll = estatesData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_estates, parent,
                false));
    }


    @Override
    public void onBindViewHolder(RealEstateAdapter.ViewHolder holder, int position) {
        Estate currentItem = mEstatesData.get(position);

        holder.bindTo(currentItem);
        //Animation
        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mEstatesData.size();
    }

    @Override
    public Filter getFilter() {
        return estateFilter;
    }
    private final Filter estateFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Estate> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mEstatesDataAll.size();
                results.values = mEstatesDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Estate estate : mEstatesDataAll) {
                    if(estate.getEstateAddress().toLowerCase().contains(filterPattern)) {
                        filteredList.add(estate);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }

            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            mEstatesData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mitemImage;
        private TextView mestateAddress;
        private TextView mestatePrice;
        private TextView mestateSize;
        private TextView mestateRoomNum;
        private TextView mestate_description;
        private TextView mseller_contact;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mitemImage = itemView.findViewById(R.id.itemImage);
            mestateAddress = itemView.findViewById(R.id.estateAddress);
            mestatePrice = itemView.findViewById(R.id.estatePrice);
            mestateSize = itemView.findViewById(R.id.estateSize);
            mestateRoomNum = itemView.findViewById(R.id.estateRoomNum);
            mestate_description = itemView.findViewById(R.id.estate_description);
            mseller_contact = itemView.findViewById(R.id.seller_contact);
        }

        public void bindTo(Estate currentItem) {
            mestateAddress.setText(currentItem.getEstateAddress());
            mestatePrice.setText(currentItem.getEstatePrice());
            mestateSize.setText(currentItem.getEstateSize());
            mestateRoomNum.setText(currentItem.getEstateRoomNum());
            mestate_description.setText(currentItem.getEstateDescription());
            mseller_contact.setText(currentItem.getSellerContact());

//            Glide.with(mContext).load(currentItem.getImageResource()).into(mitemImage);
        }
    }


}


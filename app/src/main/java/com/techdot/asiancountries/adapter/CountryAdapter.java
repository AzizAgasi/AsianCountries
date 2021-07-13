package com.techdot.asiancountries.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp;
import com.techdot.asiancountries.R;
import com.techdot.asiancountries.model.Language;
import com.techdot.asiancountries.database.CountryEntity;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<CountryEntity> countryList;
    private Context context;

    private List<Language> languagesList;
    private List<String> bordersList;

    public CountryAdapter(List<CountryEntity> countryList) {
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        CountryEntity currentCountry = countryList.get(position);
         holder.countryName.setText(currentCountry.getName());
         holder.countryCapital.setText(currentCountry.getCapital());
         holder.countryRegion.setText(currentCountry.getRegion());
         holder.countrySubregion.setText(currentCountry.getSubregion());
         holder.countryPopulation.setText(currentCountry.getPopulation().toString());

         if (!currentCountry.getLanguage().equals("")) {
             holder.countryLanguage.setText(currentCountry.getLanguage());
         } else {
             holder.countryLanguage.setVisibility(View.GONE);
             holder.languageText.setVisibility(View.GONE);
         }

         if (!currentCountry.getBorders().equals("")) {
             holder.countryBorders.setText(currentCountry.getBorders());
         } else {
             holder.borderText.setVisibility(View.GONE);
             holder.countryBorders.setVisibility(View.GONE);
         }

        GlideApp.with(context)
                .load(Uri.parse(currentCountry.getFlag()))
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        holder.countryFlag.setImageDrawable(resource);
                        return true;
                    }
                })
                .into(holder.countryFlag);
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        TextView countryCapital;
        ImageView countryFlag;
        TextView countryRegion;
        TextView countrySubregion;
        TextView countryPopulation;
        TextView countryLanguage;
        TextView countryBorders;

        TextView languageText;
        TextView borderText;

        ProgressBar progressBar;

        public ViewHolder( View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.countryName);
            countryCapital = itemView.findViewById(R.id.countryCapital);
            countryFlag = itemView.findViewById(R.id.countryFlag);
            countryRegion = itemView.findViewById(R.id.countryRegion);
            countrySubregion = itemView.findViewById(R.id.countrySubregion);
            countryPopulation = itemView.findViewById(R.id.countryPopulation);
            countryLanguage = itemView.findViewById(R.id.countryLanguage);
            countryBorders = itemView.findViewById(R.id.countryBorders);

            borderText = itemView.findViewById(R.id.borderText);
            languageText = itemView.findViewById(R.id.languageText);

            progressBar = itemView.findViewById(R.id.progressBar);

            context = itemView.getContext();
        }
    }
}

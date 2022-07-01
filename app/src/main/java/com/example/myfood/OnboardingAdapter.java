package com.example.myfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardongViewHolder>{

    private List<OnboardingItem> onboardingItems;
    public OnboardingAdapter(List<OnboardingItem> onboardingItems){
        this.onboardingItems = onboardingItems;
    }


    @NonNull
    @Override
    public OnboardongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardongViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_onboarding, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardongViewHolder holder, int position) {
            holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardongViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnboarding;

        public OnboardongViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
            }

            void setOnboardingData(OnboardingItem onboardingitem) {
                textTitle.setText(onboardingitem.getTitle());
                textDescription.setText(onboardingitem.getDescription());
                imageOnboarding.setImageResource(onboardingitem.getImage());



            }
    }
}

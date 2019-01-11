package com.rasel.recyclerviewexpandable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewRecyclerAdapter.ViewHolder> {

    private final List<Clients> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public RecyclerViewRecyclerAdapter(final List<Clients> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.client_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Clients item = data.get(position);

        holder.setIsRecyclable(false);
        holder.tvName.setText(item.getName());
        holder.tvCompany.setText(item.getCompany());
        holder.tvCountry.setText(item.getCountry());

        String picLink = item.getLogo();
        if(!picLink.isEmpty()) {
            Picasso.get().load(picLink).into(holder.imgLogo);
        }
        int size = item.getTags().size();
        for(int i=0; i<size; i++){
            if(item.getTags().get(i).getTag().equalsIgnoreCase("Android")){
                holder.tvAndroid.setVisibility(View.VISIBLE);
            }else if(item.getTags().get(i).getTag().equalsIgnoreCase("Website")){
                holder.tvWebsite.setVisibility(View.VISIBLE);
            }else if(item.getTags().get(i).getTag().equalsIgnoreCase("IOS")){
                holder.tvIos.setVisibility(View.VISIBLE);
            }
        }

        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.imgDownArrow, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.imgDownArrow, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.imgDownArrow.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });
        holder.tvAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "rasel Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvCompany,tvCountry, tvAndroid, tvWebsite, tvIos;
        public RelativeLayout buttonLayout;
        public ImageView imgDownArrow, imgLogo;
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            imgLogo = v.findViewById(R.id.imgLogo);
            tvName = v.findViewById(R.id.tvName);
            tvCompany = v.findViewById(R.id.tvCompany);
            tvCountry = v.findViewById(R.id.tvCountry);
            imgDownArrow = v.findViewById(R.id.imgDown);
            buttonLayout = v.findViewById(R.id.topRelativeLayout);
            expandableLayout = v.findViewById(R.id.expandableLayout);

            tvAndroid = v.findViewById(R.id.tvAndroid);
            tvWebsite = v.findViewById(R.id.tvWebsite);
            tvIos = v.findViewById(R.id.tvIos);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}

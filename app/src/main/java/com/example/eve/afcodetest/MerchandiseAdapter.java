package com.example.eve.afcodetest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Eve on 7/12/17.
 */

public class MerchandiseAdapter extends RecyclerView.Adapter<MerchandiseAdapter.MerchandiseViewHolder> {

    public ArrayList<MerchandiseInfo> merchandiseInfos;
    private Context context;

    public MerchandiseAdapter(ArrayList<MerchandiseInfo> list, Context context) {
        this.merchandiseInfos = list;
        this.context = context;
    }

    @Override
    public MerchandiseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_merchandise, parent, false);

        MerchandiseViewHolder holder = new MerchandiseViewHolder(view);

        return new MerchandiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MerchandiseViewHolder holder, int position) {

        MerchandiseInfo merchandiseInfo = merchandiseInfos.get(position);
//        holder.itemView.setSelected(focusItem == position);
//        holder.getAdapterPosition();

        holder.title.setText(merchandiseInfo.getTitle());

        if (merchandiseInfo.getTopDescription().equals("")) {
            holder.topDescription.setVisibility(View.GONE);
        } else {
            holder.topDescription.setVisibility(View.VISIBLE);
            holder.topDescription.setText(merchandiseInfo.getTopDescription());
        }

        if (merchandiseInfo.getPromoMessage().equals("")) {
            holder.promoMessage.setVisibility(View.GONE);
        } else {
            holder.promoMessage.setVisibility(View.VISIBLE);
            holder.promoMessage.setText(merchandiseInfo.getPromoMessage());
        }

        if (merchandiseInfo.getBottomDescription().equals("")) {
            holder.bottomDescription.setVisibility(View.GONE);
        } else {
            holder.bottomDescription.setVisibility(View.VISIBLE);
            holder.bottomDescription.setText(Html.fromHtml(merchandiseInfo.getBottomDescription()));
        }

        if (merchandiseInfo.getTitleOne().equals("")) {
            holder.titleOne.setVisibility(View.GONE);
        } else {
            holder.titleOne.setVisibility(View.VISIBLE);
            holder.titleOne.setText(merchandiseInfo.getTitleOne());
            holder.targetOne = merchandiseInfo.getTargetOne();
        }

        if (merchandiseInfo.getTitleTwo().equals("")) {
            holder.titleTwo.setVisibility(View.GONE);
        } else {
            holder.titleTwo.setVisibility(View.VISIBLE);
            holder.titleTwo.setText(merchandiseInfo.getTitleTwo());
            holder.targetTwo = merchandiseInfo.getTargetTwo();
        }

        Picasso.with(this.context).load(merchandiseInfo.getBackgroundImage()).into(holder.backgroundImage);
    }

    @Override
    public int getItemCount() {
        return merchandiseInfos.size();
    }

    class MerchandiseViewHolder extends RecyclerView.ViewHolder {
        private TextView topDescription, title, promoMessage,
                bottomDescription, titleOne, titleTwo;
        private ImageView backgroundImage;
        String targetOne, targetTwo;

        public MerchandiseViewHolder(View itemView) {
            super(itemView);

            topDescription = (TextView) itemView.findViewById(R.id.topDescription);
            title = (TextView) itemView.findViewById(R.id.title);
            promoMessage = (TextView) itemView.findViewById(R.id.promoMessage);
            bottomDescription = (TextView) itemView.findViewById(R.id.bottomDescription);
            titleOne = (TextView) itemView.findViewById(R.id.titleOne);
            titleTwo = (TextView) itemView.findViewById(R.id.titleTwo);
            backgroundImage = (ImageView) itemView.findViewById(R.id.backgroundImage);

            titleOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(targetOne));

                    context.startActivity(intent);
                }
            });

            titleTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(targetTwo));

                    context.startActivity(intent);
                }
            });

            bottomDescription.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

}

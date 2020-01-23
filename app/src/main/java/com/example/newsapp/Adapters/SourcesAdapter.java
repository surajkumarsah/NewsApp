package com.example.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Activities.ListStackActivity;
import com.example.newsapp.Models.Source;
import com.example.newsapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder>
{

    Context context;
    List<Source> sourceList;

    public SourcesAdapter(Context context, List<Source> sourceList)
    {
        this.context = context;
        this.sourceList = sourceList;
    }


    @NonNull
    @Override
    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_source, parent, false);
        return new SourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourcesViewHolder holder, int position) {
        holder.textViewName.setText(sourceList.get(position).getName());

    }


    @Override
    public int getItemCount() {

        return sourceList.size();
    }





    public class SourcesViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.source_image) CircleImageView circularImageView;
        @BindView(R.id.source_name) TextView textViewName;

        public SourcesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Source sourceItem = sourceList.get(getAdapterPosition());

                    Intent intent = new Intent(context, ListStackActivity.class);
                    intent.putExtra("sourceId",sourceItem.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
            });
        }
    }
}

package me.recruit.recruitme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AlexLand on 2016-02-28.
 */
public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>{
    private List<String> candidateList;
    private Context context;
    private View.OnClickListener onClickListener;

    public CandidateAdapter(List<String> candidateList, Context context, View.OnClickListener onClickListener) {
        this.candidateList = candidateList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public CandidateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CandidateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CandidateViewHolder holder, int position) {
        Candidate candidate = JSONParser.parse(candidateList.get(position));
        holder.name.setText(candidate.getName());
        holder.title.setText(candidate.getTitle());

        Picasso.with(context)
                .load(candidate.getPictureURL())
                .placeholder(R.mipmap.image_placeholder)
                .error(R.mipmap.image_placeholder)
                .into(holder.profilePicture);

    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder {
        private ImageView profilePicture;
        private TextView name;
        private TextView title;

        public CandidateViewHolder(View itemView) {
            super(itemView);
            profilePicture = (ImageView) itemView.findViewById(R.id.cardPicture);
            name = (TextView) itemView.findViewById(R.id.cardName);
            title = (TextView) itemView.findViewById(R.id.cardTitle);
            itemView.setOnClickListener(onClickListener);
        }
    }
}

package hci.phasedifference.recollect.viewpackage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.AvailableCardSets;

import java.util.ArrayList;
import java.util.List;

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.CardSetHolder> {

    private List<AvailableCardSets> availableCardSetsSet = new ArrayList<>();

    @NonNull
    @Override
    public CardSetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new CardSetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardSetHolder holder, int position) {
        AvailableCardSets curAvailableCardSets = availableCardSetsSet.get(position);
        holder.tvtitle.setText(curAvailableCardSets.getTitles().get(1));
        holder.tvStarred.setText(curAvailableCardSets.getTitles().get(0));
    }

    @Override
    public int getItemCount() {
        return availableCardSetsSet.size();
    }

    public void setAvailableCardSetsSet(List list) {
        availableCardSetsSet = list;
        notifyDataSetChanged();
    }

    public class CardSetHolder extends RecyclerView.ViewHolder {
        private TextView tvtitle;
        private TextView tvStarred;

        public CardSetHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.title);
            tvStarred = itemView.findViewById(R.id.starredOrNot);
        }
    }

}

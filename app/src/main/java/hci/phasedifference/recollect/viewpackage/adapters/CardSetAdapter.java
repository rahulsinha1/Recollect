package hci.phasedifference.recollect.viewpackage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.AvailableCardSets;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetInterface;

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.CardSetHolder> {

    private AvailableCardSets availableCardSets = new AvailableCardSets();

    @NonNull
    @Override
    public CardSetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new CardSetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardSetHolder holder, int position) {
        CardSetInterface set = availableCardSets.getAvailableCardSets().get(position);
        holder.tvtitle.setText(set.getCardsetTitle());
//        holder.tvComplete.setText(curAvailableCardSets.getTitles().get(0));
    }

    @Override
    public int getItemCount() {
        if (availableCardSets != null) {
            return availableCardSets.getAvailableCardSets().size();
        }
        return 0;
    }

    public void setAvailableCardSets(AvailableCardSets sets) {
        availableCardSets = sets;
        notifyDataSetChanged();
    }

    public class CardSetHolder extends RecyclerView.ViewHolder {
        private TextView tvtitle;
        private TextView tvComplete;

        public CardSetHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.title);
            tvComplete = itemView.findViewById(R.id.description);
        }
    }

}

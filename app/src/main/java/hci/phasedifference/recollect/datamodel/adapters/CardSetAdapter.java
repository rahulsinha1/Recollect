package hci.phasedifference.recollect.datamodel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.Card;

import java.util.ArrayList;
import java.util.List;

public class CardSetAdapter extends RecyclerView.Adapter<CardSetAdapter.CardSetHolder> {

    private List<Card> cardSet = new ArrayList<>();

    @NonNull
    @Override
    public CardSetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new CardSetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardSetHolder holder, int position) {
        Card curCard = cardSet.get(position);
        holder.tvtitle.setText(curCard.getWord());
        holder.tvStarred.setText(curCard.getDefinition());
    }

    @Override
    public int getItemCount() {
        return cardSet.size();
    }

    public void setCardSet(List list) {
        cardSet = list;
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

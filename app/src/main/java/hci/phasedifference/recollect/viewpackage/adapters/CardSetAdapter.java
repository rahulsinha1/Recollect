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
    private CardSetItemOnClickListener clickListener;


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

    public void setClickListener(CardSetItemOnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class CardSetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvtitle;

        public CardSetHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.title);
            View button = itemView.findViewById(R.id.buttonLearnMode);
            button.setOnClickListener(this);
            button = itemView.findViewById(R.id.buttonViewMode);
            button.setOnClickListener(this);
            button = itemView.findViewById(R.id.buttonDelete);
            button.setOnClickListener(this);
            button = itemView.findViewById(R.id.buttonEdit);
            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

}

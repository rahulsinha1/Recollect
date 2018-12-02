package hci.phasedifference.recollect.viewpackage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Card> cards;
    private static View.OnClickListener clickListener;

    public CardStackAdapter(Context context, List<Card> card, View.OnClickListener clickListener) {
        this.inflater = LayoutInflater.from(context);
        CardStackAdapter.clickListener = clickListener;
        this.cards = new ArrayList<Card>();
        for (Card c : card) {
            this.cards.add(new Card(c));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.learn_mode_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.word.setText("Word : " + card.getWord());
        holder.defn.setText("Definition : " + card.getDefinition());
        holder.defn.setVisibility(View.INVISIBLE);
        holder.status.setText(card.getLevel().toString());

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView word, defn, status;
        ImageView image;

        ViewHolder(View view) {
            super(view);
            this.word = view.findViewById(R.id.item_word);
            this.status = view.findViewById(R.id.item_status);
            this.defn = view.findViewById(R.id.item_defn);
            this.image = view.findViewById(R.id.item_image);

            this.image.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            defn.setVisibility(View.VISIBLE);
            CardStackAdapter.clickListener.onClick(v);
        }
    }

}

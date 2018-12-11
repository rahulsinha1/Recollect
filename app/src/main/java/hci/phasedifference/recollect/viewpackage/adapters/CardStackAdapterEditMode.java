package hci.phasedifference.recollect.viewpackage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapterEditMode extends RecyclerView.Adapter<CardStackAdapterEditMode.ViewHolder> {

    private static CardSetItemOnClickListener clickListener;
    private LayoutInflater inflater;
    private List<Card> cards;

    public CardStackAdapterEditMode(Context context, List<Card> card, CardSetItemOnClickListener clickListener) {
        this.inflater = LayoutInflater.from(context);
        CardStackAdapterEditMode.clickListener = clickListener;
        this.cards = new ArrayList<Card>();
        for (Card c : card) {
            this.cards.add(new Card(c));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.edit_mode_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.word.setText(card.getWord());
        holder.defn.setText(card.getDefinition());
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

    public Card getCardAt(int pos) {
        return cards.get(pos);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView word, defn;
        ImageView image;
        Button deleteBUtton;

        ViewHolder(View view) {
            super(view);
            this.word = view.findViewById(R.id.item_word);
            this.defn = view.findViewById(R.id.item_defn);
            this.image = view.findViewById(R.id.item_image);
            this.deleteBUtton = view.findViewById(R.id.buttonDeleteCard);

            this.deleteBUtton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }


    }

}

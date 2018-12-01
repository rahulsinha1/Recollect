package hci.phasedifference.recollect.viewpackage.screens;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.ActiveDataHandler;
import hci.phasedifference.recollect.datamodel.AvailableCardSets;
import hci.phasedifference.recollect.datamodel.CardViewModel;
import hci.phasedifference.recollect.viewpackage.adapters.CardSetAdapter;
import hci.phasedifference.recollect.viewpackage.adapters.CardSetItemOnClickListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvailableCardsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvailableCardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableCardsFragment extends Fragment implements CardSetItemOnClickListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardViewModel cardViewModel;
    private AvailableCardSets availableCardSets;
    private ActiveDataHandler activeDataHandler;

    private OnFragmentInteractionListener mListener;

    public AvailableCardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvailableCardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvailableCardsFragment newInstance(String param1, String param2) {
        AvailableCardsFragment fragment = new AvailableCardsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activeDataHandler = ActiveDataHandler.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cardSetview = inflater.inflate(R.layout.available_cardlist_fragment, container, false);

        RecyclerView recyclerView = cardSetview.findViewById(R.id.cardSetList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        final CardSetAdapter adapter = new CardSetAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        Button button = cardSetview.findViewById(R.id.addCardSet);
        button.setOnClickListener(this);

        cardViewModel = ViewModelProviders.of(getActivity()).get(CardViewModel.class);
        cardViewModel.getAllCards().observe(this, new Observer<AvailableCardSets>() {

            @Override
            public void onChanged(@Nullable AvailableCardSets availableCardSets) {
                adapter.setAvailableCardSets(availableCardSets);
                AvailableCardsFragment.this.availableCardSets = availableCardSets;
            }
        });

        return cardSetview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view, int position) {
        activeDataHandler.activateCardSet(
                availableCardSets.getLocalsets().get(position));
        //todo
        switch (view.getId()) {
            case R.id.buttonLearnMode:
                Navigation.findNavController(view).navigate(R.id.actionGotoLearnMode);
                break;
            case R.id.buttonViewMode:
                Navigation.findNavController(view).navigate(R.id.actionGotoViewMode);
                break;
            case R.id.addCardSet:
                Navigation.findNavController(view).navigate(R.id.actionGotoAddCard);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addCardSet:
                Navigation.findNavController(view).navigate(R.id.actionGotoAddCard);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

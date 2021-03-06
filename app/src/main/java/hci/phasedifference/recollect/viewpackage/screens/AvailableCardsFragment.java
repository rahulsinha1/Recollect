package hci.phasedifference.recollect.viewpackage.screens;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.ActiveDataHandler;
import hci.phasedifference.recollect.datamodel.AvailableCardSets;
import hci.phasedifference.recollect.datamodel.CardViewModel;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;
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
public class AvailableCardsFragment extends Fragment implements CardSetItemOnClickListener, View.OnClickListener, ConfirmDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String DELETE_DIALOG_MESSAGE = "Deleting cannot be reversed, Do you want to continue?";

    private final int REQUEST_DELETE_CARD_SET = 1;
    private final int REQUEST_RELEARN_CARD_SET = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardViewModel cardViewModel;
    private AvailableCardSets availableCardSets;
    private ActiveDataHandler activeDataHandler;
    private DialogHandler confirmationDialog;
    private CardSetImpl cardSetTobeDeleted;
    private OnFragmentInteractionListener mListener;
    private NavController navigationController;

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

        confirmationDialog = new DialogHandler(getContext(), this);
        ActiveDataHandler.getInstance().setViewModel(cardViewModel);


        Activity a = getActivity();
        ((MainActivity) a).setActionBarTitle("Recollect");

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
        //todo
        switch (view.getId()) {
            case R.id.buttonLearnMode:
                activeDataHandler.activateCardSet(
                        availableCardSets.getLocalsets().get(position));
                if (activeDataHandler.getDisplayStack().size() == 0) {
                    confirmationDialog.show("Do you want to relearn?", "Set Already Memorized", REQUEST_RELEARN_CARD_SET);
                } else {
                    navigationController = Navigation.findNavController(view);
                    navigationController.navigate(R.id.actionGotoLearnMode);
                }
                break;
            case R.id.buttonViewMode:
                // showToastMessage("Not Part of this demo");
                activeDataHandler.activateCardSet(
                        availableCardSets.getLocalsets().get(position));
                Navigation.findNavController(view).navigate(R.id.actionGotoViewMode);
                break;
            case R.id.addCardSet:
                Navigation.findNavController(view).navigate(R.id.actionGotoAddCard);
                break;
            case R.id.buttonDelete:
                cardSetTobeDeleted = availableCardSets.getLocalsets().get(position);
                //todo : handle confirm dialog here
                confirmationDialog.show(DELETE_DIALOG_MESSAGE, "Delete Card Set", REQUEST_DELETE_CARD_SET);
                break;
            case R.id.buttonEdit:
                //todo handle edit mode here
                showToastMessage("Not part of this demo");
                break;

        }
    }

    private void showToastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addCardSet:
                Navigation.findNavController(view).navigate(R.id.actionGotoAddCard);
                break;
        }
    }

    @Override
    public void confirmDialogAction(int reqID, boolean confirmation) {
        if (!confirmation) {
            return;
        }
        switch (reqID) {
            case REQUEST_DELETE_CARD_SET:
                if (cardSetTobeDeleted != null) {
                    ActiveDataHandler.getInstance().removeCardSet(cardSetTobeDeleted);
                    cardSetTobeDeleted = null;
                }
                break;
            case REQUEST_RELEARN_CARD_SET:
                activeDataHandler.relearnCardSet();
                navigationController.navigate(R.id.actionGotoLearnMode);

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

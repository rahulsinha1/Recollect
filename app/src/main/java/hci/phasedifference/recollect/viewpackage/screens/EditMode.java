package hci.phasedifference.recollect.viewpackage.screens;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.ActiveDataHandler;
import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;
import hci.phasedifference.recollect.viewpackage.adapters.CardSetItemOnClickListener;
import hci.phasedifference.recollect.viewpackage.adapters.CardStackAdapterEditMode;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditMode.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditMode#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMode extends Fragment implements View.OnClickListener, ConfirmDialogListener, CardSetItemOnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int CONGRATULATIONS_SCREEN_CONFIRMATION = 3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayoutManager manager;
    private CardStackAdapterEditMode adapter;
    private RecyclerView cardStackView;
    private RecyclerView.SmoothScroller smoothScroller;
    private LearnMode.OnFragmentInteractionListener mListener;

    public EditMode() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewMode.
     */
    // TODO: Rename and change types and number of parameters
    public static EditMode newInstance(String param1, String param2) {
        EditMode fragment = new EditMode();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_mode, container, false);


        Activity a = getActivity();
        ((MainActivity) a).setActionBarTitle(ActiveDataHandler.getInstance().getCurrentTitle());

        setupCardStackView(view);
        setupButton(view);

        smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        return view;
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
        if (context instanceof LearnMode.OnFragmentInteractionListener) {
            mListener = (LearnMode.OnFragmentInteractionListener) context;
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


    private void setupCardStackView(View v) {
        initialize(v);
    }

    private void setupButton(View v) {
        View add = v.findViewById(R.id.button_add_moreCards);

        add.setOnClickListener(this);
    }

    private void initialize(View v) {
        adapter = new CardStackAdapterEditMode(getContext(),
                ActiveDataHandler.getInstance().getAllCardsList(), this);
        cardStackView = v.findViewById(R.id.card_stack_view);

        manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        cardStackView.setLayoutManager(manager);
        cardStackView.setHasFixedSize(true);
        cardStackView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_moreCards:
                //handle adding more cards here
                Navigation.findNavController(v).navigate(R.id.editModeToAddCardMode);
                break;
        }
    }

    @Override
    public void onClick(View view, int position) {
        //todo
        switch (view.getId()) {
            case R.id.buttonDeleteCard:
                //handle card deletion here
                Card cardToRemove = adapter.getCardAt(position);
                ActiveDataHandler.getInstance().removeCard(cardToRemove);
                adapter.removeCard(cardToRemove);
                break;
        }
    }

    @Override
    public void confirmDialogAction(int reqID, boolean confirmation) {
        if (reqID == CONGRATULATIONS_SCREEN_CONFIRMATION) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onPause() {
        ActiveDataHandler.getInstance().commitChanges();
        super.onPause();
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

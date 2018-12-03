package hci.phasedifference.recollect.viewpackage.screens;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import com.yuyakaido.android.cardstackview.*;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.ActiveDataHandler;
import hci.phasedifference.recollect.datamodel.datarepresentaion.Card;
import hci.phasedifference.recollect.viewpackage.adapters.CardStackAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LearnMode.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LearnMode#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnMode extends Fragment implements CardStackListener, View.OnClickListener, ConfirmDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int CONGRATULATIONS_SCREEN_CONFIRMATION = 3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private CardStackView cardStackView;
    private LinearLayout layout_definition;


    private OnFragmentInteractionListener mListener;

    public LearnMode() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnMode.
     */
    // TODO: Rename and change types and number of parameters
    public static LearnMode newInstance(String param1, String param2) {
        LearnMode fragment = new LearnMode();
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
        View view = inflater.inflate(R.layout.fragment_learn_mode, container, false);

        layout_definition = view.findViewById(R.id.layout_definition);
        layout_definition.setVisibility(View.INVISIBLE);

        setupCardStackView(view);
        setupButton(view);
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


    private void setupCardStackView(View v) {
        initialize(v);
    }

    private void setupButton(View v) {
        View no = v.findViewById(R.id.button_no);
        View yes = v.findViewById(R.id.button_yes);

        no.setOnClickListener(this);
        yes.setOnClickListener(this);
    }

    private void initialize(View v) {
        manager = new CardStackLayoutManager(getContext(), this);
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(0.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(false);
        manager.setCanScrollVertical(false);
        adapter = new CardStackAdapter(getContext(),
                ActiveDataHandler.getInstance().getDisplayStack(), this);
        cardStackView = v.findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
    }

    @Override
    public void onCardSwiped(Direction direction) {
        int position = manager.getTopPosition();
        Log.d("CardStackView", "onCardSwiped: p = " + manager.getTopPosition() + ", d = " + direction);
        if (direction == Direction.Left) {
            ActiveDataHandler.getInstance().setUserGuess(adapter.getCards().get(manager.getTopPosition() - 1), false);
        } else {
            ActiveDataHandler.getInstance().setUserGuess(adapter.getCards().get(manager.getTopPosition() - 1), true);
        }
        if (position == adapter.getItemCount()) {
            List<Card> stack = ActiveDataHandler.getInstance().getDisplayStack();
            if (stack.size() > 0) {
                adapter.setCards(stack);
                adapter.notifyDataSetChanged();
            } else {
                new DialogHandler(getContext(), this)
                        .showOkDialog("You have Successfully memorised the Set", "Congratulations!!!",
                                CONGRATULATIONS_SCREEN_CONFIRMATION);
            }
        }
        layout_definition.setVisibility(View.INVISIBLE);
        manager.setCanScrollHorizontal(false);

    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_image:
                layout_definition.setVisibility(View.VISIBLE);
                manager.setCanScrollHorizontal(true);
                break;
            case R.id.button_yes:
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Right)
                        .setDuration(50)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
                break;
            case R.id.button_no:
                setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Left)
                        .setDuration(50)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
                break;
        }
    }

    @Override
    public void confirmDialogAction(int reqID, boolean confirmation) {
        if (reqID == CONGRATULATIONS_SCREEN_CONFIRMATION) {
            getActivity().onBackPressed();
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

package hci.phasedifference.recollect.viewpackage.screens;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import hci.phasedifference.recollect.R;
import hci.phasedifference.recollect.datamodel.ActiveDataHandler;
import hci.phasedifference.recollect.datamodel.datarepresentaion.CardSetImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddCardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCardFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText etTitle;
    private EditText etWord;
    private EditText etDefn;
    private Button buttonAddMore;
    private Button buttonOK;
    private Button buttonSave;
    private Button buttonCancel;
    private boolean nameEntered;

    private String title;
    private Map<String, String> word2def;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCardFragment newInstance(String param1, String param2) {
        AddCardFragment fragment = new AddCardFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);
        TextView tv = view.findViewById(R.id.tvaddcard);
        tv.setText("addSet mode avinash");

        Integer id = this.getId();
        trace("fragment id is :" + Integer.toHexString(id));

        etTitle = view.findViewById(R.id.editTextCardSetName);
        etWord = view.findViewById(R.id.editTextWord);
        etDefn = view.findViewById(R.id.editTextDefinition);

        buttonOK = view.findViewById(R.id.buttonOK);
        buttonAddMore = view.findViewById(R.id.buttonAddMore);
        buttonSave = view.findViewById(R.id.buttonSaveWords);
        buttonCancel = view.findViewById(R.id.buttonCancelCardAddition);


        buttonSave.setOnClickListener(this);
        buttonAddMore.setOnClickListener(this);
        buttonOK.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonCancel.setOnLongClickListener(this);

        word2def = new HashMap<>();

        nameEntered = false;
        handleEnablingViews();
        title = "";
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

    private void showToastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOK:
                if (getText(etTitle).isEmpty()) {
                    showToastMessage("Title Cannot be Empty");
                    etTitle.setText(title);

                } else {
                    title = etTitle.getText().toString();
                    nameEntered = true;
                    handleEnablingViews();
                    //todo : handle responsive disclosure here
                }
                break;
            case R.id.buttonAddMore: {
                if (getText(etWord).isEmpty()) {
                    showToastMessage("Word cannot be empty");
                } else if (getText(etDefn).isEmpty()) {
                    showToastMessage("Definition cannot be empty");
                } else {
                    if (!word2def.containsKey(getText(etWord))) {
                        word2def.put(getText(etWord), getText(etDefn));
                        etWord.setText("");
                        etDefn.setText("");
                    }
                }
            }
            break;
            case R.id.buttonSaveWords:
                //todo : handle data base addition here.
                if (word2def.size() != 0) {
                    CardSetImpl c = new CardSetImpl(title);
                    for (Map.Entry<String, String> e : word2def.entrySet()) {
                        c.addCard(e.getKey(), e.getValue());
                    }
                    ActiveDataHandler.getInstance().addCardSet(c);
                }
                break;
            case R.id.buttonCancelCardAddition:
                showToastMessage("LongPress to Cancel addition of cardset");
                //todo showing dialog is better, please show dialog here.
                break;
            default:
                break;
        }
    }

    private String getText(EditText et) {
        return et.getText().toString().trim();
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCancelCardAddition: {
                //todo : handle going back to the card set screen here
                return true;
            }
        }
        return false;
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

    private void handleEnablingViews() {
        if (nameEntered) {
            setVisibility(true);
        } else {
            setVisibility(false);
        }
    }

    private void setVisibility(boolean visibility) {
        String text = (visibility) ? "" : " ";
        //todo : space to hide hint
        etWord.setText(text);
        etDefn.setText(text);

        etWord.setEnabled(visibility);
        etDefn.setEnabled(visibility);
        buttonAddMore.setEnabled(visibility);
        buttonSave.setEnabled(visibility);
    }

    public void handleBackButton() {
        trace("handlebackbutton called");
    }


    private void trace(String str) {
        Log.d("ABG", str);
    }

}

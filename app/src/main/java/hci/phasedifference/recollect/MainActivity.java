package hci.phasedifference.recollect;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import hci.phasedifference.recollect.datamodel.Card;
import hci.phasedifference.recollect.datamodel.CardViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements GlanceMode.OnFragmentInteractionListener,
        LearnMode.OnFragmentInteractionListener,
        ModeSelectionFragment.OnFragmentInteractionListener {


    private CardViewModel cardViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        cardViewModel.getAllCards().observe(this, new Observer<List<Card>>() {

            @Override
            public void onChanged(@Nullable List<Card> cards) {
                //update data here
                Toast.makeText(MainActivity.this, "ONCHANGED", Toast.LENGTH_LONG);
            }
        });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

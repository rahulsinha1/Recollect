package hci.phasedifference.recollect.viewpackage.screens;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import hci.phasedifference.recollect.R;

public class AddCardSetActivity extends AppCompatActivity
        implements AddCardFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_set);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

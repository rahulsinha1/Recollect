package hci.phasedifference.recollect.viewpackage.screens;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import hci.phasedifference.recollect.R;

public class MainActivity extends AppCompatActivity
        implements GlanceMode.OnFragmentInteractionListener,
        AvailableCardsFragment.OnFragmentInteractionListener,
        ModeSelectionFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

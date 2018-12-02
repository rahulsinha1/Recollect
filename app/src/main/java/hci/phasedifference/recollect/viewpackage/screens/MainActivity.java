package hci.phasedifference.recollect.viewpackage.screens;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import hci.phasedifference.recollect.R;

public class MainActivity extends AppCompatActivity
        implements AvailableCardsFragment.OnFragmentInteractionListener,
        AddCardFragment.OnFragmentInteractionListener,
        LearnMode.OnFragmentInteractionListener,
        ViewMode.OnFragmentInteractionListener {


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        trace("onFragmentInteraction called " + uri);
    }

    private void trace(String str) {
        Log.d("ABG", str);
    }

}

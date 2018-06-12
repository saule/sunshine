package plant.kz.aygolek.sunshine;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            //TODO:
            Fragment newDetailFragment = new DetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            //TODO:
            ft.add(R.id.container, newDetailFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO: check menu detail
        getMenuInflater().inflate(R.menu.detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        shareActionProvider = (ShareActionProvider) item.getActionProvider();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(DetailActivity.this,SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        if(id == R.id.action_map){
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            String weatherLocationId =
                    settings.getString(getString(R.string.pref_location_key),
                            getString(R.string.pref_location_default));

            String geoLocation = "geo:0,0?q=94043"; //+weatherLocationId;
            showMap(Uri.parse(geoLocation));
            return true;
        }

        if(id == R.id.action_share){
            TextView textView = findViewById(R.id.detail_activity_text);
            String forecastInfo=textView.getText().toString();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setData(Uri.parse(forecastInfo));

            setShareIntent(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(DetailActivity.class.getSimpleName(),"Couldn't call location ");
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            TextView textView = rootView.findViewById(R.id.detail_activity_text);

            Intent intent = getActivity().getIntent();

            String detailForecast = intent.getStringExtra("forecastDay");

            textView.setText(detailForecast);
            return rootView;
        }
    }

}

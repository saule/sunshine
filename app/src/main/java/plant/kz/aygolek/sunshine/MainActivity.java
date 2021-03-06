package plant.kz.aygolek.sunshine;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(String.valueOf(R.string.pref_location_key), String.valueOf(R.string.pref_location_default));
        editor.commit();
        */
        if (savedInstanceState == null) {
            Fragment newForecastFragment = new ForecastFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.add(R.id.container, newForecastFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(id == R.id.action_settings){
            Intent settingsIntent = new Intent(MainActivity.this,SettingsActivity.class);
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

        return super.onOptionsItemSelected(item);
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(MainActivity.class.getSimpleName(),"Couldn't call location ");
        }
    }
}

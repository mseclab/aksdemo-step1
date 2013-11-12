package com.mseclab.devfest.androidkeystoredemostep1;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        private Button mGenChiaviButton;
        private TextView mDebugText;

        private static final String TAG = "AndroidKeyStoreDemo";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mGenChiaviButton = (Button) rootView.findViewById(R.id.generate_button);
            mGenChiaviButton.setOnClickListener(this);
            mDebugText = (TextView) rootView.findViewById(R.id.debugText);

            return rootView;
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.generate_button:
                    debug("Cliccato Genera chivi");
                    generaChiavi();
                    break;
            }
        }

        private void generaChiavi() {
            AsyncTask<Void, Void, Void> generaTask = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    return null;
                }
            }.execute();
        }

        private void debug(String message){
            mDebugText.append(message + "\n");
            Log.v(TAG, message);
        }
    }

}

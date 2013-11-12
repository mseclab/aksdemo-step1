package com.mseclab.devfest.androidkeystoredemostep1;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.security.KeyPairGeneratorSpec;
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

	private final static String ALIAS = "DEVKEY1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
	public static class PlaceholderFragment extends Fragment implements
			View.OnClickListener {

		private Button mGenChiaviButton;
		private TextView mDebugText;
		ProgressDialog progressdialog;

		private static final String TAG = "AndroidKeyStoreDemo";

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			mGenChiaviButton = (Button) rootView
					.findViewById(R.id.generate_button);
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

			Context cx = getActivity();
			// Generate a key pair inside the AndroidKeyStore
			Calendar notBefore = Calendar.getInstance();
			Calendar notAfter = Calendar.getInstance();
			notAfter.add(1, Calendar.YEAR);

			android.security.KeyPairGeneratorSpec.Builder builder = new KeyPairGeneratorSpec.Builder(
					cx);
			builder.setAlias(ALIAS);
			String infocert = String.format("CN=%s, OU=%s", ALIAS,
					cx.getPackageName());
			builder.setSubject(new X500Principal(infocert));
			builder.setSerialNumber(BigInteger.ONE);
			builder.setStartDate(notBefore.getTime());
			builder.setEndDate(notAfter.getTime());
			KeyPairGeneratorSpec spec = builder.build();

			KeyPairGenerator kpGenerator;
			KeyPair kp = null;
			try {
				//ShowGenerating();
				progressdialog = ProgressDialog.show(this.getActivity(), "Progress Dialog","Generating keys....",true);
				kpGenerator = KeyPairGenerator.getInstance("RSA",
						"AndroidKeyStore");
				kpGenerator.initialize(spec);
				kp = kpGenerator.generateKeyPair();
				debug("Generated key pair:" + kp.toString());
				progressdialog.dismiss();

			} catch (NoSuchAlgorithmException e2) {
				debug(e2.toString());
			} catch (NoSuchProviderException e2) {
				debug(e2.toString());
			} catch (InvalidAlgorithmParameterException e) {
				debug(e.toString());
			}

			

		}

		private void ShowGenerating() {
			progressdialog = new ProgressDialog(this.getActivity());
			progressdialog.setTitle("Generating keys....");
			progressdialog.setIndeterminate(false);
			progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressdialog.show();
		}

		public void debug(String message) {
			mDebugText.append(message + "\n");
			Log.v(TAG, message);
		}
	}

}

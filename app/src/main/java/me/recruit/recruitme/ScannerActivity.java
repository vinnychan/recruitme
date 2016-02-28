package me.recruit.recruitme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler  {
	private ZXingScannerView mScannerView;
	private String TAG = "SCANNER_ACTIVITY";

	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);
		mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
		setContentView(mScannerView);                // Set the scanner view as the content view
	}

	@Override
	public void onResume() {
		super.onResume();
		mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
		mScannerView.startCamera();          // Start camera on resume
	}

	@Override
	public void onPause() {
		super.onPause();
		mScannerView.stopCamera();           // Stop camera on pause
	}

	@Override
	public void handleResult(final Result rawResult) {
		// Do something with the result here
		Log.v(TAG, rawResult.getText()); // Prints scan results
		Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

		if (!isJSONValid(rawResult.toString())) {
			Snackbar.make(mScannerView, "QR Scan failed, please try again", Snackbar.LENGTH_LONG).show();
		} else {

			Snackbar.make(mScannerView, "QR Code Scanned", Snackbar.LENGTH_INDEFINITE)
					.setAction("View Profile", new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(ScannerActivity.this, CandidateView.class);
							intent.putExtra("RESULT_TEXT", rawResult.getText());
							startActivity(intent);
						}
					}).show();
		}

		// If you would like to resume scanning, call this method below:
		mScannerView.resumeCameraPreview(this);
	}

	public boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}


}

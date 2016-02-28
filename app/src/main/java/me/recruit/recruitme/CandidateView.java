package me.recruit.recruitme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CandidateView extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_candidate_view);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		String result = getIntent().getStringExtra("RESULT_TEXT");

		Candidate candidate = JSONParser.parse(result);

		TextView name = (TextView) findViewById(R.id.name);
		TextView title = (TextView) findViewById(R.id.title);
		TextView location = (TextView) findViewById(R.id.location);
		TextView linkedin = (TextView) findViewById(R.id.linkedin);
		TextView resume = (TextView) findViewById(R.id.resume);

		name.setText(candidate.getName());
		title.setText(candidate.getTitle());
		location.setText(candidate.getLocation());
		linkedin.setText(candidate.getLinkedIn());
		resume.setText(candidate.getResume());


		Log.d("CANDIDATE_TEST", candidate.getLinkedIn());

		Log.d("CANDIDATE_TEST", candidate.getPortfolioURLs().toString());


	}

}

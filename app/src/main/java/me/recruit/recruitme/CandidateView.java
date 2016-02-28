package me.recruit.recruitme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CandidateView extends AppCompatActivity {
    private Candidate candidate = null;

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

        candidate = JSONParser.parse(result);

		TextView name = (TextView) findViewById(R.id.cardName);
		TextView title = (TextView) findViewById(R.id.cardTitle);
		TextView location = (TextView) findViewById(R.id.location);
		TextView email = (TextView) findViewById(R.id.email);
		TextView linkedin = (TextView) findViewById(R.id.linkedin);
		TextView resume = (TextView) findViewById(R.id.resume);

		String candidateName = candidate.getFirstName() + "  " + candidate.getLastName();
		name.setText(candidateName);
		title.setText(candidate.getTitle());
		email.setText(candidate.getEmail());
		location.setText(candidate.getLocation());
		linkedin.setText(candidate.getLinkedIn());
		resume.setText(candidate.getResume());

        ImageView imageView = (ImageView) findViewById(R.id.profilepicture);

        Picasso.with(getApplicationContext())
                .load(candidate.getPictureURL())
                .placeholder(R.mipmap.image_placeholder)
                .error(R.mipmap.image_placeholder)
                .into(imageView);

		Log.d("CANDIDATE_TEST", candidate.getLinkedIn());

		Log.d("CANDIDATE_TEST", candidate.getPortfolioURLs().toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.candidateview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.action_discard) {
            finish();
			return true;
		}
        else if (id == R.id.action_save) {
            EditText comments = (EditText) findViewById(R.id.comments);
            candidate.setComments(comments.getText().toString());
            String candidateJSON = candidate.toBaseString();
            DatabaseUtil dbUtil = new DatabaseUtil(getApplicationContext());
            dbUtil.addCandidate(dbUtil.getWritableDatabase(), candidateJSON);
            finish();
        }

		return super.onOptionsItemSelected(item);
	}

}

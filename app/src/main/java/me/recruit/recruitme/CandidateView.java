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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class CandidateView extends AppCompatActivity {
    private Candidate candidate = null;

    private String candidateId = null;
	private String candidateString;

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

        candidateId = getIntent().getStringExtra("ID");



		candidateString = getIntent().getStringExtra("RESULT_TEXT");

        candidate = JSONParser.parse(candidateString);

		TextView name = (TextView) findViewById(R.id.cardName);
		TextView title = (TextView) findViewById(R.id.cardTitle);
		TextView location = (TextView) findViewById(R.id.location);
		TextView email = (TextView) findViewById(R.id.email);
		TextView linkedin = (TextView) findViewById(R.id.linkedin);
		TextView resume = (TextView) findViewById(R.id.resume);
		TextView portfolio = (TextView) findViewById(R.id.portfolio);
		TextView url1 = (TextView) findViewById(R.id.url1);
		TextView url2 = (TextView) findViewById(R.id.url2);
		TextView url3 = (TextView) findViewById(R.id.url3);
        EditText comments = (EditText) findViewById(R.id.comments);

		String candidateName = candidate.getFirstName() + "  " + candidate.getLastName();
		name.setText(candidateName);
		title.setText(candidate.getTitle());
		email.setText(candidate.getEmail());
		location.setText("Location: " + candidate.getLocation());
		linkedin.setText(candidate.getLinkedIn());
		resume.setText("Resume: " + candidate.getResume());
		portfolio.setText("Portfolio:");

		url1.setText(getOrElse(candidate.getPortfolioURLs(), 0));
		url2.setText(getOrElse(candidate.getPortfolioURLs(), 1));
		url3.setText(getOrElse(candidate.getPortfolioURLs(), 2));

        ImageView imageView = (ImageView) findViewById(R.id.profilepicture);

        Picasso.with(getApplicationContext())
                .load(candidate.getPictureURL())
                .placeholder(R.mipmap.image_placeholder)
                .error(R.mipmap.image_placeholder)
                .into(imageView);

        String commentsString = candidate.getComments();
        if (!commentsString.equals("")) {
            comments.setText(commentsString);
        }

		Log.d("COMMENTS", commentsString);
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
            if (candidateId != null) {
                dbUtil.updateCandidate(dbUtil.getWritableDatabase(), candidateId, candidateJSON);
            }
            else {
                dbUtil.addCandidate(dbUtil.getWritableDatabase(), candidateJSON);
            }

			// Post to remote DB
			try {
				JSONObject resultJson = new JSONObject(candidateJSON);
				HTTPUrlConnection.sendJson(resultJson);
			} catch (JSONException e) {
				e.printStackTrace();
			}

            finish();
        }

		return super.onOptionsItemSelected(item);
	}

	private String getOrElse(List<String> list, int i) {
		try {
			return list.get(i);
		} catch (Exception e) {
			return "";
		}
	}

}

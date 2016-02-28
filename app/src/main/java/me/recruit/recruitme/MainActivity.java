package me.recruit.recruitme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String API_URL = "http://justchooseme.azurewebsites.net/";
    private DatabaseUtil dbUtil;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = preferences.getString(AuthUtil.TOKEN_PREFERENCE, "");
        if (token.equals("")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            token = preferences.getString(AuthUtil.TOKEN_PREFERENCE, "");
        }
        Log.d("MainActivity", "Token: " + token);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.candidateRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        dbUtil = new DatabaseUtil(getApplicationContext());
        List[] candidateLists = dbUtil.getCandidatesList(dbUtil.getReadableDatabase());
        final List<String> candidateList = candidateLists[0];
        final List<String> candidateIds = candidateLists[1];

        CardCallback cardCallback = new CardCallback() {
            @Override
            public void onClick(View view, int position) {
                String candidateJSON = candidateList.get(position);
                String candidateId = candidateIds.get(position);
                Intent intent = new Intent(MainActivity.this, CandidateView.class);
                intent.putExtra("RESULT_TEXT", candidateJSON);
                intent.putExtra("ID", candidateId);
                startActivity(intent);
            }
        };

        CandidateAdapter candidateAdapter = new CandidateAdapter(candidateList, getApplicationContext(), cardCallback);
        recyclerView.setAdapter(candidateAdapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_api) {
            //TODO: remove later
            String testURL = MainActivity.API_URL + "test";
            new CallAPI().execute(testURL);
            return true;
        }
        else if (id == R.id.action_deletedb) {
            DatabaseUtil dbUtil = new DatabaseUtil(getApplicationContext());
            dbUtil.deleteDb(dbUtil.getWritableDatabase(), getApplicationContext());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List[] candidateLists = dbUtil.getCandidatesList(dbUtil.getReadableDatabase());
        final List<String> candidateList = candidateLists[0];
        final List<String> candidateIds = candidateLists[1];

        CardCallback cardCallback = new CardCallback() {
            @Override
            public void onClick(View view, int position) {
                String candidateJSON = candidateList.get(position);
                String candidateId = candidateIds.get(position);
                Intent intent = new Intent(MainActivity.this, CandidateView.class);
                intent.putExtra("RESULT_TEXT", candidateJSON);
                intent.putExtra("ID", candidateId);
                startActivity(intent);
            }
        };

        CandidateAdapter candidateAdapter = new CandidateAdapter(candidateList, getApplicationContext(), cardCallback);
        recyclerView.setAdapter(candidateAdapter);
    }
}

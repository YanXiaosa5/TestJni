package com.baitu.jnitest.snaphelp.use;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;


import com.baitu.jnitest.R;
import com.baitu.jnitest.snaphelp.utils.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    public static final String ORIENTATION = "orientation";

    private RecyclerView mRecyclerView;
    private boolean mHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }

        setupAdapter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ORIENTATION, mHorizontal);
    }

    private void setupAdapter() {
        List<App> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter();
        if (mHorizontal) {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_HORIZONTAL, "Snap center", apps));
            snapAdapter.addSnap(new Snap(Gravity.START, "Snap start", apps));
            snapAdapter.addSnap(new Snap(Gravity.END, "Snap end", apps));
            snapAdapter.addSnap(new Snap(Gravity.CENTER, "GravityPager snap", apps));
            mRecyclerView.setAdapter(snapAdapter);
        } else {
            Adapter adapter = new Adapter(false, false, apps);
            mRecyclerView.setAdapter(adapter);
            new GravitySnapHelper(Gravity.TOP, false, new GravitySnapHelper.SnapListener() {
                @Override
                public void onSnap(int position) {
                    Log.d("Snapped", position + "");
                }
            }).attachToRecyclerView(mRecyclerView);
        }
    }

    private List<App> getApps() {
        List<App> apps = new ArrayList<>();
        apps.add(new App("Google+", R.mipmap.ic_google_48dp, 4.6f));
        apps.add(new App("Gmail", R.mipmap.ic_gmail_48dp, 4.8f));
        apps.add(new App("Inbox", R.mipmap.ic_inbox_48dp, 4.5f));
        apps.add(new App("Google Keep", R.mipmap.ic_keep_48dp, 4.2f));
        apps.add(new App("Google Drive", R.mipmap.ic_drive_48dp, 4.6f));
        apps.add(new App("Hangouts", R.mipmap.ic_hangouts_48dp, 3.9f));
        apps.add(new App("Google Photos", R.mipmap.ic_photos_48dp, 4.6f));
        apps.add(new App("Messenger", R.mipmap.ic_messenger_48dp, 4.2f));
        apps.add(new App("Sheets", R.mipmap.ic_sheets_48dp, 4.2f));
        apps.add(new App("Slides", R.mipmap.ic_slides_48dp, 4.2f));
        apps.add(new App("Docs", R.mipmap.ic_docs_48dp, 4.2f));
        apps.add(new App("Google+", R.mipmap.ic_google_48dp, 4.6f));
        apps.add(new App("Gmail", R.mipmap.ic_gmail_48dp, 4.8f));
        apps.add(new App("Inbox", R.mipmap.ic_inbox_48dp, 4.5f));
        apps.add(new App("Google Keep", R.mipmap.ic_keep_48dp, 4.2f));
        apps.add(new App("Google Drive", R.mipmap.ic_drive_48dp, 4.6f));
        apps.add(new App("Hangouts", R.mipmap.ic_hangouts_48dp, 3.9f));
        apps.add(new App("Google Photos", R.mipmap.ic_photos_48dp, 4.6f));
        apps.add(new App("Messenger", R.mipmap.ic_messenger_48dp, 4.2f));
        apps.add(new App("Sheets", R.mipmap.ic_sheets_48dp, 4.2f));
        apps.add(new App("Slides", R.mipmap.ic_slides_48dp, 4.2f));
        apps.add(new App("Docs", R.mipmap.ic_docs_48dp, 4.2f));
        return apps;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.layoutType) {
            mHorizontal = !mHorizontal;
            setupAdapter();
            item.setTitle(mHorizontal ? "Vertical" : "Horizontal");
        } else if (item.getItemId() == R.id.grid) {
            startActivity(new Intent(this, GridActivity.class));
        }
        return false;
    }
}

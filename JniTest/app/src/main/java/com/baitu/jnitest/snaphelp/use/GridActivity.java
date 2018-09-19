package com.baitu.jnitest.snaphelp.use;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.baitu.jnitest.R;
import com.baitu.jnitest.snaphelp.utils.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;


public class GridActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        mRecyclerView = findViewById(R.id.recyclerView);

        Adapter adapter = new Adapter(false, false, getApps());

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, true));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        new GravitySnapHelper(Gravity.TOP).attachToRecyclerView(mRecyclerView);
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
}

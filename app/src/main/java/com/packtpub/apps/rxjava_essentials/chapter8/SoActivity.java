package com.packtpub.apps.rxjava_essentials.chapter8;

import com.packtpub.apps.rxjava_essentials.App;
import com.packtpub.apps.rxjava_essentials.R;
import com.packtpub.apps.rxjava_essentials.apps.AppInfo;
import com.packtpub.apps.rxjava_essentials.chapter8.api.stackexchange.SeApiManager;
import com.packtpub.apps.rxjava_essentials.chapter8.api.stackexchange.models.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;

public class SoActivity extends ActionBarActivity implements SoAdapter.ViewHolder.OpenProfileListener {

    @InjectView(R.id.so_recyclerview)
    RecyclerView mRecyclerView;

    @InjectView(R.id.so_swipe)
    SwipeRefreshLayout mSwipe;

    private SoAdapter mAdapter;

    private SeApiManager mSeApiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so);
        ButterKnife.inject(this);

        mAdapter = new SoAdapter(new ArrayList<User>());
        mAdapter.setOpenProfileListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSeApiManager = new SeApiManager();

//        mSwipe.setOnRefreshListener(this::refreshList);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        refreshList();
    }

    private void refreshList() {
        showRefresh(true);
//        mSeApiManager.getMostPopularSOusers(10)
//                .subscribe(users -> {
//                    showRefresh(false);
//                    mAdapter.updateUsers(users);
//                }, error -> {
//                    App.L.error(error.toString());
//                    showRefresh(false);
//                });

        mSeApiManager.getMostPopularSOusers(10)
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        App.L.error(e.toString());
                        showRefresh(false);
                    }

                    @Override
                    public void onNext(List<User> users) {
                        showRefresh(false);
                        mAdapter.updateUsers(users);
                    }
                });
    }

    private void showRefresh(boolean show) {
        mSwipe.setRefreshing(show);
        int visibility = show ? View.GONE : View.VISIBLE;
        mRecyclerView.setVisibility(visibility);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_so, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void open(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}

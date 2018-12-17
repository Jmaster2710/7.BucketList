package com.joelKroon.bucketlist.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.joelKroon.bucketlist.R;
import com.joelKroon.bucketlist.database.AppDatabase;
import com.joelKroon.bucketlist.database.BucketItem;
import com.joelKroon.bucketlist.database.BucketItemDao;

public class MainActivity extends AppCompatActivity implements BucketItemAdapter.ClickListener {

    private BucketItemAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBucketItem();
            }
        });

        BucketItemViewModelFactory bucketItemViewModelFactory = new BucketItemViewModelFactory( AppDatabase.getInstance( getApplicationContext() ).bucketItemDao() );
        BucketItemViewModel viewModel = ViewModelProviders.of( this, bucketItemViewModelFactory ).get( BucketItemViewModel.class );

        mRecyclerView = findViewById( R.id.recyclerView );
        mAdapter = new BucketItemAdapter( this );
        viewModel.bucketItemList.observe(this, new Observer<PagedList<BucketItem>>() {
            @Override
            public void onChanged(@Nullable PagedList<BucketItem> bucketItems) {
                mAdapter.submitList( bucketItems );
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void OnClick(BucketItem bucketItem) {
        setBucketItemCompleted( bucketItem, bucketItem.getCompleted() != 1);
    }

    private void insertBucketItem() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.setAction(Intent.ACTION_INSERT);
        startActivity(intent);
    }

    private void deleteBucketItem(final BucketItem bucketItem ) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance( getApplicationContext() ).bucketItemDao().delete( bucketItem );
            }
        });
    }

    private void setBucketItemCompleted( final BucketItem bucketItem, boolean completed ) {

        if (completed)
        {
            bucketItem.setCompleted(1);
        } else
        {
            bucketItem.setCompleted(0);
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance( getApplicationContext() ).bucketItemDao().update( bucketItem );
            }
        });
    }
}

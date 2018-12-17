package com.joelKroon.bucketlist.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Objects;

import com.joelKroon.bucketlist.R;
import com.joelKroon.bucketlist.database.AppDatabase;
import com.joelKroon.bucketlist.database.BucketItem;

public class EditActivity extends AppCompatActivity {

    private TextInputEditText mInputName;
    private TextInputEditText mInputDescription;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init the view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mCheckBox = findViewById( R.id.checkbox_completed);
        mInputName = findViewById( R.id.input_name );
        mInputDescription = findViewById( R.id.input_description );

        if (Objects.equals(getIntent().getAction(), Intent.ACTION_INSERT)) {
            fab.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveBucketlistItem();
                }
            });
        }
    }

    private void saveBucketlistItem() {
        String name = mInputName.getText().toString();
        String description = mInputDescription.getText().toString();
        boolean completed = mCheckBox.isChecked();

        final BucketItem bucketItem = new BucketItem( name, description, completed );

        // Insert the homework object in the database
        AsyncTask.execute(new Runnable() {
                @Override
                public void run() { AppDatabase.getInstance( getApplicationContext() ).bucketItemDao().insertAll( bucketItem ); }});

        finish();
    }

}

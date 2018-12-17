package com.joelKroon.bucketlist.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.joelKroon.bucketlist.database.BucketItemDao;

public class BucketItemViewModelFactory implements ViewModelProvider.Factory {

    private BucketItemDao bucketItemDao;

    public BucketItemViewModelFactory(BucketItemDao bucketItemDao) {
        this.bucketItemDao = bucketItemDao;
    }

    @NonNull
    @Override
    public BucketItemViewModel create(@NonNull Class modelClass) {
        return new BucketItemViewModel( bucketItemDao );
    }
}

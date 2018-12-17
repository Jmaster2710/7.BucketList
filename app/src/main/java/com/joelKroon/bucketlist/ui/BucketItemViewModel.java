package com.joelKroon.bucketlist.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.joelKroon.bucketlist.database.BucketItemDao;
import com.joelKroon.bucketlist.database.BucketItem;

public class BucketItemViewModel extends ViewModel {

    public final LiveData<PagedList<BucketItem>> bucketItemList;

    public BucketItemViewModel(BucketItemDao bucketItemDao) {
        bucketItemList = new LivePagedListBuilder<>( bucketItemDao.getAll(), 20 ).build();
    }
}

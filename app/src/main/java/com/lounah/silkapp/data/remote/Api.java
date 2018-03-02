package com.lounah.silkapp.data.remote;


import android.arch.lifecycle.LiveData;

import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Response;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface Api {

    Observable<List<Dialog>> getDialogsByUserID(String... args);

}

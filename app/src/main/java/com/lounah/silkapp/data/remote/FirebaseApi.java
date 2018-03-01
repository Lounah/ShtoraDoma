package com.lounah.silkapp.data.remote;


import com.lounah.silkapp.data.model.Dialog;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FirebaseApi implements Api {

    @Inject
    public FirebaseApi() {

    }

    @Override
    public Single<List<Dialog>> getDialogsByUserID(String... args) {
        return Single.just(Arrays.asList(new Dialog("1","1","1","1")));
    }
}

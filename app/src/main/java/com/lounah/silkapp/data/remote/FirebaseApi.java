package com.lounah.silkapp.data.remote;


import com.lounah.silkapp.data.model.Dialog;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FirebaseApi implements Api {

    @Inject
    public FirebaseApi() {

    }

    @Override
    public Single<List<Dialog>> getDialogsByUserID(String... args) {
        return null;
    }
}

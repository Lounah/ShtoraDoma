package com.lounah.silkapp.data.remote;


import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lounah.silkapp.data.model.Dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class FirebaseApi implements Api {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_DIALOGS = "dialogs";
    private static final String PARTICIPANT_ID = "participant_id";
    private static final String DATE = "date";

    @Inject
    public FirebaseApi() {

    }

    @Override
    public Observable<List<Dialog>> getDialogsByUserID(String... args) {
        return Observable.create(source -> {

            Query query = db.collection(COLLECTION_DIALOGS)
                    .whereEqualTo(PARTICIPANT_ID, args[0]);

            query.addSnapshotListener((documentSnapshots, e) -> {

                List<Dialog> dialogs = new ArrayList<>();

                if (e != null) {
                    source.onError(new Throwable());
                }

                if (documentSnapshots != null) {
                    for (DocumentSnapshot snapshot : documentSnapshots) {
                        dialogs.add(snapshot.toObject(Dialog.class));
                    }
                    source.onNext(dialogs);
                }
            });
        });
    }
}

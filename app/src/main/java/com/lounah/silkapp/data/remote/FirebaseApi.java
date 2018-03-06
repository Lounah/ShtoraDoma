package com.lounah.silkapp.data.remote;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class FirebaseApi implements Api {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_DIALOGS = "dialogs";
    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_PRODUCTS = "products";
    private static final String COLLECTION_COMMENTS= "comments";
    private static final String PARTICIPANT_ID = "participant_id";
    private static final String DATE = "date";

    @Inject
    public FirebaseApi() {

    }

    @Override
    public Observable<List<Dialog>> getDialogsByUserID(int id) {
        return Observable.create(source -> {

            Query query = db.collection(COLLECTION_DIALOGS)
                    .whereEqualTo(PARTICIPANT_ID, id);

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

    @Override
    public Completable saveUser(@NonNull User user) {
        return Completable.create(source -> {
            db.collection(COLLECTION_USERS).add(user)
            .addOnSuccessListener(doc ->  source.onComplete())
            .addOnFailureListener(source::onError)
            .addOnCompleteListener(doc -> source.onComplete());
        });
    }

    @Override
    public Single<List<Product>> getProducts(int uid) {
        return Single.create(source -> {

            final List<Product> products = new ArrayList<>();

            db.collection(COLLECTION_PRODUCTS).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot snaps = task.getResult();

                            for (DocumentSnapshot doc : snaps) {
                                products.add(doc.toObject(Product.class));
                            }

                            source.onSuccess(products);

                        } else source.onError(new Throwable());
                    });
        });
    }

    @Override
    public Observable<List<Comment>> getComments(int id) {
        return Observable.create(source -> {

            Query query = db.collection(COLLECTION_COMMENTS)
                    .whereEqualTo("product_id", id);

            query.addSnapshotListener((documentSnapshots, e) -> {

               final List<Comment> comments = new ArrayList<>();

                if (e != null) {
                    source.onError(new Throwable());
                }

                if (documentSnapshots != null) {
                    for (DocumentSnapshot snapshot : documentSnapshots) {
                        comments.add(snapshot.toObject(Comment.class));
                    }
                    source.onNext(comments);
                }
            });

        });
    }
}

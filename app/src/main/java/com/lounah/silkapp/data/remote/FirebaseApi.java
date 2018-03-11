package com.lounah.silkapp.data.remote;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Message;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FirebaseApi implements Api {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String COLLECTION_DIALOGS = "dialogs";
    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_PRODUCTS = "products";
    private static final String COLLECTION_COMMENTS= "comments";
    private static final String COLLECTION_MESSAGES= "messages";
    private static final String PARTICIPANT_ID = "participant_id";
    private static final String DATE = "date";

    @Inject
    public FirebaseApi() {

    }

    @Override
    public Observable<List<Dialog>> getDialogsByUserID(int id) {
        return Observable.create(source -> {

//            Query query = db.collection(COLLECTION_DIALOGS)
//                    .whereEqualTo(PARTICIPANT_ID, id);
//
//            query.addSnapshotListener((documentSnapshots, e) -> {
//
//                List<Dialog> dialogs = new ArrayList<>();
//
//                if (e != null) {
//                    source.onError(new Throwable());
//                }
//
//                if (documentSnapshots != null) {
//                    for (DocumentSnapshot snapshot : documentSnapshots) {
//                        dialogs.add(snapshot.toObject(Dialog.class));
//                    }
//                    source.onNext(dialogs);
//                }
//            });


            Query getDialog = db.collection(COLLECTION_DIALOGS)
                    .whereEqualTo(PARTICIPANT_ID, id);

            getDialog.addSnapshotListener((documentSnapshots, e) -> {
               if (e != null)
                   source.onError(e);

               if (documentSnapshots != null) {
                   for (DocumentSnapshot snapshot : documentSnapshots) {
                       final String lastMessageId = (String) snapshot.get("last_message_id");
                       getMessageById(id, lastMessageId)
                               .doOnSuccess(message -> {

                                   final int from_id = message.getFrom_id();
                                   final int to_id = message.getTo_id();

                                   Single.zip(getUserById(from_id), getUserById(to_id), (from_user, to_user) -> {


                                       final List<Dialog> dialogs = new ArrayList<>(1);

                                       final Dialog dialog = new Dialog();

                                       if (to_id == id) {
                                           dialog.setHostStatus(from_user.getStatus());
                                           dialog.setLastMessageAuthor(from_user.getUsername());
                                           dialog.setParticipantAvatarUrl(from_user.getAvatar_link());
                                       } else {
                                           dialog.setHostStatus(to_user.getStatus());
                                           dialog.setLastMessageAuthor(from_user.getUsername());
                                           dialog.setParticipantAvatarUrl(from_user.getAvatar_link());
                                       }
                                       dialog.setLastMessageText(message.getText());
                                       dialog.setLastMessageTime(message.getDate());
                                       dialog.setLastMessageStatus(message.getStatus());
                                       dialog.setParticipantId(id);
                                       dialog.setDialogId(snapshot.getId());

                                       dialogs.add(dialog);

                                       return dialogs;
                                   }).subscribe(source::onNext);

                               }).subscribe();
                   }
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

            Log.i("ID = ", id + " ");

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

    @Override
    public Observable<List<Message>> getMessagesByDialogId(String dialog_id) {
        return Observable.create(emitter -> {


            final List<Message> messages = new ArrayList<>();

            db.collection(COLLECTION_DIALOGS)
                    .document(dialog_id)
                    .collection(COLLECTION_MESSAGES)
                    .orderBy("date")
                    .addSnapshotListener((snaps, e) ->  {

                        messages.clear();
                        
                        if (e != null) {
                            emitter.onError(new Throwable());
                        }

                        if (snaps != null) {
                            for (DocumentSnapshot snapshot : snaps) {
                                messages.add(snapshot.toObject(Message.class));
                            }
                            emitter.onNext(messages);
                        }
                    });
        });
    }

    @Override
    public Completable sendMessage(@NonNull Message message) {
        return Completable.create(source -> {
            db.collection(COLLECTION_DIALOGS)
                    .document(message.getDialogId())
                    .collection(COLLECTION_MESSAGES)
                    .add(message)
                    .addOnCompleteListener(docRef -> {
                        setLastMessageId(message.getDialogId(), docRef.getResult().getId())
                                .doOnComplete(source::onComplete)
                                .doOnError(source::onError)
                                .subscribe();
                    })
                    .addOnFailureListener(source::onError);
        });
    }

    private Completable setLastMessageId(@NonNull final String dialogId, @NonNull final String msgId) {
        return Completable.create(source -> {
            db.collection(COLLECTION_DIALOGS)
                    .document(dialogId)
                    .update("last_message_id", msgId)
                    .addOnSuccessListener(__ -> {
                        source.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        source.onError(e);
                    });
        });
    }

    private Single<Message> getMessageById(final int participant_id, @NonNull final String id) {

        return Single.create(source -> {

            Query query = db.collection(COLLECTION_DIALOGS)
                    .whereEqualTo(PARTICIPANT_ID, participant_id)
                    .limit(1);

            query.get()
                    .addOnSuccessListener(docs -> {
                        for (DocumentSnapshot snap : docs) {
                            db.collection(COLLECTION_DIALOGS)
                                    .document(snap.getId())
                                    .collection(COLLECTION_MESSAGES)
                                    .document(id)
                                    .get()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();

                                            if (document != null && document.exists()) {
                                                final Message message = document.toObject(Message.class);
                                                source.onSuccess(message);
                                            }
                                        } else source.onError(new Throwable());
                                    });
                        }
                    })
                    .addOnFailureListener(source::onError);


        });

    }

    private Single<User> getUserById(final int id) {
        return Single.create(source -> {
           db.collection(COLLECTION_USERS)
                   .whereEqualTo("id", id)
                   .get()
                   .addOnSuccessListener(docs -> {
                       for (DocumentSnapshot snapshot : docs) {
                           final User user = snapshot.toObject(User.class);
                           source.onSuccess(user);
                       }
                   })
                   .addOnFailureListener(source::onError);
        });
    }


}

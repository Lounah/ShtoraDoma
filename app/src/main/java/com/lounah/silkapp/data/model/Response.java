package com.lounah.silkapp.data.model;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.lounah.silkapp.data.model.Status.ERROR;
import static com.lounah.silkapp.data.model.Status.LOADING;
import static com.lounah.silkapp.data.model.Status.SUCCESS;

public class Response<T>{

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final Throwable error;

    private Response(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.data = data;
        this.error = error;
        this.status = status;
    }

    public static <T> Response<T> success(@NonNull T data) {
        return new Response<>(SUCCESS, data, null);
    }

    public static <T> Response<T> error(Throwable error) {
        return new Response<>(ERROR, null, error);
    }

    public static <T> Response<T> loading() {
        return new Response<>(LOADING, null, null);
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

}

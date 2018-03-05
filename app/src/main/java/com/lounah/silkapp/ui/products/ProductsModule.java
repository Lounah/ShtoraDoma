package com.lounah.silkapp.ui.products;


import android.content.Context;
import android.support.annotation.NonNull;

import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.local.product.ProductsDao;
import com.lounah.silkapp.data.remote.Api;
import com.lounah.silkapp.data.repository.DialogsRepository;
import com.lounah.silkapp.data.repository.ProductsRepository;
import com.lounah.silkapp.di.FragmentScoped;
import com.lounah.silkapp.ui.ItemClickListener;
import com.lounah.silkapp.ui.dialogs.DialogsRvAdapter;
import com.lounah.silkapp.ui.dialogs.DialogsViewModelFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductsModule {

    @Provides
    @FragmentScoped
    ItemClickListener provideItemListener(ProductsFragment fragment) {
        return fragment::onItemClicked;
    }

    @Provides
    @FragmentScoped
    ProductsRepository provideRepository(@NonNull final Api api, @NonNull final ProductsDao dao) {
        return new ProductsRepository(api, dao);
    }

    @Provides
    @FragmentScoped
    ProductsViewModelFactory provideProductsViewModelFactory(@NonNull final ProductsRepository repository,
                                                           @Named("uid") final int uid) {
        return new ProductsViewModelFactory(repository, uid);
    }

    @Provides
    @FragmentScoped
    ProductsRvAdapter provideAdapter(@NonNull final Context context, @NonNull final ItemClickListener listener) {
        return new ProductsRvAdapter(context, listener);
    }

}

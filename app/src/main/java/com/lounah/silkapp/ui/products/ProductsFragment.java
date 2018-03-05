package com.lounah.silkapp.ui.products;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lounah.silkapp.R;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.Response;
import com.lounah.silkapp.ui.BaseFragment;
import com.lounah.silkapp.ui.ItemClickListener;
import com.lounah.silkapp.ui.MainActivity;
import com.lounah.silkapp.ui.dialogs.DialogsFragment;
import com.lounah.silkapp.ui.dialogs.DialogsRvAdapter;
import com.lounah.silkapp.ui.dialogs.DialogsViewModel;
import com.lounah.silkapp.ui.dialogs.DialogsViewModelFactory;
import com.lounah.silkapp.ui.products.comments.CommentsFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsFragment extends BaseFragment {

    @Inject
    ProductsViewModelFactory factory;

    @Inject
    ProductsRvAdapter adapter;

    @BindView(R.id.products_toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    private ItemClickListener itemListener;

    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductsViewModel viewModel = ViewModelProviders.of(this, factory).get(ProductsViewModel.class);
        viewModel.getProducts().observe(this, response -> {
            switch (response.getStatus()) {
                case ERROR:
                    processErrorState(response.getError());
                    break;

                case LOADING:
                    processLoadingState();
                    break;

                case SUCCESS:
                    processSuccessState(response);
                    break;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).onUpdateToolbar(toolbar);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProducts.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvProducts.setLayoutManager(linearLayoutManager);
    }


    private void processLoadingState() {
        Toast.makeText(getContext(), "LOADING...", Toast.LENGTH_SHORT).show();
    }

    private void processErrorState(@Nullable final Throwable error) {
        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
        Log.i("FIREBASE ERROR", error.getMessage());
    }

    private void processSuccessState(@NonNull final Response<List<Product>> products) {
        Toast.makeText(getContext(), "SUCCESS" + products.getData().size(), Toast.LENGTH_SHORT).show();
        adapter.updateDataSet(products.getData());
    }

    public void onItemClicked(final int id) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        mFragmentNavigator.pushFragment(fragment, true);
    }

}

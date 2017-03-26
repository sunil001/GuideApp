package com.guideapp.ui.views.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guideapp.R;
import com.guideapp.model.Local;
import com.guideapp.ui.views.localdetail.LocalDetailActivity;
import com.guideapp.ui.views.DividerItemDecoration;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteContract.View, FavoriteAdapter.ItemClickListener {
    private FavoriteAdapter mAdapter;
    private FavoriteContract.Presenter mPresenter;
    private TextView mMessageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        setupRecyclerView(view);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        mMessageView = (TextView) view.findViewById(R.id.message_view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new FavoritePresenter(this);
        mPresenter.loadLocals(getActivity().getSupportLoaderManager());
    }

    private void setupRecyclerView(View view) {
        mAdapter = new FavoriteAdapter(getContext(), this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.onResume(getActivity().getSupportLoaderManager());
    }

    @Override
    public void showNoItemsMessage() {
        mMessageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoItemsMessage() {
        mMessageView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Local item, ImageView view) {
        mPresenter.openLocalDetails(item, view);
    }

    @Override
    public void showLocals(List<Local> locals) {
        mAdapter.replaceData(locals);
    }

    @Override
    public void showLocalDetailUi(Local local, ImageView view) {
        LocalDetailActivity.navigate(getActivity(), view, local.getId(), local.getIdCategory());
    }
}
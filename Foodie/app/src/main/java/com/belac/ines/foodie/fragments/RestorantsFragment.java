package com.belac.ines.foodie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.helper.Restoran;
import com.belac.ines.foodie.helper.RestoranAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestorantsFragment extends Fragment {

    private List<Restoran> restoranList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RestoranAdapter restoranAdapter;
    private EditText search;

    public RestorantsFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restorants, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        restoranAdapter = new RestoranAdapter(restoranList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(restoranAdapter);

        prepareRestoranData();

        search = (EditText) view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence query, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence query, int i, int i1, int i2) {
                restoranAdapter.getFilter().filter(query);

                restoranAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable query) {}
        });
        return view;
    }

    private void prepareRestoranData() {
        Restoran res = new Restoran("Angelus", "Varaždin");
        restoranList.add(res);
        res = new Restoran("Verglec", "Varaždin");
        restoranList.add(res);
        res = new Restoran("Palacinkarnica", "Zagreb");
        restoranList.add(res);

        restoranAdapter.notifyDataSetChanged();
    }

}

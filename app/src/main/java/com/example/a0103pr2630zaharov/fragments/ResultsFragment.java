package com.example.a0103pr2630zaharov.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a0103pr2630zaharov.adapters.ResultsAdapter;
import com.example.a0103pr2630zaharov.data.Results;
import com.example.a0103pr2630zaharov.data_base.DataBaseManager;
import com.example.a0103pr2630zaharov.databinding.FragmentResultsBinding;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {
    FragmentResultsBinding binding;
    DataBaseManager dataBaseManager;
    List<Results> resultsList = new ArrayList<>();
    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.openDB();
        resultsList = dataBaseManager.getResults();
        ResultsAdapter resultsAdapter = new ResultsAdapter(getContext(), resultsList,dataBaseManager);
        binding.recyclerView.setAdapter(resultsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDB();
    }
}
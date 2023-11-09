package com.example.a0103pr2630zaharov.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a0103pr2630zaharov.R;
import com.example.a0103pr2630zaharov.data.Results;
import com.example.a0103pr2630zaharov.data_base.DataBaseManager;
import com.example.a0103pr2630zaharov.fragments.ResultsFragment;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHold> {
    LayoutInflater layoutInflater;
    List<Results> resultsList;
    DataBaseManager dataBaseManager;

    public ResultsAdapter(Context context, List<Results> resultsList, DataBaseManager dataBaseManager){
        this.dataBaseManager = dataBaseManager;
        this.resultsList = resultsList;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ResultsAdapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.result_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ViewHold holder, int position) {
        dataBaseManager = new DataBaseManager(holder.itemView.getContext());
        dataBaseManager.openDB();
        resultsList = dataBaseManager.getResults();
        Results result = resultsList.get(position);
        holder.tvRes.setText(result.getRes());
        holder.tvExp.setText(result.getExpression());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseManager.openDB();
                dataBaseManager.deleteResult(result);
                dataBaseManager.closeDB();
                ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new ResultsFragment()).commit();
            }
        });
        dataBaseManager.closeDB();
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        TextView tvRes, tvExp;
        Button btn;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            tvRes = itemView.findViewById(R.id.textViewResItem);
            tvExp = itemView.findViewById(R.id.textViewExItem);
            btn = itemView.findViewById(R.id.button);
        }
    }
}

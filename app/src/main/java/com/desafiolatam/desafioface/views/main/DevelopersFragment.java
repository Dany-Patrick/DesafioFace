package com.desafiolatam.desafioface.views.main;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.adapters.DevelopersAdapter;
import com.desafiolatam.desafioface.networks.GetUsers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevelopersFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private DevelopersAdapter adapter;
    private boolean pendingrequest;
    public DevelopersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = view.findViewById(R.id.reloadSrl);
        RecyclerView recyclerView = view.findViewById(R.id.developersRv);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DevelopersAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findLastVisibleItemPosition();
                int total = linearLayoutManager.getItemCount();
                if(total - 10 < position )
                {
                    if(!pendingrequest)
                    {
                        Map<String,String> map = new HashMap<String,String>();
                        String currentPages = String.valueOf((total / 10) + 1);
                        map.put("page",currentPages);
                        new ScrollRquest(4).execute(map);
                    }
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pendingrequest = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        adapter.update();
                    }
                },800);
            }
        });
    }

    public void update(String name) {
        pendingrequest = true;
        adapter.find(name);
    }

    private class ScrollRquest extends GetUsers
    {


        public ScrollRquest(int adittionalPages) {
            super(adittionalPages);
        }

        @Override
        protected void onPreExecute() {

            pendingrequest = true;
            refreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Integer integer) {
           pendingrequest = false;
           adapter.update();
           refreshLayout.setRefreshing(false);
        }
    }
}

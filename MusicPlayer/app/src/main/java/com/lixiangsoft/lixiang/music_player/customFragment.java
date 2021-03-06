package com.lixiangsoft.lixiang.music_player;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class customFragment extends Fragment {
    private View rootView;
    private listChangeReceiver listChangeReceiver;
    private RecyclerView playListDetail;
    private RecyclerView playList;

    public customFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_custom, container, false);
        playListDetail = (RecyclerView) rootView.findViewById(R.id.playList_detail);
        playList = (RecyclerView) rootView.findViewById(R.id.playListRecyclerView);
        //动态注册广播
        listChangeReceiver = new listChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("list_changed");
        getActivity().registerReceiver(listChangeReceiver, intentFilter);
        if (MyApplication.getBoxStore().boxFor(Playlist.class).getAll().size() > 0) {
            MyApplication.setCustomListNow(MyApplication.getBoxStore().boxFor(Playlist.class).getAll().get(0).getName());
            showPlayList();
            showPlayListDetail();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(listChangeReceiver);
        super.onDestroy();
    }

    public void showPlayListDetail() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        playListDetail.setLayoutManager(layoutManager);
        playlistdetailAdapter adapter = new playlistdetailAdapter();
        playListDetail.setAdapter(adapter);
    }

    public void showPlayList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        playList.setLayoutManager(layoutManager);
        playListAdapter adapter = new playListAdapter();
        playList.setAdapter(adapter);
    }

    private class listChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("Action", 0) == 0) {
                showPlayList();
            }
            showPlayListDetail();
        }
    }

}

package com.example.visionboard;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.visionboard.model.Board;
import com.example.visionboard.model.CatalogViewModel;
import com.example.visionboard.util.OnRowClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class BoardListFragment extends Fragment implements OnRowClickListener {

    private ArrayList<Board> boardArrayList = new ArrayList<>();
    private RecyclerView  recyclerView;
    private BoardRecyclerViewAdapter recyclerViewAdapter = new BoardRecyclerViewAdapter(boardArrayList, this);
    private Context context;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board_list,container,false);
        recyclerView=view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewModel();
    }


    @Override
    public void OnItemClick(View itemView, int position) {
        Board board = boardArrayList.get(position);
        Intent intent = new Intent(getContext(),BoardDetailActivity.class);
        intent.putExtra("board",board);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId()==R.id.menu_add_item){
            startActivity(new Intent(getContext(),AddBoardActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewModel(){

        ViewModelProvider.AndroidViewModelFactory factory = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication());
        CatalogViewModel viewModel = new ViewModelProvider(requireActivity(),factory).get(CatalogViewModel.class);
        viewModel.getBoards().observe(getViewLifecycleOwner(), boards -> {
            boardArrayList.addAll(boards);
            recyclerViewAdapter.notifyDataSetChanged();
        });
    }
}

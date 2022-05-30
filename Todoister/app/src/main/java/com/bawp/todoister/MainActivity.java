package com.bawp.todoister;

import android.content.Intent;
import android.os.Bundle;

import com.bawp.todoister.adapter.OnTodoClickListener;
import com.bawp.todoister.adapter.RecyclerViewAdapter;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {

    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int counter=0;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication()
        ).create(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(this, tasks -> {
          recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
          recyclerView.setAdapter(recyclerViewAdapter);
        });

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->{
//            Task task = new Task("Task " + counter++, Priority.MEDIUM, Calendar.getInstance().getTime(),
//                    Calendar.getInstance().getTime(),false);
//            TaskViewModel.insert(task);

//            LiveData<Task> task = taskViewModel.get(2);
//             task.observe(this, task1 -> Log.i("Database info", task1.toString()));

            showBottomSheetDialog();


        });

    }

    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialog();
    }

    @Override
    public void onRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
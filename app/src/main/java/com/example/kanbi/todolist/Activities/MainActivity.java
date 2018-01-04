package com.example.kanbi.todolist.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kanbi.todolist.API.ApiClient;
import com.example.kanbi.todolist.API.ApiInterface;
import com.example.kanbi.todolist.R;
import com.example.kanbi.todolist.ToDoAdapter;
import com.example.kanbi.todolist.ToDoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public ToDoAdapter todoAdapter;
    private List<ToDoModel> todos;
    private ApiInterface apiService;
    private FloatingActionButton addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define recyclerview
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //create ApiInterface
        apiService = ApiClient.getClient().create(ApiInterface.class);

        //show todo List
        loadToDoList();

        //go to new activity for adding new task
        addBtn = (FloatingActionButton) findViewById(R.id.addFab);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CreateToDo.class);
                startActivity(intent);
            }
        });

        //delete task
        deleteTask();

    }

    private void loadToDoList(){
        //loading msg
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        //retrieve data from Api JSON
        Call<List<ToDoModel>> call = apiService.getResults();
        call.enqueue(new Callback<List<ToDoModel>>() {
            @Override
            public void onResponse(Call<List<ToDoModel>> call, Response<List<ToDoModel>> response) {
                //  int statusCode=response.code();  if needed for showing error type
                todos = response.body();
                todoAdapter = new ToDoAdapter(todos);
                todoAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(todoAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<ToDoModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private void deleteTask(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "to Delete", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();

                //get id from swipped position
                final ToDoModel item = todos.get(position);
                long id=item.getId();

                todos.remove(position);
                todoAdapter.notifyDataSetChanged();

                //delete task on Api Json
                Call<ToDoModel> call = apiService.deleteTask(id);

                call.enqueue(new Callback<ToDoModel>() {
                    @Override
                    public void onResponse(Call<ToDoModel> call, Response<ToDoModel> response) {

                        if(response.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),"Task deleted!", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getApplicationContext(),"Failed to delete task", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ToDoModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


}

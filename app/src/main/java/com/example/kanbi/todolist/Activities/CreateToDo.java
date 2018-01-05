package com.example.kanbi.todolist.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanbi.todolist.API.ApiClient;
import com.example.kanbi.todolist.API.ApiInterface;
import com.example.kanbi.todolist.R;
import com.example.kanbi.todolist.ToDoModel;
import com.example.kanbi.todolist.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateToDo extends AppCompatActivity {
    private FloatingActionButton listBtn;
    private TextView titleInput;
    private TextView descInput;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtask);

        //dismiss keyboard when typing outside
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.hideKeyboard(CreateToDo.this);
                return false;
            }
        });

        titleInput = (TextView) findViewById(R.id.createTitle);
        descInput = (TextView) findViewById(R.id.createDesc);
        listBtn = (FloatingActionButton) findViewById(R.id.backListBtn);

        saveBtn= (Button) findViewById(R.id.createSaved);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString().trim();
                String description = descInput.getText().toString().trim();

                //leave input as option for user, don't have to fill both
                if(!TextUtils.isEmpty(title)|| !TextUtils.isEmpty(description)) {
                    createToDoList(title, description);
                }
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateToDo.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createToDoList(String title, String description){

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        ToDoModel todo=new ToDoModel();
        todo.setTitle(title);
        todo.setDescription(description);

        Call<ToDoModel> call = apiService.createTodo(todo);

        call.enqueue(new Callback<ToDoModel>() {
            @Override
            public void onResponse(Call<ToDoModel> call, Response<ToDoModel> response) {

                if(response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Task created!", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(getApplicationContext(),"not right yet", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ToDoModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent intent = new Intent(CreateToDo.this,MainActivity.class);
        startActivity(intent);
    }


}

package com.example.kanbi.todolist.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
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

public class Details extends AppCompatActivity {

    private TextView taskTitle;
    private TextView taskDetails;
    private Button itemSaveBtn;
    private FloatingActionButton mainListBtn;

    public final String ID_KEY = "ID_KEY";
    public final String TITLE_KEY = "TITLE_KEY";
    public final String DESC_KEY = "DESC_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clickeditem);

        //dismiss keyboard when click outside
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.hideKeyboard(Details.this);
                return false;
            }
        });

        Bundle extras = getIntent().getExtras();

        taskTitle = (TextView) findViewById(R.id.itemTitle);
        taskDetails = (TextView) findViewById(R.id.itemDes);
        itemSaveBtn = (Button) findViewById(R.id.itemSave);
        mainListBtn = (FloatingActionButton) findViewById(R.id.mainListBtn);

        //receive intent
        final long taskIdGet = extras.getLong(ID_KEY);
        final String taskTitleGet = extras.getString(TITLE_KEY);
        final String taskDetailsGet = extras.getString(DESC_KEY);

        //add content to each element
        taskTitle.setText(taskTitleGet);
        taskDetails.setText(taskDetailsGet);

        //save edited task
        itemSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateToDoList(taskIdGet,taskTitleGet,taskDetailsGet);
            }
        });

        //back to main List
        mainListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    private void updateToDoList(long id, String title, String description){

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        ToDoModel todo=new ToDoModel();
        todo.setTitle(title);
        todo.setDescription(description);

        Call<ToDoModel> call = apiService.updateTask(id,todo);

        call.enqueue(new Callback<ToDoModel>() {
            @Override
            public void onResponse(Call<ToDoModel> call, Response<ToDoModel> response) {

                if(response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Task updated!", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(getApplicationContext(),"Updating failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ToDoModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

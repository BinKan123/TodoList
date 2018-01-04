package com.example.kanbi.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanbi.todolist.Activities.Details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanbi on 30/12/2017.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>  {

    private List<ToDoModel> todoList;
    public final String ID_KEY="ID_KEY";
    public final String TITLE_KEY="TITLE_KEY";
    public final String DESC_KEY="DESC_KEY";

    public ToDoAdapter(List<ToDoModel> list){
        todoList=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();

        final ToDoModel item = todoList.get(position);

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());

        //click to see Details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Details.class);

                intent.putExtra(ID_KEY,item.getId());
                intent.putExtra(TITLE_KEY,item.getTitle());
                intent.putExtra(DESC_KEY,item.getDescription());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

}

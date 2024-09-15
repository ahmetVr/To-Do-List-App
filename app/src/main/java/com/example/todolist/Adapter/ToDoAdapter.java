package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddTask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db,MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();

        ToDoModel element = toDoList.get(position);
        holder.task.setText(element.getTask());
        holder.task.setChecked(convertBoolean(element.getStatus()));

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(element.getId(),1);
                } else {
                    db.updateStatus(element.getId(),0);
                }
            }
        });
    }
    private boolean convertBoolean(int number) {
        return number != 0;
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }
    public void setTasks(List<ToDoModel> list) {
        this.toDoList = list;
        notifyDataSetChanged();
    }

    public void editElement(int position) {
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();

        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        AddTask fragment = new AddTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddTask.TAG);
    }

    public void deleteItem(int position) {
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        public ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.to_do_check_box);
        }
    }
    public Context getContext() {
        return activity;
    }
}

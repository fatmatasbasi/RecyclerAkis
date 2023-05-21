package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TodoAdapter todoAdapter;
    List<TodoItem> todoItemList;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoItemList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoItemList);
        recyclerView.setAdapter(todoAdapter);

        databaseHelper = new DatabaseHelper(this);

        loadTodoItems();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTodoDialog();
            }
        });
    }

    private void loadTodoItems() {
        todoItemList.clear();
        todoItemList.addAll(databaseHelper.getAllTodoItems());
        todoAdapter.notifyDataSetChanged();
    }

    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Todo");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String todoText = input.getText().toString().trim();
                if (!TextUtils.isEmpty(todoText)) {
                    TodoItem todoItem = new TodoItem(todoText);
                    databaseHelper.insertTodoItem(todoItem);
                    loadTodoItems();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}

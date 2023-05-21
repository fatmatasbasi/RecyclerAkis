package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoItem> todoItems;

    public TodoAdapter(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem todoItem = todoItems.get(position);
        holder.textView.setText(todoItem.getText());
        holder.checkBox.setChecked(todoItem.isCompleted());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                todoItem.setCompleted(isChecked);
                // Burada todo durumunu güncelleyebilirsiniz (örneğin, veritabanında güncelleme yapabilirsiniz)
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Burada todo öğesini silme işlemini gerçekleştirebilirsiniz (örneğin, veritabanından kaldırabilirsiniz)
                showDeleteConfirmationDialog(holder.itemView.getContext(), todoItem);
                return true;
            }
        });
    }

    private void showDeleteConfirmationDialog(Context context, TodoItem todoItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Note")
                .setMessage("Are you sure you want to delete the note?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Notu silme işlemini gerçekleştir
                        deleteTodoItem(todoItem);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteTodoItem(TodoItem todoItem) {
        // Burada todo öğesini silme işlemini gerçekleştir (örneğin, veritabanından kaldırabilirsiniz)
        todoItems.remove(todoItem);
        notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.todoText);
            checkBox = itemView.findViewById(R.id.todoCheckBox);
        }
    }
}


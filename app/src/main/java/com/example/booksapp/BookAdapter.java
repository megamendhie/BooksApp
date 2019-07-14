package com.example.booksapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<Book> listOfBooks;
    private Context context;

    public BookAdapter(Context context, ArrayList<Book> listOfBooks){
        this.context = context;
        this.listOfBooks = listOfBooks;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate( R.layout.book_placeholder, viewGroup, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {
        bookViewHolder.txtTitle.setText(listOfBooks.get(position).getTitle());
        bookViewHolder.txtAuthor.setText(listOfBooks.get(position).getAuthor());
        bookViewHolder.txtGenre.setText(listOfBooks.get(position).getGenre());
    }

    @Override
    public int getItemCount() {
        return listOfBooks.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtAuthor, txtGenre;
        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtGenre = itemView.findViewById(R.id.txtGenre);
        }
    }
}

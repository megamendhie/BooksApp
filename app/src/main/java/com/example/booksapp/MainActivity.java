package com.example.booksapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listBooks;
    private ShimmerFrameLayout shimmerLayout;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listBooks = findViewById(R.id.listBooks);
        listBooks.setLayoutManager(new LinearLayoutManager(this));
        shimmerLayout = findViewById(R.id.shimmer);
        shimmerLayout.startShimmer();
        database = FirebaseFirestore.getInstance();
        queryDatabase();
    }

    private void queryDatabase(){
        String[] genres = {"romance", "action", "fantasy"}; //This could be created dynamically
        int count = genres.length;

        Query[] queries = new Query[count];
        Task[] tasks = new Task[count];

        //query the genres and get the task, all within a loop
        for(int i = 0; i < count; i++){
            queries[i] = database.collection("books").whereEqualTo("genre", genres[i]);
            tasks[i] = queries[i].get();
        }

        Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> list) {
                ArrayList<Book> books = new ArrayList<>();
                for(Object object: list){
                    QuerySnapshot querySnapshot = (QuerySnapshot) object;
                    if(querySnapshot !=null || !querySnapshot.isEmpty()){
                        for(DocumentSnapshot snapshot: querySnapshot.getDocuments()){
                            Book book = snapshot.toObject(Book.class);
                            books.add(book);
                        }
                    }
                }

                //populating recyclerView with the list
                listBooks.setAdapter(new BookAdapter(MainActivity.this, books));
                shimmerLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
            }
        });
    }
}

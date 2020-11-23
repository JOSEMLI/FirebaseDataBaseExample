package com.example.firebasedatabaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    ScaleAnimation shrinkAnim;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private FirebaseRecyclerAdapter<Movie , MovieViewHolder> adapter;

    private TextView tvNoMovies;


    private DatabaseReference movierefence ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static final String userId = "53";
    private static final String idMovies = "idMovies";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        tvNoMovies = findViewById(R.id.tv_no_movies);

        shrinkAnim = new ScaleAnimation(1.15f,0f,1.15f,0f, Animation.RELATIVE_TO_SELF , 0.5f , Animation.RELATIVE_TO_SELF, 0.5f);

        if(recyclerView != null)
        {
            recyclerView.setHasFixedSize(true);
        }

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        movierefence =   FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("movies");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        FirebaseRecyclerOptions<Movie> options = new FirebaseRecyclerOptions.Builder<Movie>().setQuery(movierefence, Movie.class).build();

        FirebaseRecyclerAdapter<Movie, MovieViewHolder> adapter = new FirebaseRecyclerAdapter<Movie, MovieViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MovieViewHolder holder, int position, @NonNull Movie model) {

                if (tvNoMovies.getVisibility() == View.VISIBLE)
                {
                    tvNoMovies.setVisibility(View.VISIBLE);
                }
                holder.tvMovieName.setText(model.getMovieName());
                holder.ratingBar.setRating(model.getMovieRating());

                Picasso.get().load(model.getMoviePoster()).centerCrop().into(holder.ivMoviePoster);

                //TODO: codigo para los botones de mantenimiento

                

            }

            @NonNull
            @Override
            public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };



    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView tvMovieName;
        RatingBar ratingBar;
        ImageView ivMoviePoster;
        ImageButton eliminar;
        ImageButton modificar;

        public MovieViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tv_name);
        }
    }


}
package com.ebadamza.firebasedb

import android.content.ContentValues.TAG
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference

        val movie1 = Movie("John Wick", 5.0, 2023)
        val movie2 = Movie("Wakanda Forever", 5.0, 2023)
        val movie3 = Movie("Frozen 2", 4.0, 2019)
        val movie4 = Movie("Avengers Endgame", 4.9, 2019)

        val user1 = "EB"
        val preferencesEb = UserPreferences(true)
        database.child("users").child(user1).child("Preferences").setValue(preferencesEb)
            .addOnSuccessListener {
                // Success message
            }
            .addOnFailureListener {
                // Failure message
            }
         val cinemaEB = listOf(movie1, movie2)
        database.child("users").child(user1).child("MovieList").setValue(cinemaEB)

        val user2 = "Sarina"
        val preferencesSarina = UserPreferences(false)
        database.child("users").child(user2).child("Preferences").setValue(preferencesSarina)
        val cinemaSarina = listOf(movie1, movie3, movie4)
        database.child("users").child(user2).child("MovieList").setValue(cinemaSarina)

        // get movies list for a user (user1)
        val databaseRef = database.child("users").child(user1).child("MovieList")
        val movieListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val movies = dataSnapshot.getValue<List<Movie>>()?.filterNotNull()
                Log.i("MoviesRetrieved", movies.toString())
                if (movies != null) {
                    // show in recyclerView
                    generateRecyclerView(movies)
                } else {
                    Log.i("NoMoviesToRetrieve", "Eish, no movies!")
                    // update the UI in some way to show no movies to show
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("MoviesRetrievalError", databaseError.toString())
            }
        }
        databaseRef.addValueEventListener(movieListener)

        // get preferences for a user (user1)
        database.child("users").child(user1).child("Preferences").get()
            .addOnSuccessListener {
                Log.i("PreferencesRetrieved", it.value.toString())
                // Deserialize to a UserPreferences object
            }
            .addOnFailureListener {
                // Failure message
            }
    }

    fun generateRecyclerView(movieData: List<Movie>) {
        val recyclerview = findViewById<RecyclerView>(R.id.MoviesRecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        Log.i("MoviesToShow", movieData.toString())
        val adapter = CustomAdapter(movieData)
        recyclerview.adapter = adapter
    }
}
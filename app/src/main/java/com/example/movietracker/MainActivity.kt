package com.example.movietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movietracker.presentation.navigation.MovieTrackerNavGraph
import com.example.movietracker.ui.theme.MovieTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieTrackerTheme {
                MovieTrackerNavGraph()
            }
        }
    }
}

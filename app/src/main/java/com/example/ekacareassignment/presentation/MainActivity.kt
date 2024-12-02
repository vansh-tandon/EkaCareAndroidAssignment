package com.example.ekacareassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ekacareassignment.presentation.ui.theme.EkaCareAssignmentTheme
import com.example.ekacareassignment.presentation.userInfoScreen.UserInfoScreenRoot
import com.example.ekacareassignment.presentation.userInfoScreen.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EkaCareAssignmentTheme {
                val viewmodel: UserInfoViewModel by viewModels()
                UserInfoScreenRoot(viewModel = viewmodel)
            }
        }
    }
}
package com.example.acugemma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.acugemma.navigation.Screen
import com.example.acugemma.ui.components.BottomNavigationBar
import com.example.acugemma.ui.screens.HomeScreen
import com.example.acugemma.ui.screens.LessonScreen
import com.example.acugemma.ui.screens.SubjectDetailScreen
import com.example.acugemma.ui.screens.ProgressScreen
import com.example.acugemma.ui.screens.SubjectsScreen
import com.example.acugemma.ui.screens.ProfileScreen
import androidx.compose.foundation.layout.padding
import com.example.acugemma.ui.theme.AcuGemmaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcuGemmaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AcuGemma()
                }
            }
        }
    }
}

@Composable
fun AcuGemma() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) } // 0 for Home/Learn

    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigationBar(
                    selectedTab = selectedTab,
                    onTabSelected = { index ->
                        selectedTab = index
                        when (index) {
                            0 -> navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            1 -> navController.navigate(Screen.Subjects.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            2 -> navController.navigate(Screen.Progress.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            3 -> navController.navigate(Screen.Profile.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onSubjectClick = { subject ->
                        navController.navigate(Screen.SubjectDetail.createRoute(subject.name))
                    }
                )
            }

            composable(Screen.SubjectDetail.route) { backStackEntry ->
                val subjectName = backStackEntry.arguments?.getString("subjectName") ?: ""
                SubjectDetailScreen(
                    subjectName = subjectName,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onTopicClick = {topic ->
                        navController.navigate(Screen.Lesson.createRoute(topic.id))
                    }
                )

            }

            composable(Screen.Lesson.route) { backStackEntry ->
                val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
                LessonScreen(
                    topicId = topicId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Progress.route) {
                ProgressScreen()
            }

            composable(Screen.Subjects.route) {
                SubjectsScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    AcuGemmaTheme {
        AcuGemma()
    }
}
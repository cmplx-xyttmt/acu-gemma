package com.example.acugemma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.acugemma.navigation.Screen
import com.example.acugemma.ui.screens.HomeScreen
import com.example.acugemma.ui.screens.LessonScreen
import com.example.acugemma.ui.screens.SubjectDetailScreen
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

    NavHost(navController = navController, startDestination = Screen.Home.route) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    AcuGemmaTheme {
        AcuGemma()
    }
}
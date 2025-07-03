package com.example.acugemma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    Home()
                }
            }
        }
    }
}

val WHITE_BACKGROUND_COLOR = Color(0x00FFFFFF)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Learn",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color(0xFF181611)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF181611)
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SubjectsSection()
            }
            item {
                ContinueLearningSection()
            }
        }

    }
}

@Composable
fun SubjectsSection() {
    Column {
        Text(
            text = "Subjects",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF181611),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SubjectCard(
            title = "Mathematics",
            description = "Explore numbers, shapes, and problem-solving.",
            drawableResId = R.drawable.math_image
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubjectCard(title = "Geography",
            description = "Discover the world, its landscpaes, and cultures.",
            drawableResId = R.drawable.geography_image)
    }
}

@Composable
fun SubjectCard(
    title: String,
    description: String,
    drawableResId: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = WHITE_BACKGROUND_COLOR),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF181611),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = description,
                        fontSize = 18.sp,
                        color = Color(0xFF8a7c60),
                        lineHeight = 22.sp
                    )
                }

                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFf4b43d),
                        contentColor = Color(0xFF181611)
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .height(32.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "Start",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                }
            }
        }


    }
}

@Composable
fun ContinueLearningSection() {
    Column {
        Text(
            text = "Continue Learning",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF181611),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ContinueLearningCard(
            title = "Fractions",
            subject = "Mathematics",
            onClick = {}
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinueLearningCard(
    title: String,
    subject: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = WHITE_BACKGROUND_COLOR),
//        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFf5f3f0),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null,
                    tint = Color(0xFF181611),
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF181611),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subject,
                    fontSize = 14.sp,
                    color = Color(0xFF181611),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go to lesson",
                tint = Color(0xFF181611),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFF1F1F1)
        )

        NavigationBar(
            containerColor = Color(0x00FFFFFF),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x00FFFFFF))
        ) {
            val navItems = listOf(
                BottomNavItem("Learn", Icons.Default.Home, true),
                BottomNavItem(
                    "Practice",
                    Icons.Default.Build,
                    false
                ), // TODO: Add more appropriate Icon,
                BottomNavItem("Progress", Icons.Default.Check, false),
                BottomNavItem("Profile", Icons.Default.Person, false)
            )

            navItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedTab == index,
                    onClick = { onTabSelected(index) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF181611),
                        selectedTextColor = Color(0xFF181611),
                        unselectedIconColor = Color(0xFF8a7c60),
                        unselectedTextColor = Color(0xFF8a7c60),
                        indicatorColor = Color(0x00FFFFFF)
                    )
                )
            }
        }

    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val isSelected: Boolean
)


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    AcuGemmaTheme {
        Home()
    }
}
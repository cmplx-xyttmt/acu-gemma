package com.example.acugemma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                    "Subjects",
                    Icons.Default.MenuBook,
                    false
                ), // TODO: Add more appropriate Icon,
                BottomNavItem("Progress", Icons.Default.ShowChart, false),
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

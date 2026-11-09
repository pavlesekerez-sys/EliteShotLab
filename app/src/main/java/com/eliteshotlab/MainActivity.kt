package com.eliteshotlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Orange = Color(0xFFFF7A1A)
private val Background = Color(0xFF101114)
private val SurfaceDark = Color(0xFF1B1D22)
private val Muted = Color(0xFF9A9DA6)

private enum class AppTab(val label: String) {
    HOME("Home"), TRAINING("Training"), CALENDAR("Calendar"),
    AI_COACH("AI Coach"), PROGRESS("Progress"), SETTINGS("Settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EliteShotLabApp() }
    }
}

@Composable
private fun EliteShotLabApp() {
    var selectedTab by remember { mutableStateOf(AppTab.HOME) }
    val colors = darkColorScheme(
        primary = Orange,
        onPrimary = Color.Black,
        background = Background,
        surface = SurfaceDark,
        onBackground = Color.White,
        onSurface = Color.White,
        secondary = Orange
    )

    MaterialTheme(colorScheme = colors) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(containerColor = SurfaceDark, contentColor = Color.White) {
                    val tabs = listOf(
                        AppTab.HOME, AppTab.TRAINING, AppTab.CALENDAR,
                        AppTab.AI_COACH, AppTab.PROGRESS, AppTab.SETTINGS
                    )
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = when (tab) {
                                        AppTab.HOME -> Icons.Default.Home
                                        AppTab.TRAINING -> Icons.Default.FitnessCenter
                                        AppTab.CALENDAR -> Icons.Default.CalendarMonth
                                        AppTab.AI_COACH -> Icons.Default.AutoAwesome
                                        AppTab.PROGRESS -> Icons.Default.TrendingUp
                                        AppTab.SETTINGS -> Icons.Default.Settings
                                    },
                                    contentDescription = tab.label
                                )
                            },
                            label = { Text(tab.label, fontSize = 9.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Orange,
                                selectedTextColor = Orange,
                                indicatorColor = Color(0xFF392416),
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            when (selectedTab) {
                AppTab.HOME -> HomeScreen(Modifier.padding(padding))
                AppTab.TRAINING -> SimpleScreen("TRAINING", "Your workouts start here.", Modifier.padding(padding))
                AppTab.CALENDAR -> SimpleScreen("CALENDAR", "Your 16-week training calendar.", Modifier.padding(padding))
                AppTab.AI_COACH -> SimpleScreen("AI COACH", "Your personal basketball development coach.", Modifier.padding(padding))
                AppTab.PROGRESS -> SimpleScreen("PROGRESS", "Track shots, workouts and improvement.", Modifier.padding(padding))
                AppTab.SETTINGS -> SimpleScreen("SETTINGS", "Your profile and app preferences.", Modifier.padding(padding))
            }
        }
    }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 28.dp, bottom = 24.dp)
    ) {
        item {
            Text("ELITE SHOT LAB", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(Modifier.height(8.dp))
            Text("BUILT IN THE GYM.\nPROVEN IN THE GAME.", color = Color.White, fontSize = 27.sp, lineHeight = 32.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(8.dp))
            Text("Your basketball development starts today.", color = Muted, fontSize = 14.sp)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("WEEK", "01", "Foundation", Modifier.weight(1f))
                StatCard("PROGRAM", "16", "Weeks", Modifier.weight(1f))
                StatCard("LEVEL", "01", "Build", Modifier.weight(1f))
            }
        }
        item {
            SectionTitle("TODAY'S FOCUS")
            DarkCard {
                Text("Pull-up Mid-Range", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text("Footwork • Balance • Shot consistency", color = Muted, fontSize = 13.sp)
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("FOUNDATION PHASE", color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Text("45–60 MIN", color = Muted, fontSize = 11.sp)
                }
            }
        }
        item {
            SectionTitle("YOUR DEVELOPMENT PLAN")
            DarkCard {
                PhaseRow("01", "Foundation", "Weeks 1–4", true)
                PhaseRow("02", "Separation", "Weeks 5–8", false)
                PhaseRow("03", "Game Speed", "Weeks 9–12", false)
                PhaseRow("04", "Performance", "Weeks 13–16", false)
            }
        }
        item {
            Text("CONSISTENCY BEATS TALENT WHEN TALENT DOESN'T WORK.", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, detail: String, modifier: Modifier = Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = SurfaceDark), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(13.dp)) {
            Text(label, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(7.dp))
            Text(value, color = Orange, fontSize = 25.sp, fontWeight = FontWeight.Black)
            Text(detail, color = Color.White, fontSize = 11.sp)
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(title, color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
}

@Composable
private fun DarkCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(Modifier.padding(18.dp), content = content)
    }
}

@Composable
private fun PhaseRow(number: String, title: String, weeks: String, active: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(number, color = if (active) Orange else Muted, fontWeight = FontWeight.Black, fontSize = 18.sp)
        Spacer(Modifier.width(14.dp))
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(weeks, color = Muted, fontSize = 11.sp)
        }
        Spacer(Modifier.weight(1f))
        if (active) Text("CURRENT", color = Orange, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SimpleScreen(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("ELITE SHOT LAB", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Spacer(Modifier.height(12.dp))
        Text(title, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
        Spacer(Modifier.height(8.dp))
        Text(subtitle, color = Muted, fontSize = 15.sp)
        Spacer(Modifier.height(20.dp))
        Text("COMING IN THE NEXT BUILD", color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

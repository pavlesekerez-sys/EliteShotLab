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
    HOME("Home"), TRAINING("Workout"), CALENDAR("Calendar"),
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
                AppTab.TRAINING -> WorkoutScreen(Modifier.padding(padding))
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


private data class WorkoutExercise(
    val name: String,
    val target: String,
    val coachingTip: String
)

private data class WorkoutPlan(
    val title: String,
    val duration: String,
    val focus: String,
    val exercises: List<WorkoutExercise>
)

@Composable
private fun WorkoutScreen(modifier: Modifier = Modifier) {
    val workouts = listOf(
        WorkoutPlan(
            "Pull-up Mid-Range", "45–60 MIN", "Footwork • Balance • Shot consistency",
            listOf(
                WorkoutExercise("Dynamic warm-up", "5–7 min", "Move smoothly and prepare your ankles, hips and shoulders."),
                WorkoutExercise("Form shooting", "3 × 10 makes", "Start close to the basket; keep your elbow and follow-through consistent."),
                WorkoutExercise("1–2 pull-up footwork", "4 × 5 each side", "Practice stopping under control before every shot."),
                WorkoutExercise("Dribble into pull-up", "5 × 5 each side", "Stay balanced and land under control. Focus on quality, not speed."),
                WorkoutExercise("Free throws", "2 × 10 shots", "Use the same routine before every attempt.")
            )
        ),
        WorkoutPlan(
            "Ball Handling", "25–35 MIN", "Control • Both hands • Change of pace",
            listOf(
                WorkoutExercise("Stationary pound dribbles", "3 × 30 sec each hand", "Keep your eyes up and the ball below your waist."),
                WorkoutExercise("Crossovers", "3 × 20 reps", "Move the ball quickly while keeping your body balanced."),
                WorkoutExercise("Between-the-legs", "3 × 15 reps", "Stay low and make each transfer controlled."),
                WorkoutExercise("Moving change of pace", "4 × down and back", "Change speed after the move; keep control of the ball."),
                WorkoutExercise("Weak-hand control", "3 × 45 sec", "Give your weaker hand focused, controlled repetitions.")
            )
        ),
        WorkoutPlan(
            "Finishing at Rim", "30–40 MIN", "Touch • Both sides • Body control",
            listOf(
                WorkoutExercise("Mikan drill", "3 × 10 makes", "Alternate hands and finish softly off the glass."),
                WorkoutExercise("Right-hand layups", "3 × 8 makes", "Take a balanced approach and use the correct footwork."),
                WorkoutExercise("Left-hand layups", "3 × 8 makes", "Work patiently on your weaker side."),
                WorkoutExercise("Two-foot power finishes", "3 × 6 makes", "Gather securely, jump under control and finish high."),
                WorkoutExercise("Free throws", "2 × 10 shots", "Reset your routine and breathing between attempts.")
            )
        )
    )
    var selectedWorkoutIndex by remember { mutableIntStateOf(0) }
    val workout = workouts[selectedWorkoutIndex]
    val completed = remember(selectedWorkoutIndex) {
        mutableStateListOf<Boolean>().apply {
            repeat(workout.exercises.size) { add(false) }
        }
    }
    val completedCount = completed.count { it }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 28.dp)
    ) {
        item {
            Text("ELITE SHOT LAB", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(Modifier.height(8.dp))
            Text("YOUR WORKOUT", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(6.dp))
            Text("Choose a session and tick off each exercise as you complete it.", color = Muted, fontSize = 13.sp)
        }
        item {
            SectionTitle("CHOOSE YOUR SESSION")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("SHOOTING", "HANDLING", "FINISHING").forEachIndexed { index, label ->
                    FilterChip(
                        selected = selectedWorkoutIndex == index,
                        onClick = { selectedWorkoutIndex = index },
                        label = { Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF392416),
                            selectedLabelColor = Orange,
                            labelColor = Muted
                        )
                    )
                }
            }
        }
        item {
            DarkCard {
                Text(workout.title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(5.dp))
                Text(workout.focus, color = Muted, fontSize = 12.sp)
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(workout.duration, color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Text("$completedCount / ${workout.exercises.size} COMPLETED", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(10.dp))
                Box(
                    Modifier.fillMaxWidth().height(7.dp)
                        .background(Color(0xFF303239), RoundedCornerShape(8.dp))
                ) {
                    Box(
                        Modifier.fillMaxWidth(
                            if (workout.exercises.isEmpty()) 0f else completedCount.toFloat() / workout.exercises.size
                        ).fillMaxHeight()
                            .background(Orange, RoundedCornerShape(8.dp))
                    )
                }
                if (completedCount == workout.exercises.size) {
                    Spacer(Modifier.height(12.dp))
                    Text("SESSION COMPLETE — GREAT WORK!", color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        item { SectionTitle("EXERCISES") }
        items(workout.exercises.size) { index ->
            val exercise = workout.exercises[index]
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (completed[index]) Color(0xFF24251F) else SurfaceDark
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = completed[index],
                        onCheckedChange = { completed[index] = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Orange,
                            uncheckedColor = Muted,
                            checkmarkColor = Color.Black
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            exercise.name,
                            color = if (completed[index]) Muted else Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(exercise.target, color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(5.dp))
                        Text(exercise.coachingTip, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
                    }
                }
            }
        }
        item {
            OutlinedButton(
                onClick = { for (i in completed.indices) completed[i] = false },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("RESET SESSION")
            }
        }
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

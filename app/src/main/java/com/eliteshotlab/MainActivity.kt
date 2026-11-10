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
            "Shot Creation", "40–50 MIN", "Mid-range • Pull-ups • Balance • Repeatable mechanics",
            listOf(
                WorkoutExercise("Dynamic movement warm-up", "5 min", "Easy movement, ankle and hip mobility, then relaxed close-range shots."),
                WorkoutExercise("One-hand form shooting", "2 × 8 makes", "Start close. Keep the shooting elbow comfortably under the ball and finish with a relaxed follow-through."),
                WorkoutExercise("1-dribble elbow pull-up", "3 × 5 makes each side", "Attack one step to the elbow, stop balanced and rise without drifting."),
                WorkoutExercise("Cross-to-pull-up", "3 × 4 makes each direction", "Sell the crossover with your shoulders, gather cleanly and shoot from a balanced base."),
                WorkoutExercise("Hesitation pull-up", "3 × 4 makes each side", "Change pace first; keep the hesitation compact, then stop under control."),
                WorkoutExercise("Retreat dribble into space", "2 × 4 makes each side", "Create a little separation with a controlled retreat, re-square and shoot only when balanced."),
                WorkoutExercise("Side-step jumper", "2 × 4 makes each side", "Move laterally into space without fading away; land under control."),
                WorkoutExercise("Free throws with routine", "2 × 5 makes", "Use the same calm routine each time and track makes, not rushed attempts.")
            )
        ),
        WorkoutPlan(
            "Ball Handling", "35–45 MIN", "Tight control • Change of pace • Separation • Both hands",
            listOf(
                WorkoutExercise("Dynamic warm-up with the ball", "4 min", "Move around the court at an easy pace, switching hands and keeping your eyes up."),
                WorkoutExercise("Pound dribble series", "2 × 30 sec each hand", "Use your fingertips, a strong controlled bounce and a relaxed upper body."),
                WorkoutExercise("In-and-out to burst", "3 × 5 each hand", "Sell the direction with your shoulder; push into open space only after the fake."),
                WorkoutExercise("Crossover — low and wide", "3 × 8 each direction", "Move the ball across your body quickly while your hips and shoulders stay balanced."),
                WorkoutExercise("Between-legs to attack", "3 × 5 each direction", "Keep the transfer tight, then take your first step into the open lane."),
                WorkoutExercise("Behind-back protection", "2 × 6 each direction", "Keep the ball close to your hips and use the move to protect it, not just for style."),
                WorkoutExercise("Hesitation — slow to fast", "4 × down and back", "Approach under control, briefly change rhythm, then accelerate into space."),
                WorkoutExercise("Combo: cross → between → go", "3 × 4 each side", "Keep the combination compact. If control breaks, slow down and rebuild it."),
                WorkoutExercise("Cone/line 1-on-1 attack", "4 × 20 sec", "Imagine a defender at the marker: read the space, make one decisive move and get past the line.")
            )
        ),
        WorkoutPlan(
            "Finishing", "35–45 MIN", "Both hands • Angles • Soft touch • Finishing choices",
            listOf(
                WorkoutExercise("Dynamic warm-up", "5 min", "Prepare with easy movement and a few relaxed layups on both sides."),
                WorkoutExercise("Mikan series", "2 × 8 makes", "Alternate hands around the rim. Use soft touch and stay balanced."),
                WorkoutExercise("Weak-side layups", "3 × 5 makes each side", "Use the appropriate hand and footwork. Look at the target on the backboard."),
                WorkoutExercise("Reverse layups", "2 × 5 makes each side", "Use the rim as protection and finish softly on the far side."),
                WorkoutExercise("Stride-stop two-foot finish", "3 × 4 makes", "Gather under control, land balanced on two feet and finish without rushing."),
                WorkoutExercise("Floater from the lane", "3 × 5 makes", "Use a soft, high release from a comfortable distance before the defender arrives."),
                WorkoutExercise("Euro-step footwork progression", "2 × 4 each direction", "First rehearse the steps slowly without a shot; add the finish only when the footwork is controlled."),
                WorkoutExercise("Drive read: layup or floater", "3 × 4 each side", "Use a cone or a partner's stationary hand as a visual cue and choose the finish that fits the space."),
                WorkoutExercise("Free throws", "2 × 5 makes", "Take a short reset between attempts and keep the same routine.")
            )
        ),
        WorkoutPlan(
            "Footwork & Separation", "30–40 MIN", "Stops • Pivots • First step • Creating space",
            listOf(
                WorkoutExercise("Movement preparation", "5 min", "Easy skips, lateral steps and controlled deceleration."),
                WorkoutExercise("Jump stop and balance", "3 × 5 reps", "Land softly on two feet, knees comfortably bent, and hold your balance."),
                WorkoutExercise("Stride stop both directions", "3 × 5 reps", "Practice stopping from a light jog without extra steps or leaning."),
                WorkoutExercise("Front and reverse pivots", "2 × 6 each foot", "Keep the pivot foot planted and turn under control."),
                WorkoutExercise("Jab step to first step", "3 × 5 each side", "Make the jab believable, stay balanced and push into the open lane."),
                WorkoutExercise("V-cut into catch position", "3 × 5 reps", "Change direction sharply but under control, then show your hands ready."),
                WorkoutExercise("Retreat and re-attack", "3 × 4 each side", "Create space with a retreat dribble, reset your feet and attack a new angle."),
                WorkoutExercise("Footwork into pull-up", "2 × 4 each side", "Combine a clean stop with a balanced mid-range shot.")
            )
        ),
        WorkoutPlan(
            "Game Moves", "35–45 MIN", "Read the defender • Choose a move • Finish or pull-up",
            listOf(
                WorkoutExercise("Dynamic warm-up", "5 min", "Prepare with easy movement, ball control and close-range finishes."),
                WorkoutExercise("Attack from triple threat", "3 × 4 each side", "Start balanced, read the imaginary defender and choose a jab, drive or shot."),
                WorkoutExercise("Closeout read: drive or shoot", "3 × 5 reps", "Use a cone as a visual defender: attack open space or take a balanced shot."),
                WorkoutExercise("Crossover into rim finish", "3 × 4 each side", "Make one controlled move, get your shoulder past the marker and finish with the correct hand."),
                WorkoutExercise("Hesitation into pull-up", "3 × 4 each side", "Change pace, stop under control and rise vertically for the mid-range shot."),
                WorkoutExercise("Retreat dribble counter", "2 × 4 each side", "When the lane feels crowded, retreat to space and choose a new angle."),
                WorkoutExercise("Two-choice finishing", "3 × 4 reps", "Alternate a layup and a floater based on the space in front of the rim."),
                WorkoutExercise("Pressure free throws", "2 × 5 makes", "Take your time and use the same routine you use in practice.")
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
    val categoryLabels = listOf("SHOT", "HANDLE", "FINISH", "FOOTWORK", "GAME")

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
            Text("Five focused sessions to build skills that transfer to the game.", color = Muted, fontSize = 13.sp)
        }
        item {
            SectionTitle("CHOOSE YOUR SESSION")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                categoryLabels.forEachIndexed { index, label ->
                    FilterChip(
                        selected = selectedWorkoutIndex == index,
                        onClick = { selectedWorkoutIndex = index },
                        label = { Text(label, fontSize = 8.sp, fontWeight = FontWeight.Bold, maxLines = 1) },
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

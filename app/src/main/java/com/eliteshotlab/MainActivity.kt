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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import java.util.Calendar

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
                AppTab.CALENDAR -> CalendarScreen(Modifier.padding(padding))
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
    val id: String, val name: String, val target: String, val tip: String, val trackShots: Boolean = false
)
private data class WorkoutPlan(val id: String, val title: String, val subtitle: String, val exercises: List<WorkoutExercise>)

private fun workoutPlans() = listOf(
    WorkoutPlan("shooting", "SHOOTING", "Pull-up after change • Form shooting • Track every attempt", listOf(
        WorkoutExercise("back", "Offensive back change → pull-up", "20 shots", "Create separation with a convincing change, then stop balanced and rise.", true),
        WorkoutExercise("front", "Front change → pull-up", "20 shots", "Protect the ball through the front change and get into a balanced shot.", true),
        WorkoutExercise("middle", "Middle change → pull-up", "20 shots", "Shift the defender with the middle change; avoid drifting on the jumper.", true),
        WorkoutExercise("form", "Shooting form", "80 shots", "Work close to the basket on repeatable balance, release and follow-through.", true)
    )),
    WorkoutPlan("finishing", "FINISHING", "Six finishes • Both sides • Record your makes", listOf(
        WorkoutExercise("normal", "Change → normal finish", "Part of 40–50 total attempts", "Attack out of the change and finish naturally with the correct hand."),
        WorkoutExercise("reverse1", "Reverse 1", "Part of 40–50 total attempts", "Use your first reverse variation and finish with soft touch."),
        WorkoutExercise("reverse2", "Reverse 2", "Part of 40–50 total attempts", "Use your second reverse variation with controlled take-off."),
        WorkoutExercise("step", "Step through", "Part of 40–50 total attempts", "Rehearse the footwork slowly first, then finish in control."),
        WorkoutExercise("fake", "Finta pa zicer", "Part of 40–50 total attempts", "Sell the fake, stay balanced and finish with a simple layup."),
        WorkoutExercise("euro", "Eurostep", "Part of 40–50 total attempts", "Practise the footwork at a controlled speed and finish smoothly.")
    )),
    WorkoutPlan("handle", "BALL HANDLING", "Control • Separation • Change of pace • Read the defender", listOf(
        WorkoutExercise("pound", "Pound dribbles — both hands", "2 × 30 sec each hand", "Eyes up, fingertips active, control the bounce at different heights."),
        WorkoutExercise("cross", "Low, sharp crossover", "3 × 20 sec", "Shift hips and shoulders with the ball, then push into the new direction."),
        WorkoutExercise("inout", "In-and-out → acceleration", "3 × 6 each hand", "Sell the fake with your shoulder and eyes, then accelerate into space."),
        WorkoutExercise("between", "Between-the-legs → attack", "3 × 6 each direction", "Keep the transfer compact and use it to protect the ball."),
        WorkoutExercise("behind", "Behind-the-back protection", "3 × 6 each direction", "Keep the ball close and use the move to escape pressure."),
        WorkoutExercise("hesi", "Hesitation — slow to fast", "4 × down and back", "Approach under control, change rhythm briefly, then accelerate decisively."),
        WorkoutExercise("retreat", "Retreat dribble → re-attack", "3 × 5 each side", "Retreat when the lane closes, create room and attack a new angle."),
        WorkoutExercise("combo", "Crossover → between → go", "3 × 4 each side", "Keep the combination compact; one clean move is better than unnecessary tricks."),
        WorkoutExercise("cones", "Cone read — choose the counter", "4 short rounds", "Treat a cone as a defender: read the angle and choose the most effective move.")
    ))
)

private fun todayKey(): String {
    val c = Calendar.getInstance()
    return "%04d-%02d-%02d".format(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH))
}

@Composable
private fun WorkoutScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("elite_shot_lab_training", android.content.Context.MODE_PRIVATE) }
    val plans = remember { workoutPlans() }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var revision by remember { mutableIntStateOf(0) }
    val plan = plans[selectedIndex]
    val date = todayKey()
    val completed = plan.exercises.mapIndexed { i, _ -> prefs.getBoolean("check|" + date + "|" + plan.id + "|" + i, false) }
    val count = completed.count { it }
    val doneKey = "done|" + date + "|" + plan.id

    fun updateCheck(index: Int, value: Boolean) {
        prefs.edit().putBoolean("check|" + date + "|" + plan.id + "|" + index, value).apply()
        val allDone = plan.exercises.indices.all { i ->
            if (i == index) value else prefs.getBoolean("check|" + date + "|" + plan.id + "|" + i, false)
        }
        prefs.edit().putBoolean(doneKey, allDone).apply()
        revision++
    }

    LazyColumn(modifier.fillMaxSize().padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(13.dp), contentPadding = PaddingValues(top = 22.dp, bottom = 28.dp)) {
        item {
            Text("ELITE SHOT LAB", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(Modifier.height(7.dp))
            Text("EXTRA SKILL WORK", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(5.dp))
            Text("A focused add-on to your two individual practices — not another full training plan.", color = Muted, fontSize = 13.sp)
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("SHOOT", "FINISH", "HANDLE").forEachIndexed { i, label ->
                    FilterChip(selected = selectedIndex == i, onClick = { selectedIndex = i }, label = { Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold) }, modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFF392416), selectedLabelColor = Orange, labelColor = Muted))
                }
            }
        }
        item {
            DarkCard {
                Text(plan.title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(5.dp))
                Text(plan.subtitle, color = Muted, fontSize = 12.sp)
                Spacer(Modifier.height(13.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(count.toString() + " / " + plan.exercises.size + " DONE", color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    if (prefs.getBoolean(doneKey, false)) Text("SAVED TO CALENDAR", color = Orange, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(progress = { count.toFloat() / plan.exercises.size }, modifier = Modifier.fillMaxWidth().height(7.dp), color = Orange, trackColor = Color(0xFF303239))
            }
        }
        item { Text("TODAY'S SESSION", color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp) }
        items(plan.exercises.size) { index ->
            val ex = plan.exercises[index]
            val isChecked = completed[index]
            var makes by remember(date, plan.id, ex.id) { mutableStateOf(prefs.getString("makes|" + date + "|" + plan.id + "|" + ex.id, "") ?: "") }
            var attempts by remember(date, plan.id, ex.id) { mutableStateOf(prefs.getString("attempts|" + date + "|" + plan.id + "|" + ex.id, "") ?: "") }
            val made = makes.toIntOrNull()
            val tried = attempts.toIntOrNull()
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = if (isChecked) Color(0xFF24251F) else SurfaceDark), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isChecked, onCheckedChange = { updateCheck(index, it) }, colors = CheckboxDefaults.colors(checkedColor = Orange, uncheckedColor = Muted, checkmarkColor = Color.Black))
                        Column(Modifier.weight(1f)) {
                            Text(ex.name, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(ex.target, color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(ex.tip, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
                    if (ex.trackShots) {
                        Spacer(Modifier.height(9.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(value = makes, onValueChange = {
                                makes = it.filter(Char::isDigit)
                                prefs.edit().putString("makes|" + date + "|" + plan.id + "|" + ex.id, makes).apply()
                            }, modifier = Modifier.weight(1f), label = { Text("Pogoci") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Orange, focusedLabelColor = Orange))
                            OutlinedTextField(value = attempts, onValueChange = {
                                attempts = it.filter(Char::isDigit)
                                prefs.edit().putString("attempts|" + date + "|" + plan.id + "|" + ex.id, attempts).apply()
                            }, modifier = Modifier.weight(1f), label = { Text("Pokušaji") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Orange, focusedLabelColor = Orange))
                        }
                        val percent = if (made != null && tried != null && tried > 0 && made <= tried) made * 100f / tried else null
                        if (percent != null) Text("USPEŠNOST: " + "%.1f".format(percent) + "%", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item {
            OutlinedButton(onClick = {
                plan.exercises.indices.forEach { i -> prefs.edit().putBoolean("check|" + date + "|" + plan.id + "|" + i, false).apply() }
                prefs.edit().putBoolean(doneKey, false).apply()
                revision++
            }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = Orange)) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("RESET TODAY'S SESSION")
            }
        }
        item { Text("Pošto već imaš dva individualna treninga dnevno, prilagodi dodatni rad umoru i dogovoru sa trenerom.", color = Muted, fontSize = 11.sp, lineHeight = 16.sp) }
    }
}

@Composable
private fun CalendarScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("elite_shot_lab_training", android.content.Context.MODE_PRIVATE) }
    var monthOffset by remember { mutableIntStateOf(0) }
    val cal = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 1); add(Calendar.MONTH, monthOffset) }
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val days = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val offset = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val completedDates = prefs.all.keys.filter { it.startsWith("done|") && prefs.getBoolean(it, false) }.mapNotNull { it.split("|").getOrNull(1) }.toSet()
    Column(modifier.fillMaxSize().padding(18.dp)) {
        Text("ELITE SHOT LAB", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Spacer(Modifier.height(8.dp))
        Text("TRAINING CALENDAR", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black)
        Spacer(Modifier.height(16.dp))
        DarkCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { monthOffset-- }) { Icon(Icons.Default.ChevronLeft, null, tint = Orange) }
                Text(java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale.getDefault()).format(cal.time).uppercase(), color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                IconButton(onClick = { monthOffset++ }) { Icon(Icons.Default.ChevronRight, null, tint = Orange) }
            }
            Row(Modifier.fillMaxWidth()) {
                listOf("MON","TUE","WED","THU","FRI","SAT","SUN").forEach { Text(it, color = Muted, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f)) }
            }
            Spacer(Modifier.height(8.dp))
            val totalCells = ((offset + days + 6) / 7) * 7
            for (week in 0 until totalCells / 7) {
                Row(Modifier.fillMaxWidth()) {
                    for (col in 0..6) {
                        val day = week * 7 + col - offset + 1
                        val key = "%04d-%02d-%02d".format(year, month + 1, day)
                        val done = day in 1..days && completedDates.contains(key)
                        Box(Modifier.weight(1f).aspectRatio(1f).padding(3.dp).background(if (done) Orange.copy(alpha = 0.25f) else Color.Transparent, RoundedCornerShape(9.dp)), contentAlignment = Alignment.Center) {
                            if (day in 1..days) Text(day.toString(), color = if (done) Orange else Color.White, fontWeight = if (done) FontWeight.Black else FontWeight.Normal)
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(10.dp).background(Orange, RoundedCornerShape(3.dp)))
                Spacer(Modifier.width(7.dp))
                Text("Completed workout day", color = Muted, fontSize = 11.sp)
            }
        }
        Spacer(Modifier.height(14.dp))
        DarkCard {
            Text("TRAINING DAYS SAVED", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(completedDates.size.toString(), color = Orange, fontSize = 30.sp, fontWeight = FontWeight.Black)
            Text("A date is marked when you complete every exercise in a session.", color = Muted, fontSize = 11.sp)
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

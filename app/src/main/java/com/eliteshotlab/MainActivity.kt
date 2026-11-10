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
                AppTab.AI_COACH -> AICoachScreen(Modifier.padding(padding))
                AppTab.PROGRESS -> ProgressScreen(Modifier.padding(padding))
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
    val completed = remember(revision, selectedIndex, date) { plan.exercises.mapIndexed { i, _ -> prefs.getBoolean("check|" + date + "|" + plan.id + "|" + i, false) } }
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


private data class ProgressShot(val id: String, val label: String)
private val progressShots = listOf(
    ProgressShot("back", "Back change → pull-up"),
    ProgressShot("front", "Front change → pull-up"),
    ProgressShot("middle", "Middle change → pull-up"),
    ProgressShot("form", "Shooting form")
)

private fun progressDateKey(calendar: Calendar): String =
    "%04d-%02d-%02d".format(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))

@Composable
private fun ProgressScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("elite_shot_lab_training", android.content.Context.MODE_PRIVATE) }
    var period by remember { mutableStateOf("WEEK") }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var refresh by remember { mutableIntStateOf(0) }
    val today = Calendar.getInstance()
    val daysToShow = if (period == "WEEK") 7 else 30
    val start = (today.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, -(daysToShow - 1)) }
    val dateKeys = (0 until daysToShow).map { i ->
        (start.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, i) }.let { progressDateKey(it) }
    }
    val allKeys = prefs.all.keys
    val completedSessions = allKeys.filter { it.startsWith("done|") && prefs.getBoolean(it, false) }
    val sessionDates = completedSessions.mapNotNull { it.split("|").getOrNull(1) }.toSet()
    val periodSessions = sessionDates.count { it in dateKeys }
    val shotTotals = progressShots.associate { shot ->
        val makes = dateKeys.sumOf { date -> prefs.getString("makes|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0 }
        val attempts = dateKeys.sumOf { date -> prefs.getString("attempts|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0 }
        shot.id to (makes to attempts)
    }
    val totalMakes = shotTotals.values.sumOf { it.first }
    val totalAttempts = shotTotals.values.sumOf { it.second }
    val overallPercent = if (totalAttempts > 0) totalMakes * 100f / totalAttempts else 0f
    val finishChecks = allKeys.count { key ->
        key.startsWith("check|") && key.contains("|finishing|") &&
            key.split("|").getOrNull(1) in dateKeys && prefs.getBoolean(key, false)
    }
    val handleChecks = allKeys.count { key ->
        key.startsWith("check|") && key.contains("|handle|") &&
            key.split("|").getOrNull(1) in dateKeys && prefs.getBoolean(key, false)
    }
    val latestReadinessDate = allKeys.filter { it.startsWith("readiness|") }
        .mapNotNull { it.split("|").getOrNull(1) }.filter { it <= progressDateKey(today) }.maxOrNull()
    val latestEnergy = latestReadinessDate?.let { prefs.getInt("readiness|" + it + "|energy", -1) } ?: -1
    val latestSoreness = latestReadinessDate?.let { prefs.getInt("readiness|" + it + "|soreness", -1) } ?: -1
    val historyDates = (sessionDates + allKeys.filter { it.startsWith("makes|") || it.startsWith("attempts|") }
        .mapNotNull { it.split("|").getOrNull(1) }).distinct().sortedDescending().take(10)

    LazyColumn(
        modifier.fillMaxSize().padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        contentPadding = PaddingValues(top = 22.dp, bottom = 28.dp)
    ) {
        item {
            Text("ELITE SHOT LAB", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(Modifier.height(7.dp))
            Text("YOUR PROGRESS", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black)
            Text("Real numbers from the extra work you record.", color = Muted, fontSize = 12.sp)
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("WEEK", "MONTH").forEach { option ->
                    FilterChip(
                        selected = period == option,
                        onClick = { period = option; selectedDate = null; refresh++ },
                        label = { Text(if (option == "WEEK") "LAST 7 DAYS" else "LAST 30 DAYS", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFF392416), selectedLabelColor = Orange, labelColor = Muted)
                    )
                }
                IconButton(onClick = { refresh++ }) { Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Orange) }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                StatCard("SESSIONS", periodSessions.toString(), if (period == "WEEK") "Last 7 days" else "Last 30 days", Modifier.weight(1f))
                StatCard("SHOT %", if (totalAttempts > 0) "%.1f%%".format(overallPercent) else "—", totalMakes.toString() + " / " + totalAttempts + " made", Modifier.weight(1f))
                StatCard("ATTEMPTS", totalAttempts.toString(), "Recorded shots", Modifier.weight(1f))
            }
        }
        item {
            DarkCard {
                Text("SHOOTING TREND", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Pogoci i pokušaji po danu", color = Muted, fontSize = 11.sp)
                Spacer(Modifier.height(12.dp))
                val chartDates = if (period == "WEEK") dateKeys else dateKeys.filterIndexed { index, _ -> index % 3 == 0 || index == dateKeys.lastIndex }
                chartDates.forEach { date ->
                    val made = progressShots.sumOf { shot -> prefs.getString("makes|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0 }
                    val tried = progressShots.sumOf { shot -> prefs.getString("attempts|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0 }
                    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(date.takeLast(5), color = Muted, fontSize = 10.sp, modifier = Modifier.width(43.dp))
                        Box(Modifier.weight(1f).height(10.dp).background(Color(0xFF303239), RoundedCornerShape(6.dp))) {
                            if (tried > 0) Box(Modifier.fillMaxWidth((made.toFloat() / tried).coerceIn(0f, 1f)).fillMaxHeight().background(Orange, RoundedCornerShape(6.dp)))
                        }
                        Text(made.toString() + "/" + tried, color = Color.White, fontSize = 10.sp, modifier = Modifier.width(48.dp), textAlign = androidx.compose.ui.text.style.TextAlign.End)
                    }
                }
                Text("Traka prikazuje procenat uspešnosti; brojke desno su pogoci/pokušaji.", color = Muted, fontSize = 10.sp)
            }
        }
        item {
            DarkCard {
                Text("SHOOTING BY MOVE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
                progressShots.forEach { shot ->
                    val pair = shotTotals[shot.id] ?: (0 to 0)
                    val percent = if (pair.second > 0) pair.first * 100f / pair.second else 0f
                    Column(Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(shot.label, color = Color.White, fontSize = 12.sp, modifier = Modifier.weight(1f))
                            Text(if (pair.second > 0) "%.1f%%".format(percent) else "—", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(5.dp))
                        LinearProgressIndicator(progress = { (percent / 100f).coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth().height(6.dp), color = Orange, trackColor = Color(0xFF303239))
                        Text(pair.first.toString() + " pogodaka od " + pair.second + " pokušaja", color = Muted, fontSize = 10.sp)
                    }
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                StatCard("FINISHING", finishChecks.toString(), "Exercises checked", Modifier.weight(1f))
                StatCard("BALL HANDLING", handleChecks.toString(), "Exercises checked", Modifier.weight(1f))
            }
        }
        item {
            DarkCard {
                Text("LATEST READINESS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                if (latestReadinessDate != null && latestEnergy >= 0 && latestSoreness >= 0) {
                    Text(latestReadinessDate, color = Muted, fontSize = 11.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("Energy: " + latestEnergy + "/5     Muscle tightness: " + latestSoreness + "/5", color = Orange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                } else {
                    Text("Još nema sačuvanih provera spremnosti. Unesi dnevni check-in u AI Coach delu.", color = Muted, fontSize = 11.sp)
                }
            }
        }
        item { Text("TRAINING HISTORY", color = Orange, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp) }
        if (historyDates.isEmpty()) {
            item { Text("Još nema zabeleženih rezultata. Kada sačuvaš trening ili uneseš šuteve, pojaviće se ovde.", color = Muted, fontSize = 12.sp) }
        } else {
            items(historyDates.size) { index ->
                val date = historyDates[index]
                val donePlans = listOf("shooting", "finishing", "handle").filter { prefs.getBoolean("done|" + date + "|" + it, false) }
                val made = progressShots.sumOf { shot -> prefs.getString("makes|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0 }
                val tried = progressShots.sumOf { shot -> prefs.getString("attempts|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0 }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { selectedDate = if (selectedDate == date) null else date },
                    colors = CardDefaults.cardColors(containerColor = if (selectedDate == date) Color(0xFF30251F) else SurfaceDark),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(Modifier.padding(13.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(date, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text(donePlans.size.toString() + " completed", color = Orange, fontSize = 11.sp)
                            Icon(Icons.Default.ExpandMore, contentDescription = null, tint = Muted)
                        }
                        Text("Shooting: " + made + " / " + tried, color = Muted, fontSize = 11.sp)
                        if (selectedDate == date) {
                            Spacer(Modifier.height(8.dp))
                            Text("SESSION DETAILS", color = Orange, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            listOf("shooting" to "Shooting", "finishing" to "Finishing", "handle" to "Ball handling").forEach { pair ->
                                val done = prefs.getBoolean("done|" + date + "|" + pair.first, false)
                                Text(pair.second + ": " + if (done) "Completed" else "Not completed", color = if (done) Orange else Muted, fontSize = 11.sp)
                            }
                            progressShots.forEach { shot ->
                                val m = prefs.getString("makes|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0
                                val a = prefs.getString("attempts|" + date + "|shooting|" + shot.id, "")?.toIntOrNull() ?: 0
                                Text(shot.label + ": " + m + " / " + a, color = Muted, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
        item {
            Text("Statistika prikazuje samo podatke koje uneseš u Workout. Prazan rezultat znači da još nema unosa — ne računa se kao nula-procenat.", color = Muted, fontSize = 10.sp, lineHeight = 15.sp)
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


private data class CoachMessage(val text: String, val fromCoach: Boolean)

@Composable
private fun AICoachScreen(modifier: Modifier = Modifier) {
    var messages by remember { mutableStateOf(listOf(CoachMessage("Ćao! Ovde ćemo raditi na šutu, driblingu, završavanju i Game IQ-u. Pravi AI odgovor zahteva bezbedno povezivanje AI servisa — to još nije uključeno u ovu verziju.", true))) }
    var input by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("SHOOTING") }
    var energy by remember { mutableFloatStateOf(3f) }
    var soreness by remember { mutableFloatStateOf(2f) }
    val prompts = when(topic) {
        "SHOOTING" -> listOf("Kako da budem stabilniji na pull-up šutu?", "Kako da popravim izbačaj pod pritiskom?")
        "HANDLING" -> listOf("Kako da napravim više prostora odbrambenom igraču?", "Kako da čitam reakciju odbrane?")
        "FINISHING" -> listOf("Kako da završavam uz dobar balans?", "Kada da koristim reverse završnicu?")
        else -> listOf("Kako da prepoznam pomoć u odbrani?", "Kako da čitam pick-and-roll?")
    }
    LazyColumn(modifier.fillMaxSize().padding(horizontal=16.dp), verticalArrangement=Arrangement.spacedBy(12.dp), contentPadding=PaddingValues(top=20.dp,bottom=24.dp)) {
        item {
            Text("ELITE SHOT LAB",color=Orange,fontSize=12.sp,fontWeight=FontWeight.Bold,letterSpacing=2.sp)
            Text("AI COACH",color=Color.White,fontSize=27.sp,fontWeight=FontWeight.Black)
            Text("YOUR BASKETBALL DEVELOPMENT HUB",color=Muted,fontSize=10.sp)
        }
        item {
            DarkCard {
                Row(verticalAlignment=Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome,contentDescription=null,tint=Orange,modifier=Modifier.size(26.dp))
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("PERSONAL BASKETBALL COACH",color=Color.White,fontWeight=FontWeight.Bold,fontSize=14.sp)
                        Text("AI servis: potrebno povezivanje",color=Orange,fontSize=11.sp)
                    }
                }
            }
        }
        item {
            Text("CHOOSE YOUR FOCUS",color=Orange,fontSize=11.sp,fontWeight=FontWeight.Bold)
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(4.dp)) {
                listOf("SHOOTING","HANDLING","FINISHING","GAME IQ").forEach { t ->
                    FilterChip(selected=topic==t,onClick={topic=t},label={Text(t,fontSize=9.sp)},colors=FilterChipDefaults.filterChipColors(selectedContainerColor=Color(0xFF392416),selectedLabelColor=Orange,labelColor=Muted))
                }
            }
        }
        item {
            DarkCard {
                Text("QUESTIONS TO EXPLORE",color=Color.White,fontWeight=FontWeight.Bold,fontSize=14.sp)
                prompts.forEach { p -> OutlinedButton(onClick={input=p},modifier=Modifier.fillMaxWidth().padding(top=4.dp),colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White)) { Text(p,fontSize=12.sp) } }
            }
        }
        item {
            DarkCard {
                Text("DAILY READINESS CHECK",color=Color.White,fontWeight=FontWeight.Bold,fontSize=14.sp)
                Text("Uskladi dodatni rad sa svoja dva individualna treninga i oporavkom.",color=Muted,fontSize=12.sp)
                Spacer(Modifier.height(10.dp))
                Text("ENERGIJA • ${energy.toInt()}/5",color=Orange,fontSize=11.sp,fontWeight=FontWeight.Bold)
                Slider(value=energy,onValueChange={energy=it},valueRange=1f..5f,steps=3,colors=SliderDefaults.colors(thumbColor=Orange,activeTrackColor=Orange))
                Text("MIŠIĆNA ZATEGNUTOST • ${soreness.toInt()}/5",color=Orange,fontSize=11.sp,fontWeight=FontWeight.Bold)
                Slider(value=soreness,onValueChange={soreness=it},valueRange=1f..5f,steps=3,colors=SliderDefaults.colors(thumbColor=Orange,activeTrackColor=Orange))
                Text(if(energy<=2f || soreness>=4f) "Prednost daj oporavku i razgovaraj sa trenerom o opterećenju." else "Prati osećaj i dogovori dodatni rad sa trenerom.",color=Muted,fontSize=12.sp)
            }
        }
        item { Text("COACH CHAT",color=Orange,fontSize=11.sp,fontWeight=FontWeight.Bold) }
        items(messages.size) { i ->
            val m=messages[i]
            Row(Modifier.fillMaxWidth(),horizontalArrangement=if(m.fromCoach) Arrangement.Start else Arrangement.End) {
                Card(Modifier.fillMaxWidth(0.88f),colors=CardDefaults.cardColors(containerColor=if(m.fromCoach) SurfaceDark else Color(0xFF392416)),shape=RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(13.dp)) {
                        Text(if(m.fromCoach) "AI COACH" else "YOU",color=Orange,fontSize=9.sp,fontWeight=FontWeight.Bold)
                        Text(m.text,color=Color.White,fontSize=13.sp,lineHeight=19.sp)
                    }
                }
            }
        }
        item {
            OutlinedTextField(value=input,onValueChange={input=it},modifier=Modifier.fillMaxWidth(),placeholder={Text("Napiši pitanje za AI Coach...")},minLines=2,colors=OutlinedTextFieldDefaults.colors(focusedBorderColor=Orange))
            Button(onClick={
                if(input.isNotBlank()) {
                    messages=messages+CoachMessage(input.trim(),false)+CoachMessage("Poruka je zabeležena u ovom prikazu. Za stvaran odgovor potrebno je povezati AI servis preko bezbednog servera; nećemo stavljati tajni API ključ u APK.",true)
                    input=""
                }
            },modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Orange,contentColor=Color.Black)) {
                Icon(Icons.Default.Send,contentDescription=null)
                Spacer(Modifier.width(8.dp))
                Text("SEND")
            }
        }
    }
}

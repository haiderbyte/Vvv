package com.osamu.existentialjournal.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.osamu.existentialjournal.data.JournalDatabase
import com.osamu.existentialjournal.data.JournalEntry
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditEntryScreen(entryId: Long, onBack: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { JournalDatabase.getDatabase(context) }
    val dao = db.journalDao()

    var entry by remember { mutableStateOf<JournalEntry?>(null) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf(com.osamu.existentialjournal.data.MoodRepository.moods[1].label) }

    val backgroundGradient = androidx.compose.ui.graphics.Brush.verticalGradient(
        colors = listOf(Color.Black, Color(0xFF080808))
    )

    LaunchedEffect(entryId) {
        val fetchedEntry = dao.getEntryById(entryId)
        if (fetchedEntry != null) {
            entry = fetchedEntry
            title = fetchedEntry.title
            content = fetchedEntry.content
            selectedMood = fetchedEntry.mood
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(horizontal = 24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = Color.White.copy(alpha = 0.5f))
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        scope.launch {
                            entry?.let { dao.deleteEntry(it) }
                            onBack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "حذف", tint = Color.Red.copy(alpha = 0.6f))
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Button(
                        onClick = {
                            if (content.isNotBlank()) {
                                scope.launch {
                                    entry?.let {
                                        dao.updateEntry(
                                            it.copy(
                                                title = if (title.isBlank()) "بدون عنوان" else title,
                                                content = content,
                                                mood = selectedMood
                                            )
                                        )
                                    }
                                    onBack()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("تحديث", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Mood Selection
            Text(
                "الحالة الوجودية:",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                com.osamu.existentialjournal.data.MoodRepository.moods.forEach { mood ->
                    val isSelected = selectedMood == mood.label
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedMood = mood.label }
                            .padding(vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    if (isSelected) Color.White.copy(alpha = 0.1f) else Color.Transparent,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) Color.White.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.05f),
                                    RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                mood.icon,
                                contentDescription = mood.label,
                                tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            mood.label,
                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f),
                            fontSize = 10.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title Field
            BasicTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                ),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    if (title.isEmpty()) {
                        Text("عنوان الفكرة..", color = Color.White.copy(alpha = 0.1f), fontSize = 32.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Serif)
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Content Field
            BasicTextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp),
                textStyle = TextStyle(
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 20.sp,
                    lineHeight = 32.sp
                ),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    if (content.isEmpty()) {
                        Text("اكتب ما يجول في خاطرك هنا..", color = Color.White.copy(alpha = 0.1f), fontSize = 20.sp)
                    }
                    innerTextField()
                }
            )
        }
    }
}

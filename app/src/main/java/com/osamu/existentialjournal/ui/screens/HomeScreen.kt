package com.osamu.existentialjournal.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Brightness3
import androidx.compose.material.icons.outlined.CompassCalibration
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Waves
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.osamu.existentialjournal.data.JournalDatabase
import com.osamu.existentialjournal.data.JournalEntry
import com.osamu.existentialjournal.data.QuoteRepository
import com.osamu.existentialjournal.data.MoodRepository
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode

@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEntryClick: (Long) -> Unit
) {
    val context = LocalContext.current
    val db = remember { JournalDatabase.getDatabase(context) }
    val dao = db.journalDao()
    
    val entries by dao.getAllEntries().collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredEntries = remember(searchQuery, entries) {
        if (searchQuery.isEmpty()) {
            entries
        } else {
            entries.filter { 
                it.title.contains(searchQuery, ignoreCase = true) || 
                it.content.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val quote = remember { QuoteRepository.getDailyQuote() }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color.Black, Color(0xFF080808)),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Status Bar Look (Immersive UI)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date()),
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(Color(0xFF10B981), RoundedCornerShape(50.dp)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AMOLED MODE ACTIVE",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                // Branded Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(80.dp),
                        color = Color.White.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.1f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Ω",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraLight,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "مفكرة الوجود",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = (-1).sp
                    )
                }

                // Header Action Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "الإعدادات",
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Daily Quote Card (Immersive Style)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    color = Color.Transparent, // Using background for gradient
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.1f))
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.04f), Color.Transparent)
                                )
                            )
                            .padding(24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "فلسفة اليوم",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        Text(
                            text = "“${quote.text}”",
                            color = Color(0xFFE0E0E0),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            lineHeight = 28.sp,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "— ${quote.author}",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }
                }

                // Search Bar
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    color = Color.White.copy(alpha = 0.03f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.07f))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.3f),
                            modifier = Modifier.size(18.dp)
                        )
                        
                        BasicTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif
                            ),
                            cursorBrush = SolidColor(Color.White),
                            decorationBox = { innerTextField ->
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "ابحث في أغوار أفكارك..",
                                        color = Color.White.copy(alpha = 0.2f),
                                        fontSize = 15.sp,
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                                innerTextField()
                            }
                        )

                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "مسح البحث",
                                tint = Color.White.copy(alpha = 0.3f),
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { searchQuery = "" }
                            )
                        }
                    }
                }

                // List Section Title
                Text(
                    text = "التدوينات الأخيرة",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // List of Entries
                Box(modifier = Modifier.weight(1f)) {
                    if (filteredEntries.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (searchQuery.isEmpty()) "لا توجد تدفّقات فكرية بعد.." else "لا توجد نتائج تطابق بحثك..",
                                color = Color.White.copy(alpha = 0.2f),
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Serif,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 120.dp),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            items(filteredEntries) { entry ->
                                Box(modifier = Modifier.clickable { onEntryClick(entry.id) }) {
                                    JournalEntryItem(entry)
                                }
                                Divider(color = Color.White.copy(alpha = 0.05f), thickness = 0.5.dp)
                            }
                        }
                    }

                    // FAB (Immersive White FAB)
                    FloatingActionButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .align(Alignment.BottomStart) // Matches React left alignment
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .padding(bottom = 24.dp, start = 0.dp)
                            .size(64.dp),
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "أضف تدوينة", modifier = Modifier.size(28.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun JournalEntryItem(entry: JournalEntry) {
    val date = remember(entry.timestamp) {
        SimpleDateFormat("dd MMMM", Locale("ar")).format(Date(entry.timestamp))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = date,
                color = Color.White.copy(alpha = 0.3f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
            
            Surface(
                color = Color.White.copy(alpha = 0.02f),
                shape = RoundedCornerShape(100.dp),
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.05f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val mood = MoodRepository.getMoodByLabel(entry.mood)
                    val moodIcon = mood?.icon ?: Icons.Default.Add
                    
                    Icon(
                        imageVector = moodIcon,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.4f),
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        text = entry.mood,
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = entry.title,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            letterSpacing = (-0.5).sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = entry.content,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 15.sp,
            maxLines = 3,
            lineHeight = 24.sp,
            textAlign = TextAlign.Justify,
            fontWeight = FontWeight.Light
        )
    }
}

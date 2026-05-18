package com.osamu.existentialjournal.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.foundation.selection.toggleable
import com.osamu.existentialjournal.ui.ReminderManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val reminderManager = remember { ReminderManager(context) }
    var remindersEnabled by remember { mutableStateOf(reminderManager.isEnabled()) }

    val backgroundGradient = androidx.compose.ui.graphics.Brush.verticalGradient(
        colors = listOf(Color.Black, Color(0xFF080808))
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
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = Color.White.copy(alpha = 0.5f))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "الإعدادات",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Reminders Section
            Text(
                text = "التذكير اليومي",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = remindersEnabled,
                        onValueChange = { 
                            remindersEnabled = it
                            if (it) {
                                reminderManager.scheduleReminder(20, 0) // Default 8:00 PM
                            } else {
                                reminderManager.cancelReminder()
                            }
                        },
                        role = Role.Switch
                    ),
                color = Color.White.copy(alpha = 0.02f),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "تفعيل التذكير",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "استلم تنبيهاً يومياً في الساعة 8:00 مساءً للتدوين.",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Switch(
                        checked = remindersEnabled,
                        onCheckedChange = null, // Handled by toggleable Row
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color.White.copy(alpha = 0.2f),
                            uncheckedThumbColor = Color.White.copy(alpha = 0.2f),
                            uncheckedTrackColor = Color.Transparent,
                            uncheckedBorderColor = Color.White.copy(alpha = 0.1f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Developer Content Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White.copy(alpha = 0.02f),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(32.dp)) {
                    Text(
                        text = "حول المطور",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    Text(
                        text = "المطور: محمد حيدر (أوسامو دزاي) - مطور برمجيات أندرويد ومحاسب ذكي يجمع بين دقة الأرقام الإدارية وإبداع الأكواد البرمجية. مؤسس ومشرف قناة \"لم يعد إنساناً\" الوجودية.",
                        color = Color.White,
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        textAlign = TextAlign.Justify,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "تم بناء هذا التطبيق ليكون ملاذاً فكرياً نقياً، بعيداً عن صخب الإنترنت وضجيج الواجهات المزدحمة، ومصمم خصيصاً لمن يقدسون الهدوء والعمق والتأمل.",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // App Version
            Text(
                text = "إصدار 1.0.0 — مفكرة الوجود",
                color = Color.White.copy(alpha = 0.2f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

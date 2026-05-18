package com.osamu.existentialjournal.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(onBack: () -> Unit) {
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
                        text = "محمد حيدر (أوسامو دزاي)",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    
                    Text(
                        text = "مطور برمجيات أندرويد ومحاسب ذكي يجمع بين دقة الأرقام الإدارية وإبداع الأكواد البرمجية. مطور خبير في نظام أندرويد وخاصة في تقنيات Jetpack Compose و Room Database، مهتم بأتمتة الأنظمة وتطوير البرمجيات المحلية (Local-first) التي تحترم خصوصية المستخدم وأداء الأجهزة.",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 16.sp,
                        lineHeight = 28.sp,
                        textAlign = TextAlign.Justify
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "مؤسس ومشرف على مجتمع وقناة \"لم يعد إنساناً\" (No Longer Human) الوجودية. تم بناء هذا التطبيق ليكون ملاذاً فكرياً نقياً، بعيداً عن صخب الإنترنت وضجيج الواجهات المزدحمة، ومصمم خصيصاً لمن يقدسون الهدوء والعمق والتأمل.",
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

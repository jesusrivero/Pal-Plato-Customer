package com.techcode.palplato.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun AppConfirmDialog(
	title: String,
	message: String,
	confirmText: String = "Confirmar",
	cancelText: String = "Cancelar",
	onConfirm: () -> Unit,
	onDismiss: () -> Unit
) {
	AlertDialog(
		onDismissRequest = { onDismiss() },
		title = {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Icon(
					imageVector = Icons.Default.Warning,
					contentDescription = null,
					tint = Color(0xFFFFA000),
					modifier = Modifier.size(28.dp)
				)
				Spacer(modifier = Modifier.width(8.dp))
				Text(
					text = title,
					fontSize = 20.sp,
					fontWeight = FontWeight.Bold,
					color = Color(0xFF111827)
				)
			}
		},
		text = {
			Text(
				text = message,
				fontSize = 16.sp,
				color = Color(0xFF374151)
			)
		},
		confirmButton = {
			TextButton(
				onClick = {
					onConfirm()
					onDismiss()
				}
			) {
				Text(confirmText, color = Color(0xFF2563EB))
			}
		},
		dismissButton = {
			TextButton(onClick = { onDismiss() }) {
				Text(cancelText, color = Color.Gray)
			}
		},
		modifier = Modifier
			.clip(RoundedCornerShape(16.dp))
			.background(Color.Transparent),
		containerColor = Color.White,
		tonalElevation = 12.dp,
		shape = RoundedCornerShape(16.dp)
	)
}

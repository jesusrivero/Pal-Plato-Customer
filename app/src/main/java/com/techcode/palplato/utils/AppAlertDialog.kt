package com.techcode.palplato.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AppAlertDialog(
	state: Resource<*>?,       // Estado del recurso (Success, Error, Loading)
	onDismiss: () -> Unit      // Acción al cerrar el diálogo
) {
	if (state is Resource.Success || state is Resource.Error) {
		val isSuccess = state is Resource.Success
		val title = if (isSuccess) "Éxito" else "Error"
		val message = if (isSuccess) "Operación realizada correctamente" else (state as Resource.Error).message
		
		// Autocerrar después de 2 segundos
		LaunchedEffect(state) {
			delay(2000)
			onDismiss()
		}
		
		AlertDialog(
			onDismissRequest = { onDismiss() },
			title = {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Icon(
						imageVector = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Error,
						contentDescription = null,
						tint = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFE53935),
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
			confirmButton = {},
			modifier = Modifier
				.clip(RoundedCornerShape(16.dp))
				.background(Color.Transparent),
			containerColor = Color.White, // Fondo blanco profesional
			tonalElevation = 12.dp,
			shape = RoundedCornerShape(16.dp)
		)
	}
}



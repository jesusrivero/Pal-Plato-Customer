package com.techcode.palplato.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun NoInternetScreen(onRetry: () -> Unit) { // ✅ función normal
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			painter = painterResource(id = com.techcode.palplato.R.drawable.ic_logo),
			contentDescription = "Sin conexión",
			tint = Color.Gray,
			modifier = Modifier.size(80.dp)
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		Text(
			text = "Sin conexión a Internet",
			style = MaterialTheme.typography.titleMedium,
			color = Color.Gray
		)
		
		Spacer(modifier = Modifier.height(8.dp))
		
		Button(onClick = { onRetry() }) { // ✅ llamar la lambda dentro de llaves
			Text("Reintentar")
		}
	}
}


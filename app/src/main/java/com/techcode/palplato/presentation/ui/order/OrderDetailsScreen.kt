package com.techcode.palplato.presentation.ui.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun OrderDetailsScreen(	navController: NavController){
	
	OrderDetailsScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreenContent(navController: NavController) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Detalles del Pedido",
						style = MaterialTheme.typography.titleMedium
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
					}
				},
				actions = {
					IconButton(onClick = { /* Acción notificaciones */ }) {
						Icon(
							painter = painterResource(id = R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(16.dp)
		) {
			Text("Cliente: Sofía Ramírez", style = MaterialTheme.typography.bodyLarge)
			Text("Pedido #12345", color = Color.Gray)
			
			Spacer(modifier = Modifier.height(16.dp))
			
			Text("Dirección de entrega", style = MaterialTheme.typography.labelMedium)
			Text("123 Calle Principal, Caracas", color = Color.Gray)
			
			Spacer(modifier = Modifier.height(8.dp))
			
			Text("Hora de entrega", style = MaterialTheme.typography.labelMedium)
			Text("15:30", color = Color.Gray)
			
			Spacer(modifier = Modifier.height(8.dp))
			
			Text("Método de pago", style = MaterialTheme.typography.labelMedium)
			Text("Pago Movil", color = Color(0xFF0099CC))
			
			Spacer(modifier = Modifier.height(16.dp))
			
			Text("Artículos", style = MaterialTheme.typography.titleSmall,fontSize = 25.sp)
			Spacer(modifier = Modifier.height(8.dp))

// Hamburguesa Clásica con descripción
			Column(modifier = Modifier.padding(bottom = 8.dp)) {
				Text("Hamburguesa Clásica x2", style = MaterialTheme.typography.bodyMedium,fontSize = 15.sp)
				Text("Sin cebolla, extra queso", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
			}

// Papas Fritas Grandes sin personalización
			Column(modifier = Modifier.padding(bottom = 8.dp)) {
				Text("Papas Fritas Grandes x1", style = MaterialTheme.typography.bodyMedium, fontSize = 15.sp)
			}

// Refresco con descripción opcional
			Column(modifier = Modifier.padding(bottom = 8.dp)) {
				Text("Refresco de Cola x1", style = MaterialTheme.typography.bodyMedium, fontSize = 15.sp)
				Text("Sin hielo", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
			}
			Spacer(modifier = Modifier.height(16.dp))
			
			Text("Resumen", style = MaterialTheme.typography.titleSmall)
			Spacer(modifier = Modifier.height(8.dp))
			
			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
				Text("Subtotal", color = Color.Gray)
				Text("$15.00")
			}
//			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//				Text("Impuestos", color = Color.Gray)
//				Text("$1.50")
//			}
			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
				Text("Tarifa de entrega", color = Color(0xFF0099CC))
				Text("$2.00")
			}
			Divider(modifier = Modifier.padding(vertical = 8.dp))
			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
				Text("Total", fontWeight = FontWeight.Bold)
				Text("$17.00", fontWeight = FontWeight.Bold)
			}
			
			Spacer(modifier = Modifier.height(24.dp))
			
			// Botones Aceptar y Rechazar
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				Button(
					onClick = { /* Acción rechazar */ },
					colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)), // Naranja
					shape = RoundedCornerShape(12.dp)
				) {
					Text("Rechazar", color = Color.White)
				}
				Button(
					onClick = { /* Acción aceptar */ },
					colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A)), // Verde
					shape = RoundedCornerShape(12.dp)
				) {
					Text("Aceptar pedido", color = Color.White)
				}
			}
		}
	}
}


//@Preview(showBackground = true)
//@Composable
//fun OrderDetailsSceenPreview() {
//
//	val navController = rememberNavController()
//	OrderDetailsScreenContent(navController = navController)
//}
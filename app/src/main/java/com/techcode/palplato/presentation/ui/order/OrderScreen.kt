package com.techcode.palplato.presentation.ui.order

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun OrderScreen(	navController: NavController){
	
	OrderScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreenContent(navController: NavController) {
	val pedidos = listOf(
		Pedido("Alejandro", "#1234", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Sofía", "#5678", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Ricardo", "#9012", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Isabella", "#3456", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Gabriel", "#7890", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Alejandro", "#1234", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Sofía", "#5678", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Ricardo", "#9012", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Isabella", "#3456", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Gabriel", "#7890", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Alejandro", "#1234", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Sofía", "#5678", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Ricardo", "#9012", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Isabella", "#3456", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Gabriel", "#7890", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Alejandro", "#1234", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Sofía", "#5678", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Ricardo", "#9012", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Isabella", "#3456", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Gabriel", "#7890", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Alejandro", "#1234", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Sofía", "#5678", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Ricardo", "#9012", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Isabella", "#3456", com.techcode.palplato.R.drawable.ic_hamburguesa),
		Pedido("Gabriel", "#7890", com.techcode.palplato.R.drawable.ic_hamburguesa),
	)
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Pedidos", style = MaterialTheme.typography.titleMedium) },
				actions = {
					IconButton(onClick = { /* Acción de notificaciones */ }) {
						Icon(
							painter = painterResource(id = R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 16.dp, vertical = 8.dp)
		) {
			items(pedidos) { pedido ->
				PedidoItem(pedido) {
			navController.navigate(AppRoutes.OrderDetailsScreen)
				}
				Spacer(modifier = Modifier.height(12.dp))
			}
		}
	}
}


@Composable
fun PedidoItem(pedido: Pedido, onClick: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(16.dp))
			.clickable { onClick() }
			.padding(8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			painter = painterResource(id = pedido.imagen),
			contentDescription = "Avatar del cliente",
			modifier = Modifier
				.size(48.dp)
				.clip(CircleShape)
		)
		
		Spacer(modifier = Modifier.width(12.dp))
		
		Column(
			modifier = Modifier
				.weight(1f)
		) {
			Text(
				text = "Cliente: ${pedido.cliente}",
				fontWeight = FontWeight.SemiBold,
				style = MaterialTheme.typography.bodyMedium
			)
			Text(
				text = "Pedido ${pedido.detalle}",
				color = Color.Gray,
				style = MaterialTheme.typography.bodySmall
			)
		}
	}
}

data class Pedido(
	val cliente: String,
	val detalle: String,
	@DrawableRes val imagen: Int
)




@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
	
	val navController = rememberNavController()
	OrderScreenContent(navController = navController)
}
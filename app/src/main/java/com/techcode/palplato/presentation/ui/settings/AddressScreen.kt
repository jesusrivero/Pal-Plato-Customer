package com.techcode.palplato.presentation.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AddressScreen(navController: NavController){
	AddressScreenContent(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreenContent(navController: NavController) {
	val addressList = remember {
		mutableStateListOf(
			AddressItem(
				id = 1,
				title = "Casa",
				address = "Calle 123 #45-67, Chapinero, Bogotá D.C.",
				isSelected = true
			),
			AddressItem(
				id = 2,
				title = "Trabajo",
				address = "Carrera 15 #93-45, Oficina 302, Zona Rosa, Bogotá D.C.",
				isSelected = false
			),
			AddressItem(
				id = 3,
				title = "Casa de Mamá",
				address = "Transversal 25 #18-32, 3rr3Hola, Bogotá D.C.",
				isSelected = false
			)
		)
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						"Mis Direcciones",
						style = MaterialTheme.typography.titleLarge,
						fontWeight = FontWeight.Bold
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.Default.ArrowBack,
							contentDescription = "Atrás",
							modifier = Modifier.size(24.dp)
						)
					}
				},
				actions = {
					IconButton(onClick = { /* Notificaciones */ }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(24.dp)
						)
					}
				}
			)
		},
		bottomBar = {
			Surface(
				modifier = Modifier
					.fillMaxWidth()
					.padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
				color = MaterialTheme.colorScheme.surface,
				shadowElevation = 8.dp
			) {
				Column(
					modifier = Modifier
						.padding(16.dp)
				) {
					// Solo un botón: Agregar nueva dirección
					AddNewAddressButton(
						onClick = {
							navController.navigate("add_new_address")
						}
					)
				}
			}
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 16.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			item {
				Spacer(Modifier.height(16.dp))
				Text(
					text = "Selecciona una dirección",
					style = MaterialTheme.typography.headlineSmall,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.onSurface,
					modifier = Modifier.padding(bottom = 12.dp)
				)
			}
			
			items(addressList) { address ->
				AddressCard(
					address = address,
					onEditClick = {
						navController.navigate("edit_address/${address.id}")
					},
					onSelectAddress = {
						addressList.replaceAll { it.copy(isSelected = it.id == address.id) }
						navController.popBackStack() // ← Vuelve a la pantalla anterior
					}
				)
			}
			
			item { Spacer(modifier = Modifier.height(24.dp)) }
		}
	}
}

@Composable
fun AddressCard(
	address: AddressItem,
	onEditClick: () -> Unit,
	onSelectAddress: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onSelectAddress() },
		elevation = CardDefaults.cardElevation(
			defaultElevation = if (address.isSelected) 6.dp else 2.dp
		),
		colors = CardDefaults.cardColors(
			containerColor = if (address.isSelected)
				MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
			else
				MaterialTheme.colorScheme.surface
		),
		shape = RoundedCornerShape(12.dp),
		border = if (address.isSelected)
			BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
		else null
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Card(
				modifier = Modifier.size(48.dp),
				colors = CardDefaults.cardColors(
					containerColor = if (address.isSelected)
						MaterialTheme.colorScheme.primary
					else
						MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
				),
				shape = RoundedCornerShape(12.dp)
			) {
				Box(
					modifier = Modifier.fillMaxSize(),
					contentAlignment = Alignment.Center
				) {
					Icon(
						painter = painterResource(id = com.techcode.palplato.R.drawable.ic_home),
						contentDescription = "Dirección",
						modifier = Modifier.size(24.dp),
						tint = if (address.isSelected)
							MaterialTheme.colorScheme.onPrimary
						else
							MaterialTheme.colorScheme.primary
					)
				}
			}
			
			Spacer(modifier = Modifier.width(16.dp))
			
			Column(
				modifier = Modifier.weight(1f)
			) {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(
						text = address.title,
						style = MaterialTheme.typography.titleMedium,
						fontWeight = FontWeight.Bold,
						color = if (address.isSelected)
							MaterialTheme.colorScheme.primary
						else
							MaterialTheme.colorScheme.onSurface
					)
					
					if (address.isSelected) {
						Spacer(modifier = Modifier.width(8.dp))
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_add),
							contentDescription = "Seleccionada",
							modifier = Modifier.size(16.dp),
							tint = MaterialTheme.colorScheme.primary
						)
					}
				}
				
				Spacer(modifier = Modifier.height(4.dp))
				
				Text(
					text = address.address,
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					lineHeight = 20.sp
				)
			}
			
			Spacer(modifier = Modifier.width(8.dp))
			
			IconButton(
				onClick = onEditClick,
				modifier = Modifier.size(40.dp)
			) {
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_phone),
					contentDescription = "Editar dirección",
					modifier = Modifier.size(20.dp),
					tint = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
	}
}

@Composable
fun AddNewAddressButton(onClick: () -> Unit) {
	Button(
		onClick = onClick,
		modifier = Modifier.fillMaxWidth(),
		shape = RoundedCornerShape(8.dp),
		colors = ButtonDefaults.buttonColors(
			containerColor = MaterialTheme.colorScheme.primary
		)
	) {
		Icon(
			painter = painterResource(id = com.techcode.palplato.R.drawable.ic_add),
			contentDescription = "Agregar",
			modifier = Modifier
				.size(20.dp)
				.padding(end = 8.dp),
			tint = MaterialTheme.colorScheme.onPrimary
		)
		
		Text(
			text = "Agregar Nueva Dirección",
			style = MaterialTheme.typography.titleMedium,
			modifier = Modifier.padding(vertical = 4.dp),
			color = MaterialTheme.colorScheme.onPrimary
		)
	}
}

data class AddressItem(
	val id: Int,
	val title: String,
	val address: String,
	val isSelected: Boolean = false
)

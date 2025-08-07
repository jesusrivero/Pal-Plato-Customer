package com.techcode.palplato.presentation.ui.bussines.shopping

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techcode.palplato.domain.viewmodels.auth.CartViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun ShoppingScreen(navController: NavController) {
	CheckoutVerificationScreen(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutVerificationScreen(
	navController: NavController,
	viewModel: CartViewModel = hiltViewModel() // ⬅️ Inyectamos el ViewModel del carrito
) {
	val subtotal by viewModel.subtotal.collectAsState()
	val deliveryFee by viewModel.deliveryCost.collectAsState()
	val total by viewModel.total.collectAsState()
	
	var deliveryInstructions by remember { mutableStateOf("") }
	var paymentProofUri by remember { mutableStateOf<Uri?>(null) }
	
	// Launcher para seleccionar imagen
	val imagePickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetContent()
	) { uri: Uri? ->
		paymentProofUri = uri
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text("Verificar Pedido", style = MaterialTheme.typography.titleMedium)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.Default.ArrowBack,
							contentDescription = "Atrás",
							modifier = Modifier.size(24.dp)
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
				.verticalScroll(rememberScrollState())
		) {
			Spacer(Modifier.height(16.dp))
			
			Text(
				text = "Dirección de Entrega",
				style = MaterialTheme.typography.headlineSmall,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.padding(bottom = 16.dp)
			)
			
			DeliveryAddressCard(
				onAddAddressClick = {
					navController.navigate(AppRoutes.AddressScreen)
				}
			)
			
			Spacer(modifier = Modifier.height(24.dp))
			
			Text(
				text = "Instrucciones de Entrega",
				style = MaterialTheme.typography.titleMedium,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.padding(bottom = 8.dp)
			)
			
			OutlinedTextField(
				value = deliveryInstructions,
				onValueChange = { deliveryInstructions = it },
				placeholder = {
					Text(
						"Ej: Casa blanca, portón negro, tocar el timbre dos veces...",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				},
				modifier = Modifier
					.fillMaxWidth()
					.height(120.dp),
				textStyle = MaterialTheme.typography.bodyMedium,
				colors = OutlinedTextFieldDefaults.colors(
					focusedBorderColor = MaterialTheme.colorScheme.primary,
					unfocusedBorderColor = MaterialTheme.colorScheme.outline
				),
				shape = RoundedCornerShape(8.dp),
				maxLines = 4
			)
			
			Spacer(modifier = Modifier.height(24.dp))
			
			// Componente para agregar comprobante de pago
			PaymentProofUploader(
				selectedImageUri = paymentProofUri,
				onUploadClick = {
					imagePickerLauncher.launch("image/*")
				},
				onRemoveImage = {
					paymentProofUri = null
				}
			)
			
			Spacer(modifier = Modifier.height(24.dp))
			
			// Resumen del pedido (Dinámico con el carrito)
			OrderSummaryCard(
				subtotal = subtotal,
				deliveryFee = deliveryFee,
				total = total
			)
			
			Spacer(modifier = Modifier.height(24.dp))
			
			Button(
				onClick = { navController.navigate("order_confirmation") },
				modifier = Modifier
					.fillMaxWidth()
					.height(56.dp),
				shape = RoundedCornerShape(12.dp),
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.primary
				)
			) {
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_phone),
					contentDescription = null,
					modifier = Modifier.size(20.dp),
					tint = MaterialTheme.colorScheme.onPrimary
				)
				Spacer(modifier = Modifier.width(8.dp))
				Text(
					text = "Realizar Pedido - $${String.format("%.2f", total)}",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold
				)
			}
			
			Spacer(modifier = Modifier.height(32.dp))
		}
	}
}


@Composable
fun DeliveryAddressCard(
	onAddAddressClick: () -> Unit
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		shape = RoundedCornerShape(12.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable { onAddAddressClick() }
				.padding(20.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			// Icono de agregar
			Card(
				modifier = Modifier.size(48.dp),
				colors = CardDefaults.cardColors(
					containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
				),
				shape = RoundedCornerShape(12.dp)
			) {
				Box(
					modifier = Modifier.fillMaxSize(),
					contentAlignment = Alignment.Center
				) {
					Icon(
						painter = painterResource(id = com.techcode.palplato.R.drawable.ic_add),
						contentDescription = "Agregar dirección",
						modifier = Modifier.size(24.dp),
						tint = MaterialTheme.colorScheme.primary
					)
				}
			}
			
			Spacer(modifier = Modifier.width(16.dp))
			
			// Textos
			Column(
				modifier = Modifier.weight(1f)
			) {
				Text(
					text = "No se agregó dirección",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurface
				)
				Text(
					text = "Agregar una dirección",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.primary
				)
			}
			
			// Flecha
			Icon(
				painter = painterResource(id = com.techcode.palplato.R.drawable.ic_direction),
				contentDescription = "Ir a agregar dirección",
				modifier = Modifier.size(20.dp),
				tint = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}

@Composable
fun OrderSummaryCard(
	subtotal: Double,
	deliveryFee: Double,
	total: Double
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		shape = RoundedCornerShape(12.dp)
	) {
		Column(
			modifier = Modifier.padding(20.dp)
		) {
			// Título
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = "Resumen del Pedido",
					style = MaterialTheme.typography.titleLarge,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.onSurface
				)
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_phone),
					contentDescription = "Resumen",
					modifier = Modifier.size(24.dp),
					tint = MaterialTheme.colorScheme.primary
				)
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			// Subtotal
			SummaryRow(
				label = "Subtotal",
				value = "$${String.format("%.2f", subtotal)}",
				isTotal = false
			)
			
			Spacer(modifier = Modifier.height(8.dp))
			
			// Delivery
			SummaryRow(
				label = "Costo de Delivery",
				value = "$${String.format("%.2f", deliveryFee)}",
				isTotal = false
			)
			
			Spacer(modifier = Modifier.height(12.dp))
			
			// Divider
			Divider(
				color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
				thickness = 1.dp
			)
			
			Spacer(modifier = Modifier.height(12.dp))
			
			// Total
			SummaryRow(
				label = "Total a Pagar",
				value = "$${String.format("%.2f", total)}",
				isTotal = true
			)
		}
	}
}

@Composable
fun SummaryRow(
	label: String,
	value: String,
	isTotal: Boolean
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = label,
			style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
			fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
			color = if (isTotal) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
		)
		Text(
			text = value,
			style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge,
			fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
			color = if (isTotal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
		)
	}
}

@Composable
fun PaymentProofUploader(
	selectedImageUri: Uri?,
	onUploadClick: () -> Unit,
	onRemoveImage: () -> Unit
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		shape = RoundedCornerShape(12.dp)
	) {
		Column(modifier = Modifier.padding(16.dp)) {
			Text(
				text = "Comprobante de Pago",
				style = MaterialTheme.typography.titleMedium,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onSurface
			)
			
			Spacer(modifier = Modifier.height(12.dp))
			
			if (selectedImageUri == null) {
				OutlinedButton(
					onClick = onUploadClick,
					modifier = Modifier.fillMaxWidth(),
					shape = RoundedCornerShape(8.dp),
					border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
				) {
					Icon(
						painter = painterResource(id = com.techcode.palplato.R.drawable.ic_add),
						contentDescription = "Subir comprobante",
						modifier = Modifier.size(20.dp),
						tint = MaterialTheme.colorScheme.primary
					)
					Spacer(modifier = Modifier.width(8.dp))
					Text(
						text = "Agregar Comprobante",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.primary
					)
				}
			} else {
				Column {
					AsyncImage(
						model = selectedImageUri,
						contentDescription = "Comprobante seleccionado",
						modifier = Modifier
							.fillMaxWidth()
							.height(180.dp)
							.clip(RoundedCornerShape(8.dp)),
						contentScale = ContentScale.Crop
					)
					
					Spacer(modifier = Modifier.height(8.dp))
					
					OutlinedButton(
						onClick = onRemoveImage,
						modifier = Modifier.fillMaxWidth(),
						shape = RoundedCornerShape(8.dp),
						border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
					) {
						Icon(
							imageVector = Icons.Default.Delete,
							contentDescription = "Eliminar comprobante",
							modifier = Modifier.size(20.dp),
							tint = MaterialTheme.colorScheme.error
						)
						Spacer(modifier = Modifier.width(8.dp))
						Text(
							text = "Eliminar Comprobante",
							color = MaterialTheme.colorScheme.error
						)
					}
				}
			}
		}
	}
}


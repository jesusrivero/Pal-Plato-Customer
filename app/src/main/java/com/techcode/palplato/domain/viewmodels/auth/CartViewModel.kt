package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.domain.model.CartItem
import com.techcode.palplato.domain.usecase.auth.cart.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
	private val cartUseCases: CartUseCases
) : ViewModel() {
	
	val cartItems = cartUseCases.getCartItems()
		.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
	
	// Subtotal basado en los ítems del carrito
	val subtotal = cartItems.map { items ->
		items.sumOf { it.price * it.quantity }
	}.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
	
	// Costo de delivery (puedes hacerlo dinámico si luego lo traes de Firestore)
	private val _deliveryCost = MutableStateFlow(2.5) // ejemplo costo fijo
	val deliveryCost: StateFlow<Double> = _deliveryCost
	
	// Total (subtotal + delivery)
	val total = combine(subtotal, deliveryCost) { sub, delivery ->
		sub + delivery
	}.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)
	
	fun addProduct(item: CartItem) {
		viewModelScope.launch { cartUseCases.addToCart(item) }
	}
	
	fun removeProduct(productId: String) {
		viewModelScope.launch { cartUseCases.removeFromCart(productId) }
	}
	
	fun clearCart() {
		viewModelScope.launch { cartUseCases.clearCart() }
	}
	
	fun completePurchase(userId: String) {
		viewModelScope.launch { cartUseCases.completePurchase(userId) }
	}
	
	fun updateQuantity(productId: String, quantity: Int) {
		viewModelScope.launch {
			cartUseCases.updateQuantity(productId, quantity)
		}
	}
	
	fun removeItem(productId: String) {
		viewModelScope.launch {
			cartUseCases.removeItem(productId)
		}
	}
	
	// Si en algún momento quieres actualizar el costo de delivery dinámicamente:
	fun setDeliveryCost(cost: Double) {
		_deliveryCost.value = cost
	}
}

package com.techcode.palplato.domain.usecase.auth.cart

data class CartUseCases(
	val getCartItems: GetCartItems,
	val addToCart: AddToCart,
	val removeFromCart: RemoveFromCart,
	val clearCart: ClearCart,
	val removeItem: RemoveCartItem,
	val updateQuantity: UpdateCartQuantity,
	val completePurchase: CompletePurchase
)
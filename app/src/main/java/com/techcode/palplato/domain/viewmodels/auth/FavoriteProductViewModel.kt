package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.domain.model.room.FavoriteProduct
import com.techcode.palplato.domain.usecase.auth.favorites.AddFavoriteProductUseCase
import com.techcode.palplato.domain.usecase.auth.favorites.GetFavoriteProductsUseCase
import com.techcode.palplato.domain.usecase.auth.favorites.IsFavoriteProductUseCase
import com.techcode.palplato.domain.usecase.auth.favorites.RemoveFavoriteProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteProductViewModel @Inject constructor(
	private val getFavoritesUseCase: GetFavoriteProductsUseCase,
	private val addFavoriteUseCase: AddFavoriteProductUseCase,
	private val removeFavoriteUseCase: RemoveFavoriteProductUseCase,
	private val isFavoriteUseCase: IsFavoriteProductUseCase
) : ViewModel() {
	
	private val _favorites = MutableStateFlow<List<FavoriteProduct>>(emptyList())
	val favorites: StateFlow<List<FavoriteProduct>> = _favorites
	
	fun loadFavorites() {
		viewModelScope.launch {
			val list = getFavoritesUseCase()
			_favorites.value = list
		}
	}
	
	
	
	fun addFavorite(product: FavoriteProduct) {
		viewModelScope.launch {
			addFavoriteUseCase(product)
			loadFavorites()
		}
	}
	
	fun removeFavorite(product: FavoriteProduct) {
		viewModelScope.launch {
			removeFavoriteUseCase(product)
			loadFavorites()
		}
	}
	
	suspend fun isFavorite(productId: String): Boolean {
		return isFavoriteUseCase(productId)
	}
}
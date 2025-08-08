package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.data.repository.local.FavoriteBusinessEntity
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.usecase.auth.favorites.FavoriteBusinessUseCases
import com.techcode.palplato.domain.repository.FavoriteBusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteBusinessViewModel @Inject constructor(
	private val useCases: FavoriteBusinessUseCases,
	private val repository: FavoriteBusinessRepository
) : ViewModel() {
	
	private val _favorites = MutableStateFlow<List<FavoriteBusinessEntity>>(emptyList())
	val favorites: StateFlow<List<FavoriteBusinessEntity>> = _favorites.asStateFlow()
	
	init {
		viewModelScope.launch {
			useCases.getFavorites() // ← aquí no lleva .invoke()
				.collect { list ->
					_favorites.value = list
				}
		}
	}
	
	fun addFavoriteFromBusiness(business: Business) {
		val entity = FavoriteBusinessEntity(
			businessId = business.id,
			name = business.name,
			logoUrl = business.logoUrl,
			description = business.description
		)
		viewModelScope.launch(Dispatchers.IO) {
			useCases.addFavorite(entity)
		}
	}
	
	fun removeFavoriteById(businessId: String) {
		viewModelScope.launch(Dispatchers.IO) {
			useCases.removeFavorite(businessId)
		}
	}
	
	fun toggleFavorite(business: Business) {
		viewModelScope.launch {
			val isFavorite = repository.isFavorite(business.id)
			if (isFavorite) {
				repository.removeFavoriteById(business.id)
			} else {
				repository.addFavorite(
					FavoriteBusinessEntity(
						businessId = business.id,
						name = business.name,
						logoUrl = business.logoUrl ?: "",
						description = business.description
					)
				)
			}
		}
	}
}
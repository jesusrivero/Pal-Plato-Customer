package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.usecase.auth.products.GetProductByIdUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.GetProductsByBusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
	private val sessionManager: SessionManager,
	private val getProductsByBusinessUseCase: GetProductsByBusinessUseCase,
	private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
	
	
	private val _products = MutableStateFlow<List<Product>>(emptyList())
	val products: StateFlow<List<Product>> get() = _products
	
	fun loadProducts(businessId: String) {
		viewModelScope.launch {
			getProductsByBusinessUseCase(businessId).collect { productList ->
				_products.value = productList
			}
		}
	}
	
	fun getProductById(businessId: String, productId: String): Flow<Product?> {
		return getProductByIdUseCase(businessId, productId)
	}
	
}

package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.usecase.auth.bussiness.products.CreateProductUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.products.DeleteProductUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.products.GetProductsUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.products.UpdateProductAvailabilityUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.products.UpdateProductUseCase
import com.techcode.palplato.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
	private val createProductUseCase: CreateProductUseCase,
	private val getProductsUseCase: GetProductsUseCase,
	private val updateProductUseCase: UpdateProductUseCase,
	private val updateProductAvailabilityUseCase: UpdateProductAvailabilityUseCase,
	private val deleteProductUseCase: DeleteProductUseCase,
	private val sessionManager: SessionManager
) : ViewModel() {
	
	private val _createProductState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
	val createProductState: StateFlow<Resource<Unit>> = _createProductState
	
	private val _productsState = MutableStateFlow<Resource<List<Product>>>(Resource.Success(emptyList()))
	val productsState: StateFlow<Resource<List<Product>>> = _productsState
	
	private val _selectedProduct = MutableStateFlow<Product?>(null)
	val selectedProduct: StateFlow<Product?> = _selectedProduct
	
	private val _updateState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
	val updateState: StateFlow<Resource<Unit>> = _updateState
	
	private val _updateAvailabilityState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
	val updateAvailabilityState: StateFlow<Resource<Unit>> = _updateAvailabilityState
	
	private val _deleteProductState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
	val deleteProductState: StateFlow<Resource<Unit>> = _deleteProductState
	
	fun createProduct(product: Product) {
		viewModelScope.launch {
			_createProductState.value = Resource.Loading()
			
			val businessId = sessionManager.getBusinessId() ?: ""
			val productWithData = product.copy(
				businessId = businessId,
				date = System.currentTimeMillis() // ✅ Fecha actual
			)
			
			val result = createProductUseCase(productWithData)
			_createProductState.value = result
		}
	}

	
	fun getProducts() {
		viewModelScope.launch {
			_productsState.value = Resource.Loading()
			val businessId = sessionManager.getBusinessId() ?: return@launch
			val result = getProductsUseCase(businessId)
			_productsState.value = result
		}
	}
	
	fun getProduct(productId: String) {
		val currentProducts = _productsState.value
		if (currentProducts is Resource.Success) {
			val product = currentProducts.result.find { it.id == productId }
			if (product != null) {
				_selectedProduct.value = product
				return
			}
		}
		// Si no lo encuentra en memoria, recargar de Firestore
		viewModelScope.launch {
			val businessId = sessionManager.getBusinessId() ?: return@launch
			val result = getProductsUseCase(businessId)
			if (result is Resource.Success) {
				_productsState.value = result
				_selectedProduct.value = result.result.find { it.id == productId }
			}
		}
	}
	fun resetState() {
		_createProductState.value = Resource.Idle
	}
	
	fun updateProduct(product: Product) {
		viewModelScope.launch {
			_updateState.value = Resource.Loading()
			val result = updateProductUseCase(product)
			_updateState.value = if (result.isSuccess) {
				Resource.Success(Unit)
			} else {
				Resource.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
			}
		}
	}
	
	fun updateProductAvailability(businessId: String, productId: String, available: Boolean) {
		viewModelScope.launch {
			_updateAvailabilityState.value = Resource.Loading()
			
			val result = updateProductAvailabilityUseCase(businessId, productId, available)
			if (result.isSuccess) {
				// Actualizar productos en memoria
				val currentProducts = (_productsState.value as? Resource.Success)?.result?.toMutableList() ?: mutableListOf()
				val index = currentProducts.indexOfFirst { it.id == productId }
				if (index != -1) {
					currentProducts[index] = currentProducts[index].copy(available = available)
					_productsState.value = Resource.Success(currentProducts)
				}
				
				_updateAvailabilityState.value = Resource.Success(Unit) // ✅ Se emite solo aquí
			} else {
				_updateAvailabilityState.value = Resource.Error(result.exceptionOrNull()?.message ?: "Error al cambiar disponibilidad")
			}
		}
	}
	
	fun deleteProduct(businessId: String, productId: String) {
		viewModelScope.launch {
			_deleteProductState.value = Resource.Loading()
			val result = deleteProductUseCase(businessId, productId)
			if (result.isSuccess) {
				val currentProducts = (_productsState.value as? Resource.Success)?.result?.toMutableList() ?: mutableListOf()
				currentProducts.removeAll { it.id == productId }
				_productsState.value = Resource.Success(currentProducts)
				_deleteProductState.value = Resource.Success(Unit)
			} else {
				_deleteProductState.value = Resource.Error(result.exceptionOrNull()?.message ?: "Error al eliminar producto")
			}
		}
	}
	
	fun resetDeleteState() {
		_deleteProductState.value = Resource.Idle
	}
	
	fun resetAvailabilityState() {
		_updateAvailabilityState.value = Resource.Idle
	}
	
	fun resetUpdateState() {
		_updateState.value = Resource.Idle
	}
	
}

package com.techcode.palplato.domain.usecase.auth.bussiness.products

import android.net.Uri
import android.util.Log
import com.techcode.palplato.domain.repository.ProductRepository
import javax.inject.Inject

class UploadProductImageUseCase @Inject constructor(
	private val repository: ProductRepository
) {
	suspend operator fun invoke(businessId: String, productId: String, imageUri: Uri): Result<String> {
		Log.d("UploadProductImageUseCase", "Ejecutando uploadProductImage con businessId=$businessId, productId=$productId, imageUri=$imageUri")
		return repository.uploadProductImage(businessId, productId, imageUri)
	}
}


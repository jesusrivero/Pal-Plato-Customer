package com.techcode.palplato.domain.helpers

import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.model.room.FavoriteProduct

fun Product.toFavoriteProduct(): FavoriteProduct = FavoriteProduct(
	id = this.id,
	businessId = this.businessId,
	price = this.price,
	name = this.name,
	description = this.description,
	category = this.category,
	imageUrl = this.imageUrl,
	preparationTime = this.preparationTime,
	date = this.date,
	available = this.available,
	size = this.size,
	type = this.type
)
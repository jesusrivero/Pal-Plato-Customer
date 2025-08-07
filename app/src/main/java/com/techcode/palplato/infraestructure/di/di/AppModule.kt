package com.techcode.palplato.infraestructure.di.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.techcode.palplato.data.repository.AuthRepositoryImpl
import com.techcode.palplato.data.repository.BusinessRepositoryImpl
import com.techcode.palplato.data.repository.CartRepositoryImpl
import com.techcode.palplato.data.repository.ProductRepositoryImpl
import com.techcode.palplato.data.repository.UserRepositoryImpl
import com.techcode.palplato.data.repository.local.AppDatabase
import com.techcode.palplato.data.repository.local.CartDao
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.repository.AuthRepository
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.domain.repository.CartRepository
import com.techcode.palplato.domain.repository.ProductRepository
import com.techcode.palplato.domain.repository.UserRepository
import com.techcode.palplato.domain.usecase.auth.products.GetProductByIdUseCase
import com.techcode.palplato.domain.usecase.auth.LoginUseCase
import com.techcode.palplato.domain.usecase.auth.RegisterUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.GetBusinessesUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.GetProductsByBusinessUseCase
import com.techcode.palplato.domain.usecase.auth.cart.AddToCart
import com.techcode.palplato.domain.usecase.auth.cart.CartUseCases
import com.techcode.palplato.domain.usecase.auth.cart.ClearCart
import com.techcode.palplato.domain.usecase.auth.cart.CompletePurchase
import com.techcode.palplato.domain.usecase.auth.cart.GetCartItems
import com.techcode.palplato.domain.usecase.auth.cart.RemoveCartItem
import com.techcode.palplato.domain.usecase.auth.cart.RemoveFromCart
import com.techcode.palplato.domain.usecase.auth.cart.UpdateCartQuantity
import com.techcode.palplato.domain.usecase.auth.updateprofiledates.ReauthenticateUserUseCase
import com.techcode.palplato.domain.usecase.auth.updateprofiledates.UpdateUserEmailUseCase
import com.techcode.palplato.domain.usecase.auth.updateprofiledates.UpdateUserPasswordUseCase
import com.techcode.palplato.domain.usecase.auth.updateprofiledates.UpdateUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	
	
	@Provides
	@Singleton
	fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
	
	@Provides
	@Singleton
	fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
	
	
	@Provides
	@Singleton
	fun provideAuthRepository(
		firebaseAuth: FirebaseAuth,
		firestore: FirebaseFirestore,
		sessionManager: SessionManager
	): AuthRepository {
		return AuthRepositoryImpl(firebaseAuth, firestore, sessionManager)
	}

	@Provides
	@Singleton
	fun provideRegisterUseCase(
		repository: AuthRepository
	): RegisterUseCase = RegisterUseCase(repository)
	
	@Provides
	@Singleton
	fun provideLoginUseCase(
		repository: AuthRepository
	): LoginUseCase = LoginUseCase(repository)
	
	@Provides
	@Singleton
	fun provideBusinessRepository(
		firestore: FirebaseFirestore,
		sessionManager: SessionManager
	): BusinessRepository {
		return BusinessRepositoryImpl(firestore)
	}
	
	@Provides
	@Singleton
	fun provideGetBusinessesUseCase(
		repository: BusinessRepository
	): GetBusinessesUseCase = GetBusinessesUseCase(repository)
	
	@Provides
	@Singleton
	fun provideProductRepository(
		firestore: FirebaseFirestore,
		firebaseStorage: FirebaseStorage
	): ProductRepository = ProductRepositoryImpl(firestore, firebaseStorage)
	

	
	@Provides
	@Singleton
	fun provideFirebaseStorage(): FirebaseStorage {
		return FirebaseStorage.getInstance()
	}
	
	@Provides
	@Singleton
	fun provideUserRepository(
		firestore: FirebaseFirestore,
		auth: FirebaseAuth,
		sessionManager: SessionManager
	): UserRepository = UserRepositoryImpl(firestore,auth, sessionManager)
	
	@Provides
	@Singleton
	fun provideUpdateUserProfileUseCase(
		userRepository: UserRepository
	): UpdateUserProfileUseCase = UpdateUserProfileUseCase(userRepository)
	
	@Provides
	@Singleton
	fun provideReauthenticateUserUseCase(repository: UserRepository): ReauthenticateUserUseCase {
		return ReauthenticateUserUseCase(repository)
	}
	
	@Provides
	@Singleton
	fun provideUpdateUserEmailUseCase(userRepository: UserRepository): UpdateUserEmailUseCase {
		return UpdateUserEmailUseCase(userRepository)
	}
	
	@Provides
	@Singleton
	fun provideUpdateUserPasswordUseCase(
		userRepository: UserRepository
	): UpdateUserPasswordUseCase = UpdateUserPasswordUseCase(userRepository)
	
	
	@Provides
	@Singleton
	fun provideGetProductsByBusinessUseCase(
		repository: ProductRepository
	): GetProductsByBusinessUseCase = GetProductsByBusinessUseCase(repository)
	
	@Provides
	@Singleton
	fun provideGetProductByIdUseCase(
		repository: ProductRepository
	): GetProductByIdUseCase = GetProductByIdUseCase(repository)
	
	
	
	
	@Provides
	@Singleton
	fun provideDatabase(
		@ApplicationContext context: Context
	): AppDatabase {
		return Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			"app_database"
		)
			.fallbackToDestructiveMigration() // ← Esto elimina y recrea la BD si el esquema cambió
			.build()
	}
	
	@Provides
	fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
	
	
	@Provides
	@Singleton
	fun provideCartRepository(
		dao: CartDao,
		firestore: FirebaseFirestore
	): CartRepository = CartRepositoryImpl(dao, firestore)
	
	@Provides
	@Singleton
	fun provideCartUseCases(repository: CartRepository): CartUseCases {
		return CartUseCases(
			getCartItems = GetCartItems(repository),
			addToCart = AddToCart(repository),
			removeFromCart = RemoveFromCart(repository),
			updateQuantity = UpdateCartQuantity(repository),
			clearCart = ClearCart(repository),
			completePurchase = CompletePurchase(repository),
			removeItem = RemoveCartItem(repository)
		)
	}
}
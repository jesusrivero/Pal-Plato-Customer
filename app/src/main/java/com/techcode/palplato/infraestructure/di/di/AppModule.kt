package com.techcode.palplato.infraestructure.di.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.data.repository.AuthRepositoryImpl
import com.techcode.palplato.data.repository.BusinessRepositoryImpl
import com.techcode.palplato.domain.repository.AuthRepository
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.domain.usecase.auth.LoginUseCase
import com.techcode.palplato.domain.usecase.auth.RegisterUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.CreateBusinessUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.GetBusinessUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.UpdateBusinessUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
	): AuthRepository {
		return AuthRepositoryImpl(firebaseAuth, firestore)
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
	fun provideBusinessRepository(firestore: FirebaseFirestore): BusinessRepository {
		return BusinessRepositoryImpl(firestore)
	}
	
	@Provides
	@Singleton
	fun provideCreateBusinessUseCase(repository: BusinessRepository): CreateBusinessUseCase {
		return CreateBusinessUseCase(repository)
	}
	
	@Provides
	@Singleton
	fun provideUpdateBusinessUseCase(repository: BusinessRepository): UpdateBusinessUseCase {
		return UpdateBusinessUseCase(repository)
	}
	
	@Provides
	@Singleton
	fun provideGetBusinessUseCase(repository: BusinessRepository): GetBusinessUseCase {
		return GetBusinessUseCase(repository)
	}
}
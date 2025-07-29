package com.techcode.palplato.infraestructure.di.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.data.repository.AuthRepositoryImpl
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.repository.AuthRepository
import com.techcode.palplato.domain.usecase.LoginUseCase
import com.techcode.palplato.domain.usecase.RegisterUseCase
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
}
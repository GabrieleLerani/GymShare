package com.project.gains.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


import com.project.gains.data.manager.ContextManagerImpl

import com.project.gains.data.manager.LocalUserManagerImpl

import com.project.gains.data.manager.SettingsManagerImpl

import com.project.gains.domain.manager.ContextManager

import com.project.gains.domain.manager.LocalUserManager

import com.project.gains.domain.manager.SettingsManager
import com.project.gains.domain.usecase.Subscribe
import com.project.gains.domain.usecase.appEntry.ReadAppEntry
import com.project.gains.domain.usecase.appEntry.ReadUser
import com.project.gains.domain.usecase.appEntry.SaveAppEntry
import com.project.gains.domain.usecase.appEntry.SaveUser

import com.project.gains.domain.usecase.auth.AuthCheck
import com.project.gains.domain.usecase.auth.AuthenticationUseCases
import com.project.gains.domain.usecase.auth.SignIn
import com.project.gains.domain.usecase.auth.SignUp

import com.mygdx.game.domain.usecase.settings.FetchUserProfile
import com.mygdx.game.domain.usecase.settings.SettingsUseCases
import com.mygdx.game.domain.usecase.settings.SignOut
import com.mygdx.game.domain.usecase.settings.Update

import com.project.gains.data.manager.AuthManagerImpl
import com.project.gains.domain.manager.AuthManager
import com.project.gains.domain.usecase.appEntry.AppEntryUseCases
import com.project.gains.domain.usecase.linkedSocial.FetchLinkedSocial
import com.project.gains.domain.usecase.linkedSocial.LinkedSocialUseCases
import com.project.gains.domain.usecase.linkedSocial.StoreLinkedSocial

import javax.inject.Singleton

/*
* This module provides instances of class, it allow classes to depend on abstractions rather
* than concrete implementations, making the code more modular and maintainable.
* In order to inject a class into another file you need to use @Inject and the name of the class.
* Then Hilt takes care of providing the instance for you*/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application,
        firestore: FirebaseFirestore
    ): LocalUserManager = LocalUserManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideAuthManager(firebaseAuth: FirebaseAuth,
                           localUserManager: LocalUserManager,
                           firestore: FirebaseFirestore
    ): AuthManager = AuthManagerImpl(
         firebaseAuth =firebaseAuth, firestore = firestore, localUserManager = localUserManager )

    @Provides
    @Singleton
    fun provideSettingsManager(firebaseAuth: FirebaseAuth,
                               localUserManager: LocalUserManager,
                               authManager: AuthManager
    ): SettingsManager = SettingsManagerImpl(
        firebaseAuth =firebaseAuth, localUserManager = localUserManager
    )

    @Provides
    @Singleton
    fun provideContextManager(context: Context): ContextManager = ContextManagerImpl(context = context )


    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager),
        readUser = ReadUser(localUserManager),
        saveUser = SaveUser(localUserManager),
    )
    @Provides
    @Singleton
    fun provideAuthUseCases(
        settingsManager: SettingsManager,
        authManager: AuthManager,
        localUserManager: LocalUserManager,

        ) = AuthenticationUseCases(
        signIn = SignIn(authManager),
        signUp = SignUp(authManager),
        authCheck= AuthCheck(authManager),
        subscribe = Subscribe(settingsManager, authManager)

    )

    @Provides
    @Singleton
    fun provideSettingsUseCases(
        settingsManager: SettingsManager,
        authManager: AuthManager,
        ) = SettingsUseCases(
        update= Update(settingsManager),
        fetch= FetchUserProfile(settingsManager),
        signOut = SignOut(settingsManager),
        subscribe = Subscribe(settingsManager, authManager),

    )


    @Provides
    @Singleton
    fun provideLinkedSocialUseCases(
        localUserManager: LocalUserManager,
        ) = LinkedSocialUseCases(
        storeLinkedSocial = StoreLinkedSocial(localUserManager),
        fetchLinkedSocial = FetchLinkedSocial(localUserManager)
    )





}
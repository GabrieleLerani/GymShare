package com.mygdx.game.domain.usecase.settings

import com.project.gains.domain.usecase.Subscribe
import com.project.gains.domain.usecase.linkedSocial.FetchLinkedSocial
import com.project.gains.domain.usecase.linkedSocial.StoreLinkedSocial

data class SettingsUseCases (
    val update: Update,
    val fetch: FetchUserProfile,
    val signOut: SignOut,
    val subscribe: Subscribe,
)
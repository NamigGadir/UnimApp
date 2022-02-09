/*
 * Created by Namig Gadirov on on 7/16/21, 4:24 PM
 * Copyright (c) 2021 . All rights reserved to GuavaPay
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

package com.unimapp.core

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest


fun NavController.deeplinkNavigate(direction: String, extras: MutableMap<String, String>? = null) {
    var deeplinkDirection = direction
    extras?.forEach { (key, value) ->
        deeplinkDirection = deeplinkDirection.replace("{$key}", value)
    }
    val deepLink = NavDeepLinkRequest.Builder
        .fromUri(deeplinkDirection.toUri())
        .build()
    navigate(deepLink)
}

object DeeplinkNavigationTypes {
    const val HOME_PAGE = "unimapp://home_page"
}

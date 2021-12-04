package com.unimapp.domain.validators

import com.unimapp.domain.base.BaseValidator
import javax.inject.Inject


class CharacterValidator @Inject constructor() : BaseValidator<String> {

    private val regex by lazy { "[.,_\\-/%]".toRegex() }

    override fun isValid(input: String): Boolean {
        return true
    }

}
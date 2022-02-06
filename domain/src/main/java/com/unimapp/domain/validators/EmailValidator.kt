package com.unimapp.domain.validators

import com.unimapp.domain.base.BaseValidator
import javax.inject.Inject


class EmailValidator @Inject constructor() : BaseValidator<String> {

    private val pattern by lazy { android.util.Patterns.EMAIL_ADDRESS }

    override fun isValid(input: String): Boolean {
        return pattern.matcher(input).matches()
    }

}
package com.unimapp.domain.validators

import com.unimapp.domain.base.BaseValidator
import javax.inject.Inject


class UpperCaseValidator @Inject constructor() : BaseValidator<String> {

    private val regex by lazy { "(.*[A-Z].*)".toRegex() }

    override fun isValid(input: String): Boolean {
        return input.matches(regex)
    }

}
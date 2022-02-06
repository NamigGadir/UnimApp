package com.unimapp.domain.usecases

import com.unimapp.domain.base.BaseUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor() : BaseUseCase<LoginUseCase.Params, String> {

    override suspend fun invoke(input: Params): String {
        return "dddddd"
    }

    class Params(userName: String, password: String)

}
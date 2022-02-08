package com.unimapp.domain.entities.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RegistrationRequest(
    var degreeType: String? = null,
    var emailAddress: String? = null,
    var firstName: String? = null,
    var birthDay: String? = null,
    var genderType: String? = null,
    var lastName: String? = null,
    var middleName: String? = null,
    var phoneNumber: String? = null,
    var password: String? = null,
    var universityId: Int? = null,
    var facultyId: List<Int>? = null,
    var interestIds: List<Int>? = null,
    var startYear: Int? = null,
    var lang: String? = null
) : Parcelable
package com.unimapp.domain.entities.profile

class About(
    val aboutId: Long,
    val aboutTitle: String,
    val aboutType: AboutType
)


enum class AboutType{
    WORK,
    EDUCATION,
    FINISHED_EDUCATION,
    IDEA,
    LOCATION,
    BIRTHDATE,
    RELATIONSHIP
}
package com.unimapp.domain.entities.auth

enum class DegreeTypes(val index: Int, val type: String) {
    FRESHMAN(0, "Freshman"),
    JUNIOR(1, "Junior"),
    MASTER(2, "Master"),
    SENIOR(3, "Senior"),
    SOPHOMORE(4, "Sophomore");

    companion object {
        fun findById(id: Int?): DegreeTypes? {
            return DegreeTypes.values().find { it.index == id }
        }
    }
}

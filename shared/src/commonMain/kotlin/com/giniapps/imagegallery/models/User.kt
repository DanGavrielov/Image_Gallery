package com.giniapps.imagegallery.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) {
    companion object {
        fun emptyObject() =
            User(
                id = -1,
                name = "",
                username = "",
                email = "",
                address = Address.emptyObject(),
                phone = "",
                website = "",
                company = Company.emptyObject()
            )
    }
}

@Serializable
data class Address (
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
) {
    companion object {
        fun emptyObject() =
            Address(
                street = "",
                suite = "",
                city = "",
                zipcode = "",
                geo = Geo.emptyObject()
            )
    }
}

@Serializable
data class Geo (
    val lat: String,
    val lng: String
) {
    companion object {
        fun emptyObject() =
            Geo(
                lat = "",
                lng = ""
            )
    }
}

@Serializable
data class Company (
    val name: String,
    val catchPhrase: String,
    val bs: String
) {
    companion object {
        fun emptyObject() =
            Company(
                name = "",
                catchPhrase = "",
                bs = ""
            )
    }
}
package com.example.casaconnect.Domain

import java.io.Serializable

class PropertyDomain(
    var type: String?,
    var title: String?,
    var address: String?,
    var pickpath: String?,
    var description: String?,
    var price: Int,
    var bed: Int,
    var bath: Int,
    var size: String?,
    var garage: Boolean?,
    var score: Double
) : Serializable

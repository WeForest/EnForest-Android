package com.gsm.domain.entity.group.request


data class CreateGroup(
    val name: String, val description: String, val tags: String
)
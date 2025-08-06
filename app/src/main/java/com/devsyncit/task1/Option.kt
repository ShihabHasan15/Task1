package com.devsyncit.task1

data class Option(
    val referTo: ReferToX,
    val value: String
){
    override fun toString(): String {
        return value
    }
}


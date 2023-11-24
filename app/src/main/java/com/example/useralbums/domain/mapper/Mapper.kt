package com.example.useralbums.domain.mapper

interface Mapper<I,O> {
    fun map(input:I):O
}
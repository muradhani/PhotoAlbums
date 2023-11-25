package com.example.useralbums.data.mapper

import com.example.useralbums.data.Dto.User.UserResponseItem
import com.example.useralbums.domain.mapper.Mapper
import com.example.useralbums.domain.models.User
import javax.inject.Inject

class UserMapper @Inject constructor(): Mapper<UserResponseItem, User> {
    override fun map(input: UserResponseItem): User {
        return User(name = input.name, id = input.id, address = "${input.address.street}.${input.address.suite}.${input.address.city}.${input.address.zipcode}")
    }
}
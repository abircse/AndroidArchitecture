package com.coxtunes.androidarchitecturekotlin2021.data.mapper

import com.coxtunes.androidarchitecture2021.data.dto.user.UsersItem
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems

/**
 * @Author: Nayeem Shiddiki Abir
 * @Date: 28-Dec-21
 */

fun UsersItem.toUsers(): UsersViewItems {
    return UsersViewItems(name, phone, website)
}
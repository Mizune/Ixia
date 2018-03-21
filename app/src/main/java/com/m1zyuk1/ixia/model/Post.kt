package com.m1zyuk1.ixia.model

import java.io.Serializable
import java.util.*

/**
 * Created by yukian on 2018/03/19.
 */
data class Post(var title: String, var imagePath: String, var createAt: Date, var comment: String) : Serializable
package com.gsm.presentation.adapter

interface RecyclerViewItemClickListener<T> {
    fun onclick(data: T): Unit

}
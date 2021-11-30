package com.gsm.presentation.adapter

interface RecyclerViewItemClickListeners<T> {
    fun onclicks(data: T): Unit

}
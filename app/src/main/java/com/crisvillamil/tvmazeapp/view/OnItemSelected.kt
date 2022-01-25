package com.crisvillamil.tvmazeapp.view

import android.view.View

interface OnItemSelected<T> {
    fun onItemSelected(item: T, sharedElementTransition: View)
}
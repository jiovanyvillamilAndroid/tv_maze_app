package com.crisvillamil.tvmazeapp

import android.view.View
import com.crisvillamil.tvmazeapp.model.Show

interface OnItemSelected<T> {
    fun onItemSelected(item: T, sharedElementTransition: View)
}
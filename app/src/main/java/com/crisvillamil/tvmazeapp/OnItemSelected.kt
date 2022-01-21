package com.crisvillamil.tvmazeapp

import android.view.View
import com.crisvillamil.tvmazeapp.model.Show

interface OnItemSelected {
    fun onItemSelected(showItem: Show, sharedElementTransition: View)
}
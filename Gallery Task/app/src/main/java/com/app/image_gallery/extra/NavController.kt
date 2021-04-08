package com.app.image_gallery.extra

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.app.image_gallery.R

fun Fragment.navigate(@IdRes resId : Int){
    val navOptions =
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_from_right)
            .setExitAnim(R.anim.slide_out_to_left)
            .setPopEnterAnim(R.anim.slide_in_from_left)
            .setPopExitAnim(R.anim.slide_out_to_right).build()
    findNavController().navigate(resId,null,navOptions)
}

fun Fragment.navigateFromTop(@IdRes resId : Int) {
    val navOptions =
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_up)
            .setExitAnim(R.anim.slide_out_up)
            .setPopEnterAnim(R.anim.slide_in_down)
            .setPopExitAnim(R.anim.slide_out_down).build()
    findNavController().navigate(resId,null,navOptions)
}

fun NavController.navigateAnim(@IdRes resId: Int){
    val navOptions =
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_from_right)
            .setExitAnim(R.anim.slide_out_to_left)
            .setPopEnterAnim(R.anim.slide_in_from_left)
            .setPopExitAnim(R.anim.slide_out_to_right).build()
    navigate(resId,null,navOptions)
}
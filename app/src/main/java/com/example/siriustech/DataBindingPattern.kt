package com.example.siriustech

import com.airbnb.epoxy.EpoxyDataBindingPattern

/**
 * [DataBindingPattern] is used to define the generated model class from XML files that start with item.
 */
@EpoxyDataBindingPattern(rClass = R::class, layoutPrefix = "item")
object DataBindingPattern
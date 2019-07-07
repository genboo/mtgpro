package ru.spcm.apps.mtgpro.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.annotation.Retention
import kotlin.annotation.Target
import kotlin.annotation.MustBeDocumented
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

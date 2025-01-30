package com.jesus.voice.common.util

import org.springframework.data.mongodb.core.mapping.Field
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

fun <V> dotPath(vararg props: KProperty<V>): String =
    props.joinToString(separator = ".") { it.field() }

private fun <V> KProperty<V>.field(): String {
    return field(
        fieldAnnotation = javaField?.getAnnotation(Field::class.java),
        memberField = name,
    )
}

private fun field(
    fieldAnnotation: Field?,
    memberField: String
): String {
    return when {
        fieldAnnotation == null -> memberField.camelToSnakeCase()
        fieldAnnotation.value.isNotEmpty() -> fieldAnnotation.value
        fieldAnnotation.name.isNotEmpty() -> fieldAnnotation.name
        else -> memberField.camelToSnakeCase()
    }
}

private fun String.camelToSnakeCase(): String {
    val pattern = "(?<=.)[A-Z]".toRegex()
    return this.replace(pattern, "_$0").lowercase()
}

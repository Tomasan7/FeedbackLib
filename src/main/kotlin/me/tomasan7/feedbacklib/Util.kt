package me.tomasan7.feedbacklib

import java.util.*
import kotlin.collections.HashMap

/**
 * Creates a new empty mutable [LinkedList].
 */
fun <T> emptyMutableLinkedList() = LinkedList<T>()

/**
 * Creates a new empty mutable [HashMap].
 */
fun <K, V> emptyMutableHashMap() = HashMap<K, V>()

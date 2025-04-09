package org.mathieu.cleanrmapi.common

/**
 * Actual implementation of [getPlatformName] for Android.
 *
 * This function returns a simple platform identifier used by the shared code
 * to tailor behavior or messaging to the current platform.
 */
actual fun getPlatformName(): String = "Android"
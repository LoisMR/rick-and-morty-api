package org.mathieu.cleanrmapi.common

/**
 * Expects a platform-specific implementation that returns the name of the current platform.
 *
 * This function allows the shared codebase (commonMain) to access platform-specific information
 * without relying on dependency injection or interface-based abstraction.
 *
 * Implementations must be provided in each platform source set using the `actual` keyword.
 */
expect fun getPlatformName(): String
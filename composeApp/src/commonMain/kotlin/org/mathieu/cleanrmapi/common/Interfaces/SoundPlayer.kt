package org.mathieu.cleanrmapi.common.Interfaces

/**
 * I created this interface to define a contract for playing short sound effects,
 * in a platform-agnostic way.
 *
 * Instead of using `expect/actual`, I chose to go with an interface and inject the implementation
 * using dependency injection (Koin). This gives me more flexibility:
 *
 * - I can mock the implementation during testing
 * - I can swap out the Android implementation if needed (e.g., for different audio engines)
 * - My shared code remains decoupled from the platform
 *
 * This design follows Clean Architecture principles: I depend on abstractions, not implementations.
 */
interface SoundPlayer {
    fun playSound()
}
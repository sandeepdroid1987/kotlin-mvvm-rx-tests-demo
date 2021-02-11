
package com.sandeep.mvvmdemo.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    class ApiError(val code: Int): Failure()

    object UknownError: Failure()
}

package com.rax.core.usecase

abstract class UseCase<R, in P> {

    /**
     * Builds which will be used when executing the current [UseCase].
     */
    protected abstract suspend fun build(param: P): R

    /**
     * Executes the current use case.
     */
    suspend operator fun invoke(param: P): R = execute(param, false)

    protected open suspend fun execute(param: P, fromUseCase: Boolean): R {
        return build(param)
    }

}
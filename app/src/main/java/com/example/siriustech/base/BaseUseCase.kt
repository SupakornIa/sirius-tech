package com.example.siriustech.base

abstract class BaseUseCase<INPUT, OUTPUT> {
    protected abstract suspend fun create(input: INPUT): OUTPUT

    suspend fun execute(input: INPUT): Result<OUTPUT> {
        return try {
            Result.success(create(input))
        } catch (error: Throwable) {
            Result.failure(error)
        }
    }
}
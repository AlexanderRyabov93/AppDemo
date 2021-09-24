package ru.alexapps.personaldictionary.exceptions

import java.lang.Exception

open class BaseAppException(message: String, cause: Throwable?, val exceptionLevel: ExceptionLevel): Exception(message, cause)
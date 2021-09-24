package ru.alexapps.personaldictionary.exceptions

class ApiCallException(message: String, cause: Throwable? = null): BaseAppException(message, cause, ExceptionLevel.INFO) {
}
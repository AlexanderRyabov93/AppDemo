package ru.alexapps.personaldictionary.exceptions

class InvalidIdException(message: String) :
    BaseAppException(message, null, ExceptionLevel.CRITICAL) {
}
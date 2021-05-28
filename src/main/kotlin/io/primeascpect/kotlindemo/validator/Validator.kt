package io.primeascpect.kotlindemo.validator

import kotlin.reflect.KProperty1

fun main() {
    ExampleDTO(null, "", 140).validate {
        ExampleDTO::id.isNotNull().min(1)
        ExampleDTO::name.isNotNull().isNotBlank()
        ExampleDTO::age.isNotNull().min(0).max(100)
    }
}

@JvmName("check")
fun <T> T.validate(validation: Validator<T>.(T) -> Unit): T = validate(this, validation)


/**
 * See org.valiktor.Validator
 */
class Validator<T>(val t: T) {

    val constraintViolations = mutableListOf<ConstraintViolation>()

    fun <E> KProperty1<T, E?>.isNotNull(): PropertyValidator<E> {
        return PropertyValidator(this).apply { isNotNull() }
    }

    fun <E> KProperty1<T, E?>.isNotBlank(): PropertyValidator<E> {
        return PropertyValidator(this).apply { isNotBlank() }
    }

    fun <E> KProperty1<T, E?>.min(min: E): PropertyValidator<E> {
        return PropertyValidator(this).apply { min(min) }
    }

    fun <E> KProperty1<T, E?>.max(max: E): PropertyValidator<E> {
        return PropertyValidator(this).apply { max(max) }
    }

    inner class PropertyValidator<E>(val property: KProperty1<T, E?>) {
        private val name = property.name
        private val value = property.get(t)

        fun isNotNull(): PropertyValidator<E> {
            if (value == null) {
                addViolation("Must not be null")
            }
            return this
        }

        fun isNotBlank(): PropertyValidator<E> {
            if (value == null || value !is String) {
                return this
            }
            addViolation("Must not be blank")
            return this
        }

        fun min(min: E): PropertyValidator<E> {
            if (value == null || min !is Comparable<*>) {
                return this
            }
            if ((min as Comparable<E>) > value) {
                addViolation("Must be greater than $min")
            }
            return this
        }

        fun max(max: E): PropertyValidator<E> {
            if (value == null || max !is Comparable<*>) {
                return this
            }
            if ((max as Comparable<E>) < value) {
                addViolation("Must be lower than $max")
            }
            return this
        }

        private fun addViolation(message: String) {
            constraintViolations.add(ConstraintViolation(name, value, message))
        }
    }
}

fun <T> validate(t: T, validation: Validator<T>.(T) -> Unit): T {
    val validator = Validator(t)
    validation(validator, t)
    if (validator.constraintViolations.isNotEmpty()) {
        throw ConstraintViolationException(validator.constraintViolations)
    }
    return t
}

class ConstraintViolationException(val constraintViolations: List<ConstraintViolation>) : RuntimeException() {
    override fun toString() = "Validation failed. Violations: " + constraintViolations.joinToString(";") {
        "field=${it.field}, value=${it.value}, message=${it.message}"
    }
}

data class ConstraintViolation(val field: String, val value: Any?, val message: String)

data class ExampleDTO(val id: Int?, val name: String?, val age: Int?)

/**
 * Добавление кастомного метода.
 */
class ServiceWithValidation {

    fun save(dto: ExampleDTO) {
        dto.validate {
            ExampleDTO::id.isNotNull().min(1).isUnique()
            ExampleDTO::name.isNotNull().isNotBlank()
            ExampleDTO::age.isNotNull().min(0).max(100)
        }
        // сохраняем
    }

    private fun Validator<ExampleDTO>.PropertyValidator<Int>.isUnique(): Validator<ExampleDTO>.PropertyValidator<Int> {
        // проверяем уникальность
        return this
    }
}
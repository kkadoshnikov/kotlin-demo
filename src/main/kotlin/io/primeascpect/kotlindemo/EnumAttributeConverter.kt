package io.primeascpect.kotlindemo

import javax.persistence.AttributeConverter

open class EnumAttributeConverter<T : Enum<T>, ID>(private val toDatabaseColumn: (T) -> ID) : AttributeConverter<T, ID> {
    private val mapping: Map<ID, T>

    init {
        val enumClass = this::class.resolveGenericType<T>("T")
        mapping = enumClass.enumConstants.associateBy(toDatabaseColumn)
    }

    override fun convertToDatabaseColumn(t: T?) = t?.let(toDatabaseColumn)
    override fun convertToEntityAttribute(id: ID?) = mapping[id]
}

enum class Cities(val code: Int) {
    OMSK(1), MOSCOW(77), SAINT_PETERSBURG(78)
}

class CityCodeConverter: EnumAttributeConverter<Cities, Int>({ it.code} )
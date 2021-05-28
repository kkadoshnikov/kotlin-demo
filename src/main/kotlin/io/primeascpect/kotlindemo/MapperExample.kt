package io.primeascpect.kotlindemo

data class Entity(val entityField1: String, val entityField2: String, val entityField3: String)

data class DTO(val dtoField1: String, val dtoField2: String, val dtoField3: String)

fun Entity.toDTO() = DTO(
    dtoField1 = entityField1,
    dtoField2 = entityField2,
    dtoField3 = entityField3,
)

fun DTO.toEntity() = Entity(
    entityField1 = dtoField1,
    entityField2 = dtoField2,
    entityField3 = dtoField3,
)
















interface Mapper<ENTITY, DTO> {
    fun toDto(entity: ENTITY) = entity.mapToDto()

    fun toEntity(dto: DTO) = dto.mapToEntity()

    fun DTO.mapToEntity(): ENTITY

    fun ENTITY.mapToDto(): DTO
}

class MapperImpl: Mapper<Entity, DTO> {
    override fun DTO.mapToEntity() = Entity(
        entityField1 = dtoField1,
        entityField2 = dtoField2,
        entityField3 = dtoField3,
    )

    override fun Entity.mapToDto() = DTO(
        dtoField1 = entityField1,
        dtoField2 = entityField2,
        dtoField3 = entityField3,
    )
}
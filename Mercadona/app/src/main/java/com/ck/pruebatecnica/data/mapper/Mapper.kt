package com.ck.pruebatecnica.data.mapper

import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.model.CharacterDto
import com.ck.pruebatecnica.data.model.Episode
import com.ck.pruebatecnica.data.model.LocationDto
import com.ck.pruebatecnica.data.model.OriginDto
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.Location
import com.ck.pruebatecnica.domain.model.Origin

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        characterId = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        image = this.image,
        episode = this.episode
    )
}

fun CharacterEntity.toDomain(characterOrigin: Origin?,charaLocation: Location?): Character? {
    if (this.characterId == null) return null
    return Character(
        id = this.characterId,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = characterOrigin,
        location = charaLocation,
        image = this.image,
        episodes = this.episode
    )
}

fun LocationEntity.toDomain(): Location {
    return Location(
        name = this.name,
        url = this.url
    )
}

fun CharacterDto.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        origin = this.origin.toDomain(),
        location = this.location.toDomain(),
        image = this.image,
        episodes = this.episode
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        name = this.name,
        url = this.url
    )
}

fun LocationDto.toEntity(characterId: Long): LocationEntity{
    return LocationEntity(
        characterId = characterId,
        name = this.name,
        url = this.url
    )
}

fun OriginEntity.toDomain(): Origin {
    return Origin(
        name = this.name,
        url = this.url
    )
}

fun OriginDto.toEntity(characterId: Long): OriginEntity{
    return OriginEntity(
        characterId = characterId,
        name = this.name,
        url = this.url
    )
}

fun OriginDto.toDomain(): Origin {
    return Origin(
        name = this.name,
        url = this.url
    )
}

fun EpisodeEntity.toDomain(): Episode {
    return Episode(
        id = this.episodeId!!,
        episodeName = this.episodeName
    )
}


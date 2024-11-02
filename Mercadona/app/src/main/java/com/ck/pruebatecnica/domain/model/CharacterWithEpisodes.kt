package com.ck.pruebatecnica.domain.model

import com.ck.pruebatecnica.data.model.Episode

data class CharacterWithEpisodes(
    val character: Character,
    val episodes: List<Episode>
)
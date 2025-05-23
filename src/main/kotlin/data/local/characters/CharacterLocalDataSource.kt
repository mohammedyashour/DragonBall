package data.local.characters

import data.local.InitialDataSaver
import domain.model.DragonBallCharacter

class CharacterLocalDataSource {

    private val cachedCharacters: List<DragonBallCharacter>
        get() = InitialDataSaver.loadCachedCharacters()

    fun getAllCharacters(): List<DragonBallCharacter> {
        return cachedCharacters
    }

    fun getCharacterById(id: Int): DragonBallCharacter? {
        return cachedCharacters.firstOrNull { it.id == id }
    }

    fun filterCharacters(race: String?, affiliation: String?, gender: String?): List<DragonBallCharacter> {
        return cachedCharacters.filter { character ->
            (race == null || character.race.equals(race, ignoreCase = true)) &&
                    (affiliation == null || character.affiliation.equals(affiliation, ignoreCase = true)) &&
                    (gender == null || character.gender.equals(gender, ignoreCase = true))
        }
    }
}

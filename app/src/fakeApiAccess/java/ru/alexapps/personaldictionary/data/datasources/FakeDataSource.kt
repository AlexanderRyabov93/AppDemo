package ru.alexapps.personaldictionary.data.datasources

import kotlinx.coroutines.delay
import ru.alexapps.personaldictionary.data.models.LexicalCategory
import ru.alexapps.personaldictionary.data.models.Sense
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.utils.Result

class FakeDataSource : BaseDataSource {
    val data = arrayOf(
        Word(
            0,
            "application",
            arrayOf(
                Sense(
                    0,
                    arrayOf("application definition"),
                    arrayOf("Popular android application")
                )
            ),
            LexicalCategory.NOUN,
            null,
            null
        ),
        Word(
            0,
            "bright",
            arrayOf(
                Sense(
                    0,
                    arrayOf("filled with light"),
                    arrayOf("The room was bright with sunshine.")
                ),
                Sense(
                    0,
                    arrayOf("vivid or brilliant"),
                    arrayOf("a bright red dress", "bright passages of prose.")
                )
            ),
            LexicalCategory.ADJECTIVE,
            null,
            null
        ),
        Word(
            0,
            "cut",
            arrayOf(
                Sense(
                    0,
                    arrayOf("to penetrate with or as if with a sharp-edged instrument or object"),
                    arrayOf("He cut his finger.")
                ),
                Sense(
                    0,
                    arrayOf(
                        "to divide with or as if with a sharp-edged instrument",
                        "sever",
                        "carve"
                    ),
                    arrayOf("to cut a rope")
                )
            ),
            LexicalCategory.VERB,
            null,
            null
        ),
        Word(
            0,
            "cut",
            arrayOf(
                Sense(
                    0,
                    arrayOf(
                        "that has been subjected to cutting",
                        "divided into pieces by cutting",
                        "detached by cutting"
                    ),
                    arrayOf("cut flowers")
                )
            ),
            LexicalCategory.ADJECTIVE,
            null,
            null
        ),
        Word(
            0,
            "decor",
            arrayOf(
                Sense(
                    0,
                    arrayOf("style or mode of decoration, as of a room, building, or the like"),
                    arrayOf("modern office decor", "a bedroom having a Spanish decor")
                ),
                Sense(
                    0,
                    arrayOf("decoration in general", " ornamentation"),
                    arrayOf("beads, baubles, and other decor")
                )
            ),
            LexicalCategory.NOUN,
            null,
            null
        ),
        Word(
            0,
            "easy",
            arrayOf(
                Sense(
                    0,
                    arrayOf("not hard or difficult", "requiring no great labor or effort"),
                    arrayOf("a book that is easy to read", "an easy victory.")
                ),
                Sense(
                    0,
                    arrayOf("free from pain, discomfort, worry, or care"),
                    arrayOf("He led an easy life")
                ),
                Sense(
                    0,
                    arrayOf("providing or conducive to ease or comfort", " comfortable"),
                    arrayOf("an easy stance", "an easy relationship")
                )
            ),
            LexicalCategory.ADJECTIVE,
            null,
            null
        ),
        Word(
            0,
            "freedom",
            arrayOf(
                Sense(
                    0,
                    arrayOf("the state of being free or at liberty rather than in confinement or under physical restraint"),
                    arrayOf("He won his freedom after a retrial.")
                ),
                Sense(0, arrayOf("political or national independence"), arrayOf()),
                Sense(
                    0,
                    arrayOf("personal liberty, as opposed to bondage or slavery"),
                    arrayOf("The formerly enslaved seamstress bought her freedom and later became Mary Todd Lincoln’s dressmaker and stylist.")
                )
            ),
            LexicalCategory.NOUN,
            null,
            null
        ),
        Word(
            0,
            "glory",
            arrayOf(
                Sense(
                    0,
                    arrayOf(
                        "very great praise, honor, or distinction bestowed by common consent",
                        "renown"
                    ),
                    arrayOf("to win glory on the field of battle")
                ),
                Sense(
                    0,
                    arrayOf(
                        "something that is a source of honor, fame, or admiration",
                        " a distinguished ornament or an object of pride"
                    ),
                    arrayOf("a sonnet that is one of the glories of English poetry")
                ),
                Sense(
                    0,
                    arrayOf("adoring praise or worshipful thanksgiving:"),
                    arrayOf("Give glory to God.")
                )
            ),
            LexicalCategory.NOUN,
            null,
            null
        ),
        Word(
            0,
            "hero",
            arrayOf(
                Sense(
                    0,
                    arrayOf("a person noted for courageous acts or nobility of character"),
                    arrayOf("He became a local hero when he saved the drowning child.")
                ),
                Sense(
                    0,
                    arrayOf("a person who, in the opinion of others, has special achievements, abilities, or personal qualities and is regarded as a role model or ideal"),
                    arrayOf("My older sister is my hero", "Entrepreneurs are our modern heroes")
                )
            ),
            LexicalCategory.NOUN,
            null,
            null
        )
    )

    override suspend fun getWords(wordToFind: String): Result<List<Word>> =
        Result.Success(
            listOf(
                Word(
                    1,
                    wordToFind,
                    arrayOf(
                        Sense(
                            1,
                            arrayOf("Fake definition of word $wordToFind 1"),
                            arrayOf("Fake example of word $wordToFind 1")
                        ),
                    ),
                    LexicalCategory.ADJECTIVE,
                    null,
                    null
                ),
                Word(
                    2,
                    wordToFind,
                    arrayOf(
                        Sense(
                            2,
                            arrayOf("Fake definition of word $wordToFind 2"),
                            arrayOf("Fake example of word $wordToFind 2")
                        )
                    ),
                    LexicalCategory.ADJECTIVE,
                    null,
                    null
                )
            )
        )


    override suspend fun getWordsBySubstring(substring: String, limit: Int): Result<List<String>> {
        delay(1000L)
        val all = Result.Success(data.map { it.word }.distinct())
        if (substring.isEmpty()) return all
        val firstLetter = substring.first()
        val filtered = all.data.filter { it.startsWith(firstLetter, true) }
        if (filtered.isNotEmpty()) return Result.Success(filtered)
        return all
    }
}
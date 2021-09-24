package ru.alexapps.personaldictionary.data.db

object DbConstants {
    const val DATABASE_NAME = "word_database"

    const val TABLE_WORDS = "words"
    const val TABLE_DEFINITIONS = "definitions"
    const val TABLE_EXAMPLES = "examples"
    const val TABLE_SEMANTIC_CATEGORIES = "semantic_categories"
    const val TABLE_SENSES = "senses"
    const val TABLE_BNC_WORDS = "bnc_coca_words"
    const val TABLE_VOCABULARY_TEST_RESULTS = "vocabulary_test_results"
    const val TABLE_BNC_WORDS_TESTS = "bnc_words_tests"
    const val TABLE_SIMPLE_WORDS = "simple_words"

    //Word
    const val COLUMN_ID = "id"
    const val COLUMN_WORD = "word"
    const val COLUMN_LEXICAL_CATEGORY = "lexical_category"
    const val COLUMN_SAVE_DATE = "save_date"
    const val COLUMN_SEMANTIC_CATEGORY_ID = "semantic_category_id"

    //Definition
    const val COLUMN_DEFINITION = "definition"
    const val COLUMN_WORD_ID = "word_id"
    const val COLUMN_SENSE_ID = "sense_id"

    //Example
    const val COLUMN_EXAMPLE = "example"

    //SemanticCategory
    const val COLUMN_TITLE = "title"
    const val COLUMN_DESCRIPTION = "description"

    //BncWords
    const val COLUMN_DIFFICULTY = "difficulty"

    //VOCABULARY_TEST_RESULTS
    const val COLUMN_BNC_WORD_ID = "bnc_word_id"
    const val COLUMN_TEST_DATE = "test_date"
    const val COLUMN_TEST_PASSED = "test_passed"
    const val COLUMN_TEST_ID = "test_id"

}
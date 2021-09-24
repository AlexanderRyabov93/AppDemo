Кусок моего проекта для демонстрации. 

Запрашивает через API случайное английское слово и отображает его значения с примерами

Стек технологий:
* Kotlin
* Android Architecture Components
* Retrofit
* Hilt
* JUnit

Что интересного:
* Создал разные buildVariants для разных API (реальный кейс из проекта пока я определялся с выбором апишки)
   * default - [Wors API](https://www.wordsapi.com/)
   * oxfordDictionary - [Oxford Dictionaris API](https://developer.oxforddictionaries.com/)
   * fakeApi - просто фейковые данные
* В зависимости от buildType нужные зависимости  подключаются с помощью Hilt




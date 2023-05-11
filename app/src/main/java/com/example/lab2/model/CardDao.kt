package com.example.lab2.model

sealed class CardDao(val title: String, val subtitle: String) {

}

class CardTextOnPicture(title: String, subtitle: String, val img: String) : CardDao(title, subtitle)
class CardTextOnTopPicture(title: String, subtitle: String, val img: String, val hasBag: String) : CardDao(title, subtitle)
class CardTitleDescriptionOnly(title: String, subtitle: String) : CardDao(title, subtitle)
class CardRoundPicture(title: String, subtitle: String, val img: String) : CardDao(title, subtitle)

enum class CardTypeEnum{
    CardTextOnPicture,
    CardTextOnTopPicture,
    CardTitleDescriptionOnly,
    CardRoundPicture
}
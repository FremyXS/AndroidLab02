package com.example.lab2.viewmodel

import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab2.databinding.FragmentCardRoundPictureBinding
import com.example.lab2.databinding.FragmentCardTextOnPictureItemBinding
import com.example.lab2.databinding.FragmentCardTextOnTopPictureBinding
import com.example.lab2.databinding.FragmentCardTitleDescriptionOnlyBinding
import com.example.lab2.model.*
import com.example.lab2.view.adapters.CardAdapter


class CardViewModel: ViewModel() {
    private val cardLiveData: MutableLiveData<List<CardDao>> = MutableLiveData<List<CardDao>>()

    fun getCardLiveData(): LiveData<List<CardDao>> {
        return cardLiveData
    }

    fun setCard(cards: List<CardRequest>) {
        val newsCards = mutableListOf<CardDao>()

        for (card in cards){
            when(getType(card)){
                CardTypeEnum.CardTextOnPicture -> {
                    newsCards += CardTextOnPicture(card.title, card.subtitle, card.img!!)
                }
                CardTypeEnum.CardTextOnTopPicture -> {
                    newsCards += CardTextOnTopPicture(card.title, card.subtitle, card.img!!, card.hasBag!! )
                }
                CardTypeEnum.CardRoundPicture -> {
                    newsCards += CardRoundPicture(card.title, card.subtitle, card.img!!)
                }
                else -> {
                    newsCards += CardTitleDescriptionOnly(card.title, card.subtitle)
                }
            }
        }

        cardLiveData.value = newsCards
    }

    fun getType(cardRequest: CardRequest): CardTypeEnum {
        if(cardRequest.hasBag != null){
            return CardTypeEnum.CardTextOnTopPicture
        }

        if(cardRequest.isCircle != null){
            return CardTypeEnum.CardRoundPicture
        }

        if(cardRequest.img != null){
            return CardTypeEnum.CardTextOnPicture
        }

        return  CardTypeEnum.CardTitleDescriptionOnly
    }
}
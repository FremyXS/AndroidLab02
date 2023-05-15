package com.example.lab2.viewmodel

import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab2.api.ApiInterface
import com.example.lab2.api.RetrofitClient
import com.example.lab2.databinding.FragmentCardRoundPictureBinding
import com.example.lab2.databinding.FragmentCardTextOnPictureItemBinding
import com.example.lab2.databinding.FragmentCardTextOnTopPictureBinding
import com.example.lab2.databinding.FragmentCardTitleDescriptionOnlyBinding
import com.example.lab2.model.*
import com.example.lab2.view.adapters.CardAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse


class CardViewModel: ViewModel() {
    private val cardLiveData: MutableLiveData<List<CardDao>> = MutableLiveData<List<CardDao>>()

    private suspend fun getCardsAsync(): List<CardRequest>? {
        val apiService = RetrofitClient.getRetrofit().create(ApiInterface::class.java)

        val call = apiService.getMovieList()

        return try {
            var res = call.awaitResponse()
            res.body()
        }
        catch(e: Exception){
            listOf<CardRequest>()
        }
    }
    fun getCardLiveData(): LiveData<List<CardDao>> {
        return cardLiveData
    }

    fun setCard() {

        viewModelScope.launch {
            val newsCards = mutableListOf<CardDao>()
            val cards = async { getCardsAsync() }.await()

            for (card in cards!!){
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


    }

    private fun getType(cardRequest: CardRequest): CardTypeEnum {
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
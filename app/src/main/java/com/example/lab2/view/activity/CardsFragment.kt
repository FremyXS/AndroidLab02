package com.example.lab2.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab2.api.ApiInterface
import com.example.lab2.api.RetrofitClient
import com.example.lab2.databinding.FragmentCardsBinding
import com.example.lab2.model.*
import com.example.lab2.view.adapters.CardAdapter
import com.example.lab2.viewmodel.CardViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class CardsFragment : Fragment() {

    private lateinit var bind: FragmentCardsBinding
    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardViewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentCardsBinding.inflate(inflater, container, false)

        onLoadCardLiveData()
        bind.container.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        bind.container.adapter = cardAdapter
        return bind.root
    }

    fun onLoadCardLiveData() {

        cardAdapter = CardAdapter()
        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        cardViewModel.getCardLiveData().observe(viewLifecycleOwner, Observer {
                cards->
            cardAdapter.submitList(cards)

        })

        val apiService = RetrofitClient.getRetrofit().create(ApiInterface::class.java)

        val call = apiService.getMovieList()

        call.enqueue(object : Callback<List<CardRequest>>{
            override fun onResponse(call: Call<List<CardRequest>>, response: Response<List<CardRequest>>) {
                val cards = response.body()
                if(cards != null){
                    cardViewModel.setCard(cards)
                }
            }

            override fun onFailure(call: Call<List<CardRequest>>, t: Throwable) {

            }
        })





    }
}
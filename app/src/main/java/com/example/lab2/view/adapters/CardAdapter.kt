package com.example.lab2.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab2.databinding.FragmentCardRoundPictureBinding
import com.example.lab2.databinding.FragmentCardTextOnPictureItemBinding
import com.example.lab2.databinding.FragmentCardTextOnTopPictureBinding
import com.example.lab2.databinding.FragmentCardTitleDescriptionOnlyBinding
import com.example.lab2.model.*

class CardAdapter : ListAdapter<CardDao, RecyclerView.ViewHolder>(MyDiffCallback()) {
    private var list: List<CardDao> = ArrayList<CardDao>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            CardTypeEnum.CardTextOnPicture.ordinal -> {
                val binding = FragmentCardTextOnPictureItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
                CardTextOnPictureItem(binding)
            }
            CardTypeEnum.CardTextOnTopPicture.ordinal -> {
                val binding = FragmentCardTextOnTopPictureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
                CardTextOnTopPictureItem(binding)
            }
            CardTypeEnum.CardRoundPicture.ordinal -> {
                val binding = FragmentCardRoundPictureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
                CardRoundPictureItem(binding)
            }
            else -> {
                val binding = FragmentCardTitleDescriptionOnlyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
                CardTitleDescriptionOnlyItem(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CardTypeEnum.CardTextOnPicture.ordinal ->
                (holder as CardTextOnPictureItem).bind(getItem(position) as CardTextOnPicture, holder.itemView.context)
            CardTypeEnum.CardTextOnTopPicture.ordinal ->
                (holder as CardTextOnTopPictureItem).bind(getItem(position) as CardTextOnTopPicture, holder.itemView.context)
            CardTypeEnum.CardRoundPicture.ordinal ->
                (holder as CardRoundPictureItem).bind(getItem(position) as CardRoundPicture, holder.itemView.context)
            else ->
                (holder as CardTitleDescriptionOnlyItem).bind(getItem(position) as CardTitleDescriptionOnly, holder.itemView.context)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is CardTextOnPicture -> CardTypeEnum.CardTextOnPicture.ordinal
            is CardTextOnTopPicture -> CardTypeEnum.CardTextOnTopPicture.ordinal
            is CardTitleDescriptionOnly -> CardTypeEnum.CardTitleDescriptionOnly.ordinal
            is CardRoundPicture -> CardTypeEnum.CardRoundPicture.ordinal
            else -> {
                -1
            }
        }
    }

    fun setItems(list: List<CardDao>){
        this.list = list;
        notifyDataSetChanged()
    }

    class CardTextOnPictureItem(val bind: FragmentCardTextOnPictureItemBinding):RecyclerView.ViewHolder(bind.root){
        fun bind(card: CardTextOnPicture, context: Context) = with(bind){
            header.text = card.title
            subheader.text= card.subtitle

            Glide.with(context).load(card.img)
                .into(imageView2)
        }
    }

    class CardTextOnTopPictureItem(val bind: FragmentCardTextOnTopPictureBinding):RecyclerView.ViewHolder(bind.root) {
        fun bind(card: CardTextOnTopPicture, context: Context) = with(bind) {
            header.text = card.title
            subheader.text= card.subtitle

            Glide.with(context).load(card.img)
                .into(imageView2)
        }
    }

    class CardTitleDescriptionOnlyItem(val bind: FragmentCardTitleDescriptionOnlyBinding):RecyclerView.ViewHolder(bind.root){
        fun bind(card: CardTitleDescriptionOnly, context: Context) = with(bind){
            header.text = card.title
            subheader.text= card.subtitle
        }
    }

    class CardRoundPictureItem(val bind: FragmentCardRoundPictureBinding):RecyclerView.ViewHolder(bind.root){
        fun bind(card: CardRoundPicture, context: Context) = with(bind){
            header.text = card.title
            subheader.text= card.subtitle

            Glide.with(context).load(card.img)
                .into(imageView)
        }
    }

    class MyDiffCallback : DiffUtil.ItemCallback<CardDao>() {

        override fun areItemsTheSame(oldItem: CardDao, newItem: CardDao): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CardDao, newItem: CardDao): Boolean {
            return oldItem == newItem
        }
    }


}
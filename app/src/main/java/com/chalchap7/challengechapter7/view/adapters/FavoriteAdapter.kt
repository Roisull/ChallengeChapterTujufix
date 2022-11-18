package com.chalchap7.challengechapter7.view.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chalchap7.challengechapter7.model.FavoritesResponseItem
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.ItemFavoritesBinding

class FavoriteAdapter (var listMovie : List<FavoritesResponseItem>): RecyclerView.Adapter<FavoriteAdapter.ViewHoldler>() {

    var onDeleteFavorites : ((Int)->Unit)? = null

    class ViewHoldler(var binding : ItemFavoritesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldler {
        val view = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoldler(view)
    }

    override fun onBindViewHolder(holder: ViewHoldler, position: Int) {
        Glide.with(holder.itemView.context).load("https://image.tmdb.org/t/p/w185"+listMovie[position].posterPath).into(holder.binding.moviePoster)
        holder.binding.movieTitle.text = listMovie[position].originalTitle
        holder.binding.movieLang.text = holder.itemView.context?.getString(R .string.bahasa)+" "+listMovie[position].originalLanguage
        holder.binding.movieReleaseDate.text = holder.itemView.context?.getString(R.string.movieDate)+" "+listMovie[position].releaseDate
        holder.binding.movieRating.text = listMovie[position].voteAverage

        holder.binding.cardMovie.setOnClickListener{
            val arg = Bundle()
            arg.putString("gambar", listMovie[position].posterPath)
            arg.putString("judul", listMovie[position].originalTitle)
            arg.putString("rating", listMovie[position].voteAverage)
            arg.putString("tanggal", listMovie[position].releaseDate)
            arg.putString("bahasa", listMovie[position].originalLanguage)
            arg.putString("detail", listMovie[position].overview)

            Navigation.findNavController(holder.itemView).navigate(R.id.action_favoriteFragment_to_detilFragment,arg)
        }

        holder.binding.delFav.setOnClickListener {
            onDeleteFavorites?.invoke(listMovie[position].id.toInt())
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

}
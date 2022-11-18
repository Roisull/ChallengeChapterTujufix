package com.chalchap7.challengechapter7.view.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chalchap7.challengechapter7.model.Constant
import com.chalchap7.challengechapter7.model.Result
import com.chalchap7.challengechapter7.R
import com.chalchap7.challengechapter7.databinding.ItemVidioBinding
import com.squareup.picasso.Picasso

class MovieAdapter(var listMovie : List<Result>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    var onAddFavorites : ((Result)->Unit)? = null

    class ViewHolder (var binding: ItemVidioBinding):RecyclerView.ViewHolder(binding.root) {
        val view = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemVidioBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Glide.with(holder.itemView.context).load("https://image.tmdb.org/t/p/w185"+listMovie[position].posterPath).into(holder.binding.imgPoster)
        holder.binding.movieTitle.text = listMovie[position].originalTitle
        holder.binding.movieLang.text = listMovie[position].originalLanguage
        holder.binding.movieReleaseDate.text = listMovie[position].releaseDate
        holder.binding.movieRating.text = listMovie[position].voteAverage.toString()

        val movie = listMovie[position]
        Picasso.get()
            .load(Constant.TMDb_POSTER_PATH + movie.backdropPath)
            .placeholder(R.drawable.placeholder_portrait)
            .fit().centerCrop()
            .into(holder.view.imgPoster)

        holder.binding.cardMovie.setOnClickListener {
            val arg = Bundle()
            arg.putString("gambar", listMovie[position].posterPath)
            arg.putString("judul", listMovie[position].originalTitle)
            arg.putString("rating", listMovie[position].voteAverage.toString())
            arg.putString("tanggal", listMovie[position].releaseDate)
            arg.putString("bahasa", listMovie[position].originalLanguage)
            arg.putString("detail", listMovie[position].overview)

            Navigation.findNavController(holder.itemView).navigate(R.id.action_homeFragment_to_detilFragment, arg)

        }
        holder.binding.addFav.setOnClickListener {
            onAddFavorites?.invoke(listMovie[position])
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }
}
package com.example.aop.part4.graduation_work.Healths

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class HealthAdapter(private val text: List<String>, private val url: List<String>): RecyclerView.Adapter<HealthAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthAdapter.ItemViewHolder {
        return HealthAdapter.ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.health_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HealthAdapter.ItemViewHolder, position: Int) {
        holder.bind(text[position], url[position])
        Log.e("HealthAdapter", url[position])
    }

    override fun getItemCount(): Int {
        return 3
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val str: TextView = itemView.findViewById<TextView>(R.id.health_text)
        private val url: YouTubePlayerView = itemView.findViewById(R.id.youtube_player_view)


        fun bind(str: String, url: String){
            this.str.text = str
            var url_token = url.split("v=")

            this.url.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    Log.d("url",url)
                    Log.d("url_token[0]",url_token[0])
                    val videoId = url_token[0]
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }
    }
}
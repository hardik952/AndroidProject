package com.example.medicsapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.medicsapplication.Models.Article
import com.example.medicsapplication.databinding.LayoutArticleAdapterBinding

class ArticleAdapter(var context: Context,var articleList: MutableList<Article>) : Adapter<ArticleAdapter.MyViewHolder>(){

    inner class MyViewHolder(var binding: LayoutArticleAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =  LayoutArticleAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var articles = articleList[position]
        holder.binding.ivArticle.setImageResource(articles.articleImg)
        holder.binding.tvArticle.text = articles.article
        holder.binding.tvDateArticle.text = articles.date
        holder.binding.tvReadArticle.text = articles.time
    }

}
package mk.ukim.finki.my_distributor.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemArticleBinding
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto
import mk.ukim.finki.my_distributor.util.callbacks.ArticleDiffCallback

class ArticlesAdapter(
    private var articles: List<ArticleDto>,
    private val onAddClicked: (ArticleDto) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleDto) {
            binding.articleName.text = article.name
            binding.articlePrice.text = "${article.price}"
            binding.addArticleButton.setOnClickListener { onAddClicked(article) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    fun updateData(newArticles: List<ArticleDto>) {
        val diffCallback = ArticleDiffCallback(this.articles, newArticles)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.articles = newArticles
        diffResult.dispatchUpdatesTo(this)
    }
}
package mk.ukim.finki.my_distributor.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto

class ArticlesAdapter(
    private var articles: List<ArticleDto>,
    private val onArticleClicked: (ArticleDto) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(article: ArticleDto){
                    binding.articleName.text = article.name
                    binding.articlePrice.text = "${article.price}"
                    binding.root.setOnClickListner { onArticleClicked(article) }

                }
            }
}
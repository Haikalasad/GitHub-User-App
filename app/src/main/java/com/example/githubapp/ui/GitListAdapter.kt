import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.response.ItemsItem
import com.example.githubapp.databinding.ItemRowBinding
import com.example.githubapp.ui.detail.DetailActivity

class GitListAdapter: ListAdapter<ItemsItem, GitListAdapter.MyViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(val binding : ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ItemsItem){
            binding.tvName.text = item.login
            Glide.with(binding.root).load(item.avatarUrl).into(binding.imgItemPhoto)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("username", item.login)
            context.startActivity(intent)
        }
    }


}
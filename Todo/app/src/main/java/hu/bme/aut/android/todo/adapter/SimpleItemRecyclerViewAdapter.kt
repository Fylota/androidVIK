package hu.bme.aut.android.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.databinding.RowTodoBinding
import hu.bme.aut.android.todo.model.Todo

class SimpleItemRecyclerViewAdapter : ListAdapter<Todo, SimpleItemRecyclerViewAdapter.ViewHolder>(itemCallback) {

    companion object{
        object itemCallback : DiffUtil.ItemCallback<Todo>(){
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var todoList = emptyList<Todo>()

    var itemClickListener: TodoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]

        holder.todo = todo

        holder.binding.tvTitle.text = todo.title
        holder.binding.tvDueDate.text = todo.dueDate

        val resource = when (todo.priority) {
            Todo.Priority.LOW -> R.drawable.ic_low
            Todo.Priority.MEDIUM -> R.drawable.ic_medium
            Todo.Priority.HIGH -> R.drawable.ic_high
        }
        holder.binding.ivPriority.setImageResource(resource)
    }

    fun addItem(todo: Todo) {
        todoList += todo
        submitList(todoList)
    }

    fun addAll(todos: List<Todo>) {
        todoList += todos
        submitList(todoList)
    }

    fun deleteRow(position: Int) {
        todoList = todoList.filterIndexed { index, _ -> index != position }
        submitList(todoList)
    }

    fun shuffleItems() {
        todoList = todoList.shuffled()
        submitList(todoList)
    }

    inner class ViewHolder(val binding: RowTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        var todo: Todo? = null

        init {
            itemView.setOnClickListener {
                todo?.let { todo -> itemClickListener?.onItemClick(todo) }
            }

            itemView.setOnLongClickListener { view ->
                itemClickListener?.onItemLongClick(adapterPosition, view)
                true
            }
        }
    }

    interface TodoItemClickListener {
        fun onItemClick(todo: Todo)
        fun onItemLongClick(position: Int, view: View): Boolean
    }
}

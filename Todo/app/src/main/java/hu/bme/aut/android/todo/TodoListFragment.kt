package hu.bme.aut.android.todo

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.todo.adapter.SimpleItemRecyclerViewAdapter
import hu.bme.aut.android.todo.adapter.SimpleItemRecyclerViewAdapter.*
import hu.bme.aut.android.todo.databinding.FragmentTodoListBinding
import hu.bme.aut.android.todo.databinding.TodoListContentBinding
import hu.bme.aut.android.todo.model.Todo

import java.nio.file.Files.delete

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link TodoDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class TodoListFragment : Fragment(), TodoCreateFragment.TodoCreatedListener, SimpleItemRecyclerViewAdapter.TodoItemClickListener

{

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter
    private  var itemDetailFragmentContainer: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)

        binding.fab?.setOnClickListener {
            simpleItemRecyclerViewAdapter.shuffleItems()
        }
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemCreateTodo) {
            val todoCreateFragment = TodoCreateFragment()
            todoCreateFragment.setTargetFragment(this,1)
            fragmentManager?.let { todoCreateFragment.show(it, "TAG") }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-land)
        itemDetailFragmentContainer = view.findViewById(R.id.todo_detail_nav_container)


        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val demoData = mutableListOf(
            Todo(1, "title1", Todo.Priority.LOW, "2011. 09. 26.", "description1"),
            Todo(2, "title2", Todo.Priority.MEDIUM, "2011. 09. 27.", "description2"),
            Todo(3, "title3", Todo.Priority.HIGH, "2011. 09. 28.", "description3")
        )
        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        simpleItemRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.todo_list).adapter =
            simpleItemRecyclerViewAdapter
    }

    override fun onItemClick(todo: Todo) {
        val bundle = Bundle()
        bundle.putString(
            TodoDetailHostActivity.KEY_DESC,
            todo.description
        )
        if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer!!.findNavController()
                .navigate(R.id.fragment_todo_detail, bundle)
        } else {
            findNavController(this).navigate(R.id.show_todo_detail, bundle)
        }
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        val popup = PopupMenu(requireActivity(), view)
        popup.inflate(R.menu.menu_todo)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> simpleItemRecyclerViewAdapter.deleteRow(position)
            }
            false
        }
        popup.show()
        return false
    }

    override fun onTodoCreated(todo: Todo) {
        simpleItemRecyclerViewAdapter.addItem(todo)
    }

}

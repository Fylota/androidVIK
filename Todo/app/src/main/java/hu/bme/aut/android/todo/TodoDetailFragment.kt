package hu.bme.aut.android.todo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.todo.databinding.FragmentTodoDetailBinding
import hu.bme.aut.android.todo.model.Todo

/**
 * A fragment representing a single Todo detail screen.
 * This fragment is either contained in a [TodoListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class TodoDetailFragment : Fragment() {

    private var selectedTodo: Todo? = null

    private lateinit var _binding: FragmentTodoDetailBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            selectedTodo = Todo(
                id = 0,
                title = "cim",
                priority = Todo.Priority.LOW,
                dueDate = "1987.23.12",
                description = args.getString(TodoDetailHostActivity.KEY_DESC) ?: ""
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoDetail.text = selectedTodo?.description
    }

}

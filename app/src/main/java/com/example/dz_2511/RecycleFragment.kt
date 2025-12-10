package com.example.dz_2511

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dz_2511.databinding.FragmentRecycleBinding


class RecycleFragment : Fragment(),
    ListAdapter.OnItemClickListener{
    private var _binding: FragmentRecycleBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecycleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView

        adapter = ListAdapter()

        adapter.setOnItemClickListener(this)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        initInitialData()

        binding.fab.setOnClickListener {
            adapter.addNumberItem()
            recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun initInitialData() {
        val items = mutableListOf<ListItem>()

        for (i in 1..10) {
            items.add(ListItem(
                id = i,
                title = "Предмет $i",
                type = ItemType.NUMBER,
                description = ""
            ))
        }

        for (i in 11..20) {
            items.add(ListItem(
                id = i,
                title = "Предмет $i",
                type = ItemType.IMAGE,
                description = ""
            ))
        }

        adapter.setItems(items)
    }


    override fun onItemClick(position: Int, item: ListItem, currentDescription: String) {
        showSimpleDescriptionEdit(position, currentDescription)
    }
    private fun showSimpleRenameDialog(position: Int, currentTitle: String) {
        val editText = android.widget.EditText(requireContext()).apply {
            setText(currentTitle)
            setSelection(currentTitle.length)
            hint = "Введите новое название"
        }

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Переименовать элемент")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newTitle = editText.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    adapter.updateItemTitle(position, newTitle)
                    Toast.makeText(
                        requireContext(),
                        "Переименовано: $newTitle",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showSimpleDescriptionEdit(position: Int, currentDescription: String) {
        val editText = android.widget.EditText(requireContext()).apply {
            setText(currentDescription)
            setSelection(currentDescription.length)
            hint = "Введите описание"
        }

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Изменить описание")
            .setMessage("Введите новое описание для элемента:")
            .setView(editText)
            .setPositiveButton("Сохранить") { _, _ ->
                val newDescription = editText.text.toString().trim()
                adapter.updateItemDescription(position, newDescription)

                Toast.makeText(
                    requireContext(),
                    "Описание обновлено",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Отмена", null)
            .setNeutralButton("Очистить") { _, _ ->
                adapter.updateItemDescription(position, "")
            }
            .show()
    }

    override fun onTitleClick(position: Int, item: ListItem, currentTitle: String) {
        showSimpleRenameDialog(position, currentTitle)
    }

}
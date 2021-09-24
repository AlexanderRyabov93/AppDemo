package ru.alexapps.personaldictionary.worddetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexapps.personaldictionary.R
import ru.alexapps.personaldictionary.data.models.Word
import ru.alexapps.personaldictionary.utils.stringArrayToString

class WordsAdapter() : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {
    private var dataSet: Array<Word> = emptyArray()

    fun updateDataSet(newDataSet: Array<Word>) {
        // TODO: 21.06.2021  Diff utils
        dataSet = newDataSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_word_item, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = dataSet[position]
        holder.wordTextView.text = word.word.capitalize()
        holder.lexicalCategoryTextView.text = word.lexicalCategory.name
        if (word.senses.isNotEmpty()) {
            val sense = word.senses[0]
            holder.definitionTextView.text = stringArrayToString(sense.definitions)
            holder.examplesTextView.text = stringArrayToString(sense.examples)
        } else {
            holder.definitionTextView.text = ""
            holder.examplesTextView.text = ""
        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTextView: TextView = itemView.findViewById(R.id.word_textView)
        val lexicalCategoryTextView: TextView =
            itemView.findViewById(R.id.lexical_category_textView)
        val definitionTextView: TextView = itemView.findViewById(R.id.definition_textView)
        val examplesTextView: TextView = itemView.findViewById(R.id.examples_textView)
    }

}
package ru.alexapps.personaldictionary.worddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.alexapps.personaldictionary.R

@AndroidEntryPoint
class WordDetailsFragment : Fragment() {
    private val wordDetailsViewModel: WordDetailsViewModel by viewModels()
    private lateinit var wordAdapter: WordsAdapter
    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_word_details, container, false)
        initViews(rootView)
        return rootView
    }

    private fun initViews(rootView: View) {
        val recyclerView: RecyclerView = rootView.findViewById(R.id.word_details_recycler)
        wordAdapter = WordsAdapter()
        recyclerView.adapter = wordAdapter
        swipeToRefreshLayout = rootView.findViewById(R.id.swipe_to_refresh)
        swipeToRefreshLayout.setOnRefreshListener {
            wordDetailsViewModel.refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        wordDetailsViewModel.loadingLiveData.observe(viewLifecycleOwner,  {
            swipeToRefreshLayout.isRefreshing = it

        })
        wordDetailsViewModel.wordsLiveData.observe(viewLifecycleOwner, {
            wordAdapter.updateDataSet(it.toTypedArray())
        })
    }

}
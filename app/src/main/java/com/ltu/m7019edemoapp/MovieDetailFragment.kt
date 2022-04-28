package com.ltu.m7019edemoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.m7019edemoapp.database.MovieDatabase
import com.ltu.m7019edemoapp.database.MovieDatabaseDao
import com.ltu.m7019edemoapp.databinding.FragmentMovieDetailBinding
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.viewmodel.MovieDetailViewModel
import com.ltu.m7019edemoapp.viewmodel.MovieDetailViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var viewModelFactory: MovieDetailViewModelFactory
    private lateinit var movieDatabaseDao: MovieDatabaseDao

    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie

        val application = requireNotNull(this.activity).application
        movieDatabaseDao = MovieDatabase.getDatabase(application).movieDatabaseDao()

        viewModelFactory = MovieDetailViewModelFactory(movieDatabaseDao, application, movie)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            isFavorite?.let {
                when (isFavorite) {
                    true -> {
                        binding.saveToDBButtonView.visibility = View.GONE
                        binding.removeFromDBButtonView.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.saveToDBButtonView.visibility = View.VISIBLE
                        binding.removeFromDBButtonView.visibility = View.GONE
                    }
                }
            }

        }

        binding.movie = movie
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieDetailToMovieListButtonView.setOnClickListener {
            findNavController().navigate(R.id.action_MovieDetailFragment_to_MovieListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
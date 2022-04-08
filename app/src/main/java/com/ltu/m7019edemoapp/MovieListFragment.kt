package com.ltu.m7019edemoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ltu.m7019edemoapp.database.Movies
import com.ltu.m7019edemoapp.databinding.FragmentMovieListBinding
import com.ltu.m7019edemoapp.databinding.MovieListItemBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val movies = Movies()

//        binding.movies = movies;

        movies.list.forEach { movie ->
//            val movieListItemBinding: MovieListItemBinding = MovieListItemBinding.inflate(inflater, container, false)
            val movieListItemBinding: MovieListItemBinding = DataBindingUtil.inflate(inflater, R.layout.movie_list_item, container, false)
            movieListItemBinding.movie = movie
            movieListItemBinding.root.setOnClickListener {
                this.findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie))
            }
            binding.movieListLinearLayout.addView(movieListItemBinding.root)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
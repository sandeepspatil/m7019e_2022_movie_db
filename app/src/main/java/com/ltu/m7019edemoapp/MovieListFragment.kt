package com.ltu.m7019edemoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e2022prep.adapter.MovieListAdapter
import com.ltu.m7019e2022prep.adapter.MovieListClickListener
import com.ltu.m7019edemoapp.database.MovieDatabase
import com.ltu.m7019edemoapp.database.MovieDatabaseDao
import com.ltu.m7019edemoapp.database.Movies
import com.ltu.m7019edemoapp.databinding.FragmentMovieListBinding
import com.ltu.m7019edemoapp.databinding.MovieListItemBinding
import com.ltu.m7019edemoapp.network.DataFetchStatus
import com.ltu.m7019edemoapp.viewmodel.MovieListViewModel
import com.ltu.m7019edemoapp.viewmodel.MovieListViewModelFactory
import timber.log.Timber


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieListViewModel
    private lateinit var viewModelFactory: MovieListViewModelFactory
    private lateinit var movieDatabaseDao: MovieDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        //val movies = Movies()

        val application = requireNotNull(this.activity).application
        movieDatabaseDao = MovieDatabase.getDatabase(application).movieDatabaseDao()

        viewModelFactory = MovieListViewModelFactory(movieDatabaseDao, application)
        viewModel =ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)

        val movieListAdapter = MovieListAdapter(
            MovieListClickListener { movie ->
                viewModel.onMovieListItemClicked(movie)
            })
        binding.movieListRecycleView.adapter = movieListAdapter

        viewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when(status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImage.visibility = View.GONE
                    }
                }
            }

        }

        viewModel.movies.observe(viewLifecycleOwner) { movieList ->
            movieList?.let {
                movieListAdapter.submitList(movieList)
            }
        }

        viewModel.navigateToMovieDetail.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie)
                )
                viewModel.onMovieDetailNavigated()
            }
        }

        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_popular_movies -> {
                Timber.i("Popular Movies Clicked")
                viewModel.getPopularMovies()
                true
            }
            R.id.action_top_rated_movies -> {
                viewModel.getTopRatedMovies()
                Timber.i("Top Rated Movies Clicked")
                true
            }
            R.id.action_saved_movies -> {
                viewModel.getSavedMovies()
                Timber.i("Saved Movies Clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
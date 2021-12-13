package ru.geekbrains.androidkotlin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.androidkotlin.viewmodel.MainViewModel
import ru.geekbrains.androidkotlin.model.Weather
import ru.geekbrains.androidkotlin.databinding.MainFragmentBinding
import ru.geekbrains.androidkotlin.viewmodel.AppState

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Подписались на изменения live Data
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })
        // Запросили новые данные
        viewModel.getWeather()

    }

    private fun render(state: AppState) {
        when (state) {
            is AppState.Success -> {
                binding.loadingContainer.visibility = View.GONE
                val weather = state.weather as Weather
                binding.cityname.text = weather.city
                binding.temperature.text = weather.temp.toString()
                binding.conditionfrg.text = weather.condition
                binding.winddir.text = weather.wind_dir
                binding.windspeed.text = weather.wind_speed.toString()
            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Попробовать снова") {
                        // Запросили новые данные
                        viewModel.getWeather()
                    }.show()
            }
            is AppState.Loading -> {
                binding.loadingContainer.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
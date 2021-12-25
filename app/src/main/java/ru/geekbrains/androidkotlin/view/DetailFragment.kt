package ru.geekbrains.androidkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.geekbrains.androidkotlin.databinding.DetailFragmentBinding
import ru.geekbrains.androidkotlin.model.Weather
import ru.geekbrains.androidkotlin.model.WeatherDTO
import ru.geekbrains.androidkotlin.model.WeatherLoader
import ru.geekbrains.androidkotlin.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>("WEATHER_EXTRA")?.let { weather ->

            binding.cityname.text = weather.city.name
            binding.temperature.text = weather.temp.toString()
            binding.conditionfrg.text = weather.condition


            WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener {
                override fun onLoaded(weatherDTO: WeatherDTO) {
                    weatherDTO.fact?.let { fact ->
                        binding.temperature.text = fact.temp?.toString()
                        binding.conditionfrg.text = fact.condition
                    }

                }

                override fun onFailed(throwable: Throwable) {
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
                }

            })

            //binding.cityname.text = weather.city.name
         //   binding.temperature.text = weather.temp.toString()
       //     binding.conditionfrg.text = weather.condition
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
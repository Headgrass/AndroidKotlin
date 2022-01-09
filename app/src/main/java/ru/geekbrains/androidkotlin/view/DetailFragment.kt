package ru.geekbrains.androidkotlin.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import ru.geekbrains.androidkotlin.R
import ru.geekbrains.androidkotlin.databinding.DetailFragmentBinding
import ru.geekbrains.androidkotlin.model.*
import ru.geekbrains.androidkotlin.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val listener = Repository.OnLoadListener {
        RepositoryImpl.getWeatherFromServer()?.let { weather ->
            binding.temperature.text = weather.temp.toString()
            binding.conditionfrg.text = weather.condition
/*
            binding.weatherImage.load("https://picsum.photos/300/300") {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_flag_24)
                transformations(CircleCropTransformation())
            }*/



            val request = ImageRequest.Builder(requireContext())
                .data("https://yastatic.ru/weather/i/icons/funky/dark/${weather.icon}.svg")
                .target(binding.weatherImage)
                .build()

            ImageLoader.Builder(requireContext())
                .componentRegistry {
                    add(SvgDecoder(requireContext())) }
                .build()
                .enqueue(request)

        } ?: Toast.makeText(context, "ОШИБКА", Toast.LENGTH_LONG).show()
    }
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RepositoryImpl.addLoadedListener(listener)

        arguments?.getParcelable<Weather>("WEATHER_EXTRA")?.let { weather ->

            binding.cityname.text = weather.city.name
            binding.temperature.text = weather.temp.toString()
            binding.conditionfrg.text = weather.condition

            requireActivity().startService(
                Intent(
                    requireContext(),
                    WeatherService::class.java
                ).apply {
                    putExtra("WEATHER_EXTRA", weather)
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RepositoryImpl.removeLoadedListener(listener)
        _binding = null
    }
}
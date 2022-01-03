package ru.geekbrains.androidkotlin.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
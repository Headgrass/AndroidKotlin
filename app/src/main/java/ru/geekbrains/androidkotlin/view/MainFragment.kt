package ru.geekbrains.androidkotlin.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geekbrains.androidkotlin.R
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
    private val adapter = MainAdapter()
    private var isRussian = true;

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

        binding.mainRV.adapter = adapter

        adapter.listener = MainAdapter.OnItemClick { weather ->
            val bundle = Bundle().apply {
                putParcelable("WEATHER_EXTRA", weather)
            }
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
            }
        }

        // Подписались на изменения live Data
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })
        // Запросили новые данные
        viewModel.getWeatherFromLocalSourceRus()

        binding.mainFAB.setOnClickListener {
            isRussian = !isRussian

            if (isRussian) {
                viewModel.getWeatherFromLocalSourceRus()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
            } else {
                viewModel.getWeatherFromLocalSourceWorld()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_flag_24)
            }
        }
        binding.historyFAB.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }

    }

    private fun render(state: AppState) {

        when (state) {
            is AppState.Success<*> -> {
                val weather: List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.hide()
            }
            is AppState.Error -> {
                binding.loadingContainer.show()
                binding.root.showSnackBar(state.error.message.toString(),
                    "Попробовать снова",
                {
                    // Запросили новые данные
                    viewModel.getWeatherFromLocalSourceRus()
                })
            }
            is AppState.Loading -> {
                binding.loadingContainer.show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
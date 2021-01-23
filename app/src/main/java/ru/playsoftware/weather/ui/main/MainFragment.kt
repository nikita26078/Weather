package ru.playsoftware.weather.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ru.playsoftware.weather.R
import ru.playsoftware.weather.adapters.WeatherAdapter
import ru.playsoftware.weather.data.WeatherData
import ru.playsoftware.weather.data.WeatherRepository
import ru.playsoftware.weather.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var compositeDisposable = CompositeDisposable()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val root = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = MainViewModelFactory(WeatherRepository())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.weatherList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.weatherList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val disposable = viewModel.weatherData
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherData>() {
                    override fun onSuccess(t: WeatherData) {
                        binding.empty.visibility = View.GONE
                        val adapter = WeatherAdapter(t.list)
                        binding.weatherList.adapter = adapter
                    }

                    override fun onError(e: Throwable) {
                        binding.empty.visibility = View.VISIBLE
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
        compositeDisposable.add(disposable)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_visit -> {
                val url = "http://openweathermap.org"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

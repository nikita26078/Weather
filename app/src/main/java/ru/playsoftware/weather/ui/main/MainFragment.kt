package ru.playsoftware.weather.ui.main

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.*
import ru.playsoftware.weather.R
import ru.playsoftware.weather.adapters.WeatherAdapter
import ru.playsoftware.weather.data.WeatherData
import ru.playsoftware.weather.data.WeatherRepository

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = MainViewModelFactory(WeatherRepository())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        weatherList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        weatherList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val disposable = viewModel.weatherData
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherData>() {
                    override fun onSuccess(t: WeatherData) {
                        empty.visibility = View.GONE
                        val adapter = WeatherAdapter(t.list)
                        weatherList.adapter = adapter
                    }

                    override fun onError(e: Throwable) {
                        empty.visibility = View.VISIBLE
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

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}

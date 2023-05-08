package com.example.storyapp.ui.fragment.maps

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.storyapp.R
import com.example.storyapp.data.Resource
import com.example.storyapp.data.api.stories.ResponseStories
import com.example.storyapp.data.api.story.Story
import com.example.storyapp.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private lateinit var mapFragment: SupportMapFragment
    private val viewModel: MapViewModel by viewModels()

    private var callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        mMap = googleMap
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getMapStory()
        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            handleStoriesResult(it)
        }
    }

    private fun handleStoriesResult(status: Resource<ResponseStories>) {
        when (status) {
            is Resource.Success -> status.data?.let {
                if(it.error){
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                mapCallbackFactory(it.stories)
            }
            is Resource.DataError -> {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mapCallbackFactory(stories: List<Story>){
        stories.forEach {
            Log.d("map_stories_output", "mapCallbackFactory: $it")
            it.lat?.let { lat ->
                it.lon?.let { lon ->
                    Log.d("map_stories_output_lat", "mapCallbackFactory: ${it.lat}")
                    val coordinate = LatLng(lat, lon)
                    mMap.addMarker(MarkerOptions().position(coordinate).title(it.id))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate))
                }
            }
        }
    }
}
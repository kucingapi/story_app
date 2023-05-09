package com.example.storyapp.ui.fragment.stories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.DataRepositorySource
import com.example.storyapp.data.api.story.Story
import com.example.storyapp.data.paging.StoryPagingSource
import com.example.storyapp.ui.fragment.stories.adapter.StoryAdapter
import com.example.storyapp.util.DataDummy
import com.example.storyapp.util.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.internal.wait
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoriesViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StoriesViewModel
    @Mock private lateinit var dataRepository: DataRepository
    private val expectedStory = MutableLiveData<PagingData<Story>>()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Before
    fun setup(){
        viewModel = StoriesViewModel(dataRepository)
    }

    @Test
    fun `when Get Story Should Not Null and Return Success`() = runTest {
        val dummyStory = DataDummy.generateDummyStories()
        val data: PagingData<Story> = StoriesPagingSource.snapshot(dummyStory)
        expectedStory.value = data
        `when`(dataRepository.getStoriesPaging()).thenReturn(expectedStory)
        val actualStory: PagingData<Story> = runBlocking { viewModel.getStoryPaging().asFlow().first() }
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory[0], differ.snapshot()[0])
    }
    @Test
    fun `when Get Story Should Return Zero If No Data`() = runTest {
        val dummyStory = listOf<Story>()
        val data: PagingData<Story> = StoriesPagingSource.snapshot(dummyStory)
        expectedStory.value = data
        `when`(dataRepository.getStoriesPaging()).thenReturn(expectedStory)
        val actualStory: PagingData<Story> = runBlocking { viewModel.getStoryPaging().asFlow().first() }
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(0, differ.snapshot().items.size)
    }

}

class StoriesPagingSource : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
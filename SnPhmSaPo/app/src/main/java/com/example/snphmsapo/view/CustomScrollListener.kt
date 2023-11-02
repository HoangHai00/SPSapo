package com.example.snphmsapo.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snphmsapo.contract.ScrollListenerActions

class CustomScrollListener(
    private val scrollListenerActions: ScrollListenerActions,
) : RecyclerView.OnScrollListener() {
    private var isLoading= true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if  ( firstVisibleItemPosition+ visibleItemCount >= totalItemCount && firstVisibleItemPosition >= 0)
                {
            if (isLoading) {
                scrollListenerActions.loadNextPageData()
            }
        }

    }
    fun enableLoading(isLoading:Boolean){
        this.isLoading = isLoading
    }
}
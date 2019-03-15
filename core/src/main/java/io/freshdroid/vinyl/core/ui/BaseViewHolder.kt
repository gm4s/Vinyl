package io.freshdroid.vinyl.core.ui

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder(
        private val view: View
) : RecyclerView.ViewHolder(view) {

    /**
     * Populate a view with data that was bound in `bindData`.
     *
     *
     * Prefer creating subscriptions to a viewmodel in the constructor, then using #{link #bindData} to
     * send new data to the viewmodel.
     */
    open fun onBind() {

    }

    /**
     * Implementations of this should inspect `data` to set instance variables in the view holder that
     * `onBind` can then use without worrying about type safety.
     *
     * @throws Exception Raised when binding is unsuccessful.
     */
    @Throws(Exception::class)
    abstract fun bindData(data: Any)

    protected fun view(): View = view

    protected fun context(): Context = view.context

}
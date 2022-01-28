package com.domainname.app.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.domainname.app.R
import com.domainname.app.base.BaseAdapter
import com.domainname.app.databinding.AdapterItemLayoutBinding


class RecyclerViewAdapter(itemList: List<Model>, private val onItemClickListener: (model: Model) -> Unit) :
    BaseAdapter<Model, AdapterItemUpcomingLayoutBinding>(itemList) {

    override fun viewBinding(parent: ViewGroup, viewType: Int): ViewBinding = AdapterItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(holder: BaseViewHolder, model: BaseModel) {
        super.bind(holder, model)
        (holder.viewBinding as AdapterItemLayoutBinding).apply {

        }
    }
}
package com.domainname.app.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.domainname.common.BaseModel


abstract class BaseRecyclerViewAdapter<Model : BaseModel, VB : ViewBinding>(private val itemList: List<Model>) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter<Model, VB>.BaseViewHolder>() {

    abstract fun viewBinding(parent: ViewGroup, viewType: Int): ViewBinding

    open fun bind(holder: BaseViewHolder, model: BaseModel) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = BaseViewHolder(viewBinding(parent, viewType))

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder, itemList[position])
    }

    inner class BaseViewHolder(val viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun getItemCount(): Int = itemList.size
}

package com.sandeep.mvvmdemo.feature.converter.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandeep.mvvmdemo.R
import com.sandeep.mvvmdemo.core.extension.inflate
import com.sandeep.mvvmdemo.feature.converter.uimodel.ConvertedCurrency
import kotlinx.android.synthetic.main.row_converted_currency.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class CurrenciesAdapter
@Inject constructor() : RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    internal var collection: List<ConvertedCurrency> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.row_converted_currency))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: ConvertedCurrency) {
            itemView.converted_currency.text = "${currency.currencyType} (${"%.2f".format(currency.rate)})"
            itemView.converted_currency_value.text = currency.convertedValue.toString()
        }
    }
}

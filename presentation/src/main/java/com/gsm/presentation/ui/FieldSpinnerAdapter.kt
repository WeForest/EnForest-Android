package com.gsm.presentation.ui

import android.content.Context
import android.widget.BaseAdapter

/*
class FieldSpinnerAdapter(
    context: Context,
    tagArray: Array<String>
) : BaseAdapter() {
    val mContext = context
    val tagArray = tagArray
    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(mContext)
            .inflate(R.layout.post_create_tag_spinner_item, null)
        view.findViewById<TextView>(R.id.choice_tag).text = tagArray[position]
        view.findViewById<TextView>(R.id.choice_tag)
            .setTextColor(ContextCompat.getColor(mContext, R.color.white))

        return view
    }

    override fun getItem(p0: Int): Any {
        return tagArray
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return tagArray.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(mContext)
            .inflate(R.layout.post_create_tag_spinner_item, null)

        view.findViewById<TextView>(R.id.choice_tag).text = tagArray[position]
        view.findViewById<TextView>(R.id.choice_tag)
            .setTextColor(ContextCompat.getColor(mContext, R.color.black))

        return view
    }

}*/

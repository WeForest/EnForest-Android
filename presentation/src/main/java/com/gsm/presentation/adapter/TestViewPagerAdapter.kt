package com.gsm.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gsm.presentation.R

class TestViewPagerAdapter(
    testColorItemList: ArrayList<String>,
    textSetTestLevel: ArrayList<Int>,
    fragment: Fragment
) : RecyclerView.Adapter<TestViewPagerAdapter.PagerViewHolder>() {

    var item = testColorItemList
    var textLevelItem = textSetTestLevel
    var fragment = fragment

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)) {

        val itemImg = itemView.findViewById<ImageView>(R.id.test_img)
        val itemTextLevel = itemView.findViewById<TextView>(R.id.test_name_textView)

        fun onClick(p: Int) {
            itemView.setOnClickListener {
                when (p) {
                    0 -> {
                        if (findNavController(fragment).currentDestination?.id == R.id.testMainFragment) {
                            fragment.findNavController()
                                .navigate(R.id.action_testMainFragment_to_easyTestFragment)
                        }else{
                            fragment.findNavController()
                                .navigate(R.id.action_testMainFragment2_to_easyTestFragment2)
                        }
                    }
                    1 -> {}
                    2 -> {}
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder((parent))
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.itemImg.setColorFilter(Color.parseColor(item[position]))
        holder.itemTextLevel.setText(textLevelItem[position])

        holder.onClick(position)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}
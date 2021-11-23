package com.gsm.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.gsm.presentation.databinding.FragmentTestMainBinding

class TestViewPagerAdapter(testColorItemList : ArrayList<String>, textSetTestLevel : ArrayList<Int>, fragment : Fragment) : RecyclerView.Adapter<TestViewPagerAdapter.PagerViewHolder>() {

    var item = testColorItemList
    var textLevelItem = textSetTestLevel
    var fragment = fragment

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)){

        val itemImg = itemView.findViewById<ImageView>(R.id.test_img)
        val itemTextLevel = itemView.findViewById<TextView>(R.id.test_name_textView)

        fun onClick(p : Int){
            itemView.setOnClickListener {
                when(p)
                {
                    0 -> {fragment.findNavController().navigate(R.id.action_testMainFragment_to_easyTestFragment)}
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
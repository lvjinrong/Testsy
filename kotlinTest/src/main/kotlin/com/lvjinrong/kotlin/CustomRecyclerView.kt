package com.lvjinrong.kotlin

import android.content.Context
import android.util.AttributeSet
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by lvjinrong on 2016/6/27.
 * realize by kotlin
 */

/**
 * 经常会碰到在ListView中点击其中一个Item，会一并触发其子控件的点击事件，
 * 例如Item中的Button、ImageButton等，导致了点击Item中Button以外区域也会触发Button点击事件。
 * 在网上找了相关方法，这里记录下，亲测可行..
 * 1、在Item的xml文件根元素中添加属性：android:descendantFocusability="blocksDescendants"
 * 2、在冲突的子控件中添加属性：android:focusable="false"和android:clickable="true"
 */

class CustomRecyclerView : RecyclerView{
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context , attributeSet , defStyle)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context , attributeSet , 0)
    constructor(context: Context) : super(context , null)

    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun onItemClick( parent: RecyclerView , itemView: View , index: Int)
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    override fun addView(child: View?, index: Int) {
        super.addView(child, index)
        child?.setOnClickListener { view ->
            onItemClickListener?.onItemClick(this, child, index)
        }
    }
}

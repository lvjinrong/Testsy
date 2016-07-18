package com.lvjinrong.kotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find

/**
 * Created by lvjinrong on 2016/6/21.
 *
 */
class Kotlin3Adapter( internal var data: List<String> ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onItemClickListener : Kotlin3Adapter.OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View , position: Int)
    }

    fun setOnItemClickListener(onItemClickListener : Kotlin3Adapter.OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder1 = holder as ViewHolder
        holder1.tv1.text = data[position % 7]
        holder1.tv2.text = "Second..." + position
        when(position % 2){
            0 -> holder1.itemView.setBackgroundResource(android.R.color.holo_orange_light)
            1 -> holder1.itemView.setBackgroundResource(android.R.color.holo_green_light)
        }
        holder1.itemView.setOnClickListener { view ->
            onItemClickListener?.onItemClick(holder1.itemView , position)
        }
    }

    override fun getItemCount(): Int {
        return 22
    }


    /**
     * modifiers ====>>>>> 包括 classModifier 和_accessModifier_:

        classModifier: 类属性修饰符，标示类本身特性。
            abstract //抽象类标示
            final  //标示类不可继承，默认属性
            enum  //标示类为枚举
            open  //类可继承，类默认是final的
            annotation  //注解类

        accessModifier: 访问权限修饰符
            private //仅在同一个文件中可见
            protected //同一个文件中或子类可见
            public //所有调用的地方都可见 --- default
            internal //同一个模块- Module -中可见
     *
     */
    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv1: TextView
        internal var tv2: TextView
        init {
            tv1 = itemView.find<TextView>(R.id.KA_tv1)
            tv2 = itemView.findViewById(R.id.KA_tv2) as TextView
        }
    }
}


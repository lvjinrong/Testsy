package com.example.myapplication

import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable

/**
 * 加载R文件里面的内容

 * @author king
 * *
 * @QQ:595163260
 * *
 * @version 2014年10月18日  下午11:46:29
 */
object Res {

    // 文件路径名
    private var pkgName: String? = null
    // R文件的对象
    private var resources: Resources? = null

    // 初始化文件夹路径和R资源
    fun init(context: Context) {
        pkgName = context.packageName
        resources = context.resources
    }

    /**
     * layout文件夹下的xml文件id获取

     */
    fun getLayoutID(layoutName: String): Int {
        return resources!!.getIdentifier(layoutName, "layout", pkgName)
    }

    // 获取到控件的ID
    fun getWidgetID(widgetName: String): Int {
        return resources!!.getIdentifier(widgetName, "id", pkgName)
    }

    /**
     * anim文件夹下的xml文件id获取

     */
    fun getAnimID(animName: String): Int {
        return resources!!.getIdentifier(animName, "anim", pkgName)
    }

    /**
     * xml文件夹下id获取

     */
    fun getXmlID(xmlName: String): Int {
        return resources!!.getIdentifier(xmlName, "xml", pkgName)
    }

    // 获取xml文件
    fun getXml(xmlName: String): XmlResourceParser {
        val xmlId = getXmlID(xmlName)
        return resources!!.getXml(xmlId)
    }

    /**
     * raw文件夹下id获取

     */
    fun getRawID(rawName: String): Int {
        return resources!!.getIdentifier(rawName, "raw", pkgName)
    }

    /**
     * drawable文件夹下文件的id

     */
    fun getDrawableID(drawName: String): Int {
        return resources!!.getIdentifier(drawName, "drawable", pkgName)
    }

    // 获取到Drawable文件
    fun getDrawable(drawName: String): Drawable {
        val drawId = getDrawableID(drawName)
        return resources!!.getDrawable(drawId)
    }

    /**
     * value文件夹

     */
    // 获取到value文件夹下的attr.xml里的元素的id
    fun getAttrID(attrName: String): Int {
        return resources!!.getIdentifier(attrName, "attr", pkgName)
    }

    // 获取到dimen.xml文件里的元素的id
    fun getDimenID(dimenName: String): Int {
        return resources!!.getIdentifier(dimenName, "dimen", pkgName)
    }

    // 获取到color.xml文件里的元素的id
    fun getColorID(colorName: String): Int {
        return resources!!.getIdentifier(colorName, "color", pkgName)
    }

    // 获取到color.xml文件里的元素的id
    fun getColor(colorName: String): Int {
        return resources!!.getColor(getColorID(colorName))
    }

    // 获取到style.xml文件里的元素id
    fun getStyleID(styleName: String): Int {
        return resources!!.getIdentifier(styleName, "style", pkgName)
    }

    // 获取到String.xml文件里的元素id
    fun getStringID(strName: String): Int {
        return resources!!.getIdentifier(strName, "string", pkgName)
    }

    // 获取到String.xml文件里的元素
    fun getString(strName: String): String {
        val strId = getStringID(strName)
        return resources!!.getString(strId)
    }

    // 获取color.xml文件里的integer-array元素
    fun getInteger(strName: String): IntArray {
        return resources!!.getIntArray(resources!!.getIdentifier(strName, "array",
                pkgName))
    }

}

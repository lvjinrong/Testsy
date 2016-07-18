package com.lvjinrong.appwidgetprovider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/*
 * @author : harvic
 * description : blog.csdn.net/harvic880925
 */

public class ExampleAppWidgetProvider extends AppWidgetProvider {

	/*
	  * 在3种情况下会调用OnUpdate()。onUpdate()是在main线程中进行，因此如果处理需要花费时间多于10秒，处理应在service中完成。
	  *（1）在时间间隔到时调用，时间间隔在widget定义的android:updatePeriodMillis中设置；
	  *（2）用户拖拽到主页，widget实例生成。无论有没有设置Configure activity，我们在Android4.4的测试中，当用户拖拽图片至主页时，widget实例生成，会触发onUpdate()，然后再显示activity（如果有）。这点和资料说的不一样，资料认为如果设置了Configure acitivity，就不会在一开始调用onUpdate()，而实验显示当实例生成（包括创建和重启时恢复），都会先调用onUpate()。在本例，由于此时在preference尚未有相关数据，创建实例时不能有效进行数据设置。
	  *（3）机器重启，实例在主页上显示，会再次调用onUpdate()
	  */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	// widget被删除时调用
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	// 最后一个widget被删除时调用
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	// 第一个widget被创建时调用
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	// 接收广播的回调函数
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}
}

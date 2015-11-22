package com.paper.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.paper.R;
import com.paper.view.fragment.ImageGridViewFragment;
import com.paper.view.fragment.NavDrawerFragment;

import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.InjectUtil.InjectView;
import dswork.android.lib.core.util.MyStrictMode;
import dswork.android.lib.core.util.bmputil.BitmapLoader;

public class MainActivity extends ActionBarActivity implements NavDrawerFragment.NavigationDrawerCallbacks
{
	//标记要注入的控件
	@InjectView(id=R.id.drawer_layout) DrawerLayout mDrawerLayout;
	@InjectView(id=R.id.toolbar) Toolbar mToolbar;
	//变量
	private NavDrawerFragment mNavDrawerFragment;
	private static CharSequence mTitle;
	public static int clickKeyBackTimes = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			// inside your activity (if you did not enable transitions in your theme)
			getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
			// set transition
			getWindow().setEnterTransition(new Explode());//普通transition的进入效果
			getWindow().setExitTransition(new Explode());//普通transition的退出效果
			getWindow().setSharedElementEnterTransition(new ChangeImageTransform());//共享元素transition的进入效果
			getWindow().setSharedElementExitTransition(new ChangeImageTransform());//共享元素transition的退出效果
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		MyStrictMode.setPolicy();//webapp需引用
		InjectUtil.injectView(this);//注入控件

		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		mTitle = getTitle();
		// Set up the drawer.
		mNavDrawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
	}

	@Override
	protected void onPause()
	{
		BitmapLoader.fluchCache();
		clickKeyBackTimes = 0;
		super.onPause();
	}

	@Override
	protected void onDestroy()
	{
		BitmapLoader.cancelAllTasks();
		super.onDestroy();
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        switch (keyCode)  
        {
        	case KeyEvent.KEYCODE_BACK:
        		if(mNavDrawerFragment.isDrawerOpen())
	            {
	            	mDrawerLayout.closeDrawers();
	            }
        		else
        		{
        			if(clickKeyBackTimes == 0)
        			{
        				clickKeyBackTimes++;
        				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        			}
        			else
        			{
        				this.finish();
        			}
        		}
        		return true;  
        }  
        return false;// 如果返回true就是代表告诉系统“所有的按键我全要了”，达到了屏蔽所有按键的效果（home和菜单键貌似还不能屏蔽）  
  
    } 

	@Override
	public void onNavigationDrawerItemSelected(int position, long data_id, CharSequence data_name)
	{
		// 根据导航选项替换fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new ImageGridViewFragment(position + 1, data_id, data_name, new ImageGridViewFragment.Callbacks()
		{
			@Override
			public void getNavSectionName(CharSequence sectionName)
			{
				//点击导航后才可修改actionbar title
				if(sectionName=="") return;
				mTitle = getString(R.string.app_name)+" > "+sectionName;
			}
		})).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if(!mNavDrawerFragment.isDrawerOpen())
		{
			getSupportActionBar().setTitle(mTitle);//切换菜单title
//			getMenuInflater().inflate(R.menu.main, menu);//显示菜单按钮
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}

}

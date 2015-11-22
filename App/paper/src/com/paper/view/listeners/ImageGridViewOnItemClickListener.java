package com.paper.view.listeners;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.paper.R;
import com.paper.model.PaperImage;
import com.paper.view.ImageViewPagerActivity;

import java.io.Serializable;
import java.util.List;

public class ImageGridViewOnItemClickListener implements View.OnClickListener
{
	private Context ctx;
	private long id;//被点击的图片id
	private long pid;//对应菜单项id
	private List<PaperImage> dataList;
	private View v;
	
	public ImageGridViewOnItemClickListener(Context ctx, long id, long pid, List<PaperImage> dataList, View v)
	{
		super();
		this.ctx = ctx;
		this.id = id;
		this.pid = pid;
		this.dataList = dataList;
		this.v = v;
	}

	@Override
	public void onClick(View v)
	{
		Bundle b = new Bundle();
		b.putLong("id", id);
		b.putLong("pid", pid);
		b.putSerializable("dataList", (Serializable) dataList);
		//跳转到图片分页Activity
//		ctx.startActivity(new Intent().setClass(ctx, ImageViewPagerActivity.class).putExtra("info", b));
//		ctx.overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
//		ActivityOptionsCompat.makeCustomAnimation(ctx, R.anim.abc_fade_in, R.anim.abc_fade_out);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			//android L share element 跳转动画（minSdkVersion:21）
			Intent intent = new Intent().setClass(ctx, ImageViewPagerActivity.class).putExtra("info", b);
			String transitionName = ctx.getString(R.string.transition_album_cover);
			ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) ctx, v, transitionName);
			ctx.startActivity(intent, options.toBundle());
		}
		else
		{
			ctx.startActivity(new Intent().setClass(ctx, ImageViewPagerActivity.class).putExtra("info", b));
		}
	}
}

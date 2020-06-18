package com.labs.taqwa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.labs.taqwa.R;

public class SlideImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImage;

    public SlideImageAdapter(Context context, int[] iImage){
        this.mContext = context;
        this.mImage = iImage;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.list_image, container, false);
        ImageView imageView = viewItem.findViewById(R.id.imageView);
        imageView.setImageResource(mImage[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        return mImage.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}

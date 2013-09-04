package larry.effectdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class VideoSplashActivity extends FragmentActivity {

	VideoFragment[] mFragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_video);
		mFragments = new VideoFragment[3];
		for (int i = 0; i < mFragments.length; i++) {
			mFragments[i] = VideoFragment.newInstance(i);
		}
		ViewPager pager = (ViewPager)findViewById(R.id.splash_pager);
		pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				mFragments[pos].play();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			return mFragments[pos];
		}

		@Override
		public int getCount() {
			return 3;
		}
		
	}

}

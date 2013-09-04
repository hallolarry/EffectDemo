package larry.effectdemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

public class VideoFragment extends Fragment implements SurfaceHolder.Callback {

	private static final String TAG = "VideoFragment";
	SurfaceView mSurface;
	SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	int mIndex;
	boolean hasActiveHolder;

	public static VideoFragment newInstance(int pos) {
		VideoFragment f = new VideoFragment();
		f.mIndex = pos + 1;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mPlayer = new MediaPlayer();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.splash_video_fragment, null);
		mSurface = (SurfaceView) v.findViewById(R.id.splash_surface);
		mHolder = mSurface.getHolder();
		mHolder.addCallback(this);
		return v;
	}

	@Override
	public void onDestroy() {
		mPlayer.release();
		mPlayer = null;
		super.onDestroy();
	}

	public void play() {
		mPlayer.start();
	}

	public void stop() {
		mPlayer.stop();
	}

	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		Log.d(TAG, getId() + "  setUserVisibleHintsetUserVisibleHint"
				+ isVisibleToUser);
//		if (isVisibleToUser == true) {
//			play();
//		} else if (isVisibleToUser == false) {
//			stop();
//		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mPlayer.setDisplay(mHolder);
			mPlayer.reset();
			mPlayer.setDataSource("/sdcard/" + mIndex + ".mp4");
			mPlayer.prepare();
		} catch (Exception e) {

		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mPlayer.stop();
	}
}

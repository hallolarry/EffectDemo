package larry.effectdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;

public class RingView extends View {
	private Paint mPaint;

	int startColor, endColor;

	Handler mHandler;
	int r = 100;
	int a = 0xFF;
	Interpolator mInterpolator;

	public RingView(Context context) {
		super(context);
		initView();
	}

	/**
	 * @see android.view.View#View(android.content.Context,
	 *      android.util.AttributeSet)
	 */
	public RingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private final void initView() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(1);
		mHandler = new Handler();
		mInterpolator = new CycleInterpolator(1.0f);
	}

	/**
	 * Render
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	double[] factors = new double[] { 0.01, 0.03, 0.05, 0.07, 0.09, 0.11, 0.13, 0.15, 0.25, 0.5, 1.0, 0.5, 0.25, 0.15,
			0.13, 0.11, 0.09, 0.07, 0.05, 0.03, 0.01 };

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int color = 0x0000ff;
		for (int i = 0; i < factors.length; i++) {
			double factor = factors[i];
			int alpha = (int) (a * factor);
			mPaint.setColor(alpha << 24 | color);
			canvas.drawCircle(200, 200, r - i, mPaint);
		}
		// mHandler.postDelayed(task, 50);
	}

	Runnable task = new Runnable() {

		@Override
		public void run() {
			r += 10;
			a -= 255 / 18;
			if (r > 200) {
				r = 20;
				a = 0xFF;
			}
			postInvalidate();
		}
	};

	int getA(int color) {
		return color >> 24 & 0xFF;
	}

	int getR(int color) {
		return color >> 16 & 0xFF;
	}

	int getG(int color) {
		return color >> 8 & 0xFF;
	}

	int getB(int color) {
		return color & 0xFF;
	}
}

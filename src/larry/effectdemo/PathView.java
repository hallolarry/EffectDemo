package larry.effectdemo;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.View;

class PathView extends View {

	private Paint mPaint;
	private RadialGradient mRadialGradient;

	public PathView(Context context) {
		super(context);
		setFocusable(true);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		// mPaint.setColor(0x550000EE);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					angel += 10;
					if (r >= 360) {
						r = 0;
					}
					postInvalidate();
				}
			};
		}.start();
	}

	int r = 10;
	int mCenterX = 200, mCenterY = 200;
	int mCenterR = 100;
	int mX = 300, mY = mCenterY;
	float angel = 0;

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		float a = angel;
		for (int i = 0; i < 5; i++) {
			a -= 10;
			mX = (int) (mCenterX + mCenterR * Math.cos(a * Math.PI / 180));
			mY = (int) (mCenterY + mCenterR * Math.sin(a * Math.PI / 180));
			mRadialGradient = new RadialGradient(mX, mY, 10, new int[] {
					0xFFFFFF | ((255 - (50 * i)) << 24), 0x00FFFFFF }, null,
					Shader.TileMode.REPEAT);
			mPaint.setShader(mRadialGradient);
			canvas.drawCircle(mX, mY, r, mPaint);
		}
	}
}
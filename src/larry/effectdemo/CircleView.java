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
import android.util.AttributeSet;
import android.view.View;

class CircleView extends View {

	private Paint mPaint;
	private EmbossMaskFilter mEmboss;
	private BlurMaskFilter mBlur;
	private RadialGradient mRadialGradient;
	private Rect mRect;

	/**
	 * @see android.view.View#View(android.content.Context,
	 *      android.util.AttributeSet)
	 */
	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public CircleView(Context context) {
		super(context);
		setFocusable(true);
		initView();
	}

	private void initView() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		// mPaint.setColor(0x550000EE);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		// mPaint.setStrokeWidth(50);

		mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);

		mBlur = new BlurMaskFilter(6, BlurMaskFilter.Blur.OUTER);
		mRadialGradient = new RadialGradient(200, 200, 50, new int[] {
				Color.TRANSPARENT, 0x112694D3, 0x992694D3, 0x222694D3 },
				new float[] { 0f, 0.7f, 0.95f, 1f }, Shader.TileMode.REPEAT);
		// mPaint.setMaskFilter(mBlur);
		mPaint.setShader(mRadialGradient);
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					r += 20;
					if (r > 400) {
						r = 10;
					}
					postInvalidate();
				}
			};
		}.start();

	}

	int r = 100;

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.clipRect(0, 0, 400, 400);
		// mRadialGradient = new RadialGradient(200, 200, 50, new int[] {
		// Color.TRANSPARENT, 0x112694D3, 0x992694D3, 0x222694D3 },
		// new float[] { 0f, 0.7f, 0.95f, 1f }, Shader.TileMode.REPEAT);
		// mPaint.setMaskFilter(mBlur);
		// mPaint.setShader(mRadialGradient);
		canvas.drawCircle(200, 200, r, mPaint);
	}
}
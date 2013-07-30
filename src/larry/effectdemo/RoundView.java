package larry.effectdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

class RoundView extends View {

	private Paint mPaint;
	private boolean mRun;

	private int mColorCircle = 0xFFDFDFDF;
	private int mColorArc = 0xFFED3246;
	private int mColorText = 0xFFDFDFDF;
	private String mLoadingText = "";
	private int mWidth = 200;
	private int mHeight = 200;
	private int mStrokeWidth = 4;
	int r = 100;
	float degree;
	RectF mRectf = new RectF(100, 100, 300, 300);

	public RoundView(Context context) {
		super(context);
		setFocusable(true);
		initView(context);

	}

	public RoundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundView);
		mColorCircle = a.getColor(R.styleable.RoundView_colorCircle,
				0xFFDFDFDF);
		mColorArc = a.getColor(R.styleable.RoundView_colorArc, 0xFFED3246);
		mColorText = a.getColor(R.styleable.RoundView_colorText, 0xFFDFDFDF);
		mLoadingText = a.getString(R.styleable.RoundView_text);
		a.recycle();
		initView(context);
	}

	private void initView(Context context) {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(mStrokeWidth);
		// mBlur = new BlurMaskFilter(6, BlurMaskFilter.Blur.OUTER);
		// mPaint.setMaskFilter(mBlur);

	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
			mWidth = result - getPaddingLeft() - getPaddingRight();
		} else {
			result = mWidth + getPaddingLeft() + getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
			mHeight = result - getPaddingTop() - getPaddingBottom();
		} else {
			result = mHeight + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
		mRectf = new RectF(getPaddingLeft() + mStrokeWidth, getPaddingTop()
				+ mStrokeWidth, getWidth() - getPaddingRight() - mStrokeWidth,
				getHeight() - getPaddingBottom() - mStrokeWidth);
	}

	@Override
	protected void onAttachedToWindow() {
		mRun = true;
		new Thread() {
			public void run() {
				while (mRun) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					degree += 10;
					if (degree > 360) {
						degree = 10;
					}
					postInvalidate();
				}
			};
		}.start();
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		mRun = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setColor(mColorCircle);
		float x = mRectf.centerX();
		float y = mRectf.centerY();
		float r = mRectf.width() / 2;
		canvas.drawCircle(x, y, r, mPaint);
		mPaint.setColor(mColorArc);
		canvas.drawArc(mRectf, degree, 90, false, mPaint);
		mPaint.setColor(mColorText);
		mPaint.setTextSize(15);
		if (mLoadingText != null)
			canvas.drawText(mLoadingText,
					mRectf.centerX() - mPaint.measureText(mLoadingText) / 2,
					mRectf.centerY(), mPaint);
	}
}
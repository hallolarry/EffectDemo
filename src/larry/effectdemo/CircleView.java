package larry.effectdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 使用示例
 * 
 * <CircleView android:layout_width="200dp" android:layout_height="200dp"
 * android:layout_gravity="center" circleview:colorArc="#FFED3246"
 * circleview:colorCircle="#FFDFDFDF" circleview:colorText="#FF444444"
 * circleview:strokeWidth="1dp" circleview:text="载入中..."
 * circleview:textSize="20sp" />
 * 
 * @author panlei
 * 
 */
class CircleView extends View {

	private Paint mPaint, mTextPaint;
	private boolean mRun;

	/** 圆圈颜色 **/
	private int mColorCircle = 0xFFDFDFDF;
	/** 动态圆弧颜色 **/
	private int mColorArc = 0xFFED3246;
	/** 中间字体颜色 **/
	private int mColorText = 0xFF444444;
	/** 中间字体大小 **/
	private int mTextSize = 20;
	/** 中间文本内容 **/
	private String mLoadingText;
	/** 默认宽高，wrapcontent时 **/
	private int mWidth = 200;
	private int mHeight = 200;
	/** 圆圈线宽 **/
	private float mStrokeWidth = 2.0f;

	/** 动态角度 **/
	private float degree;
	/** 绘制圆弧所用矩形 **/
	private RectF mRectf = new RectF(100, 100, 300, 300);

	public CircleView(Context context) {
		super(context);
		initView(context);

	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CircleView);
		mColorCircle = a.getColor(R.styleable.CircleView_colorCircle,
				mColorCircle);
		mColorArc = a.getColor(R.styleable.CircleView_colorArc, mColorArc);
		mColorText = a.getColor(R.styleable.CircleView_android_textColor,
				mColorText);
		mLoadingText = a.getString(R.styleable.CircleView_android_text);
		mTextSize = a.getDimensionPixelOffset(
				R.styleable.CircleView_android_textSize, mTextSize);
		mStrokeWidth = a.getDimension(R.styleable.CircleView_strokeWidth,
				mStrokeWidth);
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

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(mColorText);
		mTextPaint.setTextSize(mTextSize);
	}

	/**
	 * 设置字体颜色
	 * 
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		mColorText = textColor;
		mTextPaint.setColor(mColorText);
	}

	/**
	 * 设置圆圈以及动态圆弧的颜色
	 * 
	 * @param circleColor
	 * @param arcColor
	 */
	public void setColor(int circleColor, int arcColor) {
		mColorArc = circleColor;
		mColorArc = arcColor;
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
		mRectf = new RectF(getPaddingLeft() + mStrokeWidth, getPaddingTop()
				+ mStrokeWidth, getWidth() - getPaddingRight() - mStrokeWidth,
				getHeight() - getPaddingBottom() - mStrokeWidth);
		float x = mRectf.centerX();
		float y = mRectf.centerY();
		float r = Math.min(mRectf.width(), mRectf.height()) / 2;

		mPaint.setColor(shadow(mColorCircle));
		canvas.drawCircle(x, y, r - mStrokeWidth, mPaint);
		canvas.drawCircle(x, y, r + mStrokeWidth, mPaint);
		mPaint.setColor(mColorCircle);
		canvas.drawCircle(x, y, r, mPaint);
		mPaint.setColor(mColorArc);
		canvas.drawArc(mRectf, degree, 90, false, mPaint);
		if (mLoadingText != null)
			canvas.drawText(
					mLoadingText,
					mRectf.centerX() - mTextPaint.measureText(mLoadingText) / 2,
					mRectf.centerY() + mTextSize / 2, mTextPaint);
		
		
	}

	/**
	 * 设置阴影颜色
	 * 
	 * @param color
	 * @return
	 */
	private int shadow(int color) {
		int red = (color >> 16 & 0xFF) + 20;
		int green = (color >> 8 & 0xFF) + 20;
		int blue = (color & 0xFF) + 20;
		int alpha = color >> 24 & 0xFF;
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}
}
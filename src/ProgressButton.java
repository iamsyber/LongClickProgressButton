

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;


/**
 * Description:长按结束按钮
 * CreateTime: 2019/8/9 15:11
 * <p>
 * author syber
 * version 1.0
 */
public class ProgressButton extends View {

    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画大圆环的画笔
    private Paint mBigRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 画矩形的画笔
    private Paint mRectPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 字体颜色
    private int mRectColor;
    private int mTextColor;
    // 大圆环颜色
    private int mBigRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 大圆环半径
    private float mBigRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;


    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch ( event.getAction()){
                    case  MotionEvent.ACTION_DOWN:
                        MyLogUtil.showLog("MotionEvent.ACTION_DOWN");
                        startAnimationProgress(100);
                        break;
                    case  MotionEvent.ACTION_UP:
                        MyLogUtil.showLog("MotionEvent.ACTION_UP");
                        stopAnimationProgress(mProgress);
                        break;
                }
                return false;
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xCCCCCC);
        mBigRingColor = typeArray.getColor(R.styleable.TasksCompletedView_bigRingColor, 0xFFFFFFFF);
        mTextColor = typeArray.getColor(R.styleable.TasksCompletedView_circleTextColor, 0xFFFFFFFF);
        mRectColor = typeArray.getColor(R.styleable.TasksCompletedView_RectColor, 0xFFFFFFFF);
        mRingRadius = mRadius + mStrokeWidth;
        mBigRingRadius = mRadius + mStrokeWidth *2;
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mBigRingPaint = new Paint();
        mBigRingPaint.setAntiAlias(true);
        mBigRingPaint.setColor(mCircleColor);
        mBigRingPaint.setStyle(Paint.Style.STROKE);

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(mRectColor);
        mRectPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mRadius / 3);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        if(mProgress==0){
            canvas.drawCircle(mXCenter, mYCenter, mBigRingRadius, mCirclePaint);
        }else{
            canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
        }
        RectF bigoval = new RectF();
        bigoval.left = (mXCenter - mBigRingRadius);
        bigoval.top = (mYCenter - mBigRingRadius);
        bigoval.right = mBigRingRadius  + mXCenter;
        bigoval.bottom = mBigRingRadius + mYCenter;
        canvas.drawArc(bigoval, -90,  360, false, mBigRingPaint);

        RectF rectRect = new RectF();
        rectRect.left = (mXCenter - mRadius/4);
        rectRect.top =(mYCenter - mRadius/2);
        rectRect.bottom = mYCenter;
        rectRect.right = (mXCenter + mRadius/4);
        canvas.drawRoundRect(rectRect,5,5, mRectPaint);

        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius  + mXCenter;
            oval.bottom = mRingRadius + mYCenter;
            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint); //
            String txt =  "长按结束";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight, mTextPaint);
        }else{
            String txt =  "长按结束";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight, mTextPaint);
        }

    }



    private ProgressButtonFinishCallback mProgressButtonFinishCallback;

    public   void setListener(ProgressButtonFinishCallback progressButtonFinishCallback){
        mProgressButtonFinishCallback=progressButtonFinishCallback;
    }

    public interface ProgressButtonFinishCallback{
        void onFinish();
    }




    private ValueAnimator startAnimator;
    private ValueAnimator stopAnimator;

    //按压开始
    private void startAnimationProgress(int progress)
    {
        if(null!=stopAnimator){
            if(stopAnimator.isRunning()){
                stopAnimator.cancel();
            }
        }
        startAnimator = ValueAnimator.ofInt(0, progress);
        startAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
                if(mProgress==100){
                    mProgressButtonFinishCallback.onFinish();
                }
            }
        });
        startAnimator.setInterpolator(new OvershootInterpolator());
        startAnimator.setDuration(2000);
        startAnimator.start();
    }

    //按压结束
    private void stopAnimationProgress(int progress)
    {
        if(null!=startAnimator) {
            if (startAnimator.isRunning()) {
                startAnimator.cancel();
            }
        }
        stopAnimator = ValueAnimator.ofInt(progress,0);
        stopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        stopAnimator.setInterpolator(new OvershootInterpolator());
        stopAnimator.setDuration(1000);
        stopAnimator.start();
    }



}

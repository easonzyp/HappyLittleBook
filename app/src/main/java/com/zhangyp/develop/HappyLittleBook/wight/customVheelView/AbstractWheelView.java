package com.zhangyp.develop.HappyLittleBook.wight.customVheelView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.zhangyp.develop.HappyLittleBook.R;


/**
 * Abstract spinner spinnerwheel view.
 * This class should be subclassed.
 *
 * @author vondear
 */
public abstract class AbstractWheelView extends AbstractWheel {

    private static int itemID = -1;

    @SuppressWarnings("unused")
    private final String LOG_TAG = AbstractWheelView.class.getName() + " #" + (++itemID);

    //----------------------------------
    //  Default properties values
    //----------------------------------

    protected static final int DEF_ITEMS_DIMMED_ALPHA = 50; // 60 in ICS

    protected static final int DEF_SELECTION_DIVIDER_ACTIVE_ALPHA = 70;

    protected static final int DEF_SELECTION_DIVIDER_DIMMED_ALPHA = 70;

    protected static final int DEF_ITEM_OFFSET_PERCENT = 1;

    protected static final int DEF_ITEM_PADDING = 10;

    protected static final int DEF_SELECTION_DIVIDER_SIZE = 2;

    //----------------------------------
    //  Class properties
    //----------------------------------

    // configurable properties

    /**
     * The alpha of the selector spinnerwheel when it is dimmed.
     */
    protected int mItemsDimmedAlpha;

    /**
     * The alpha of separators spinnerwheel when they are shown.
     */
    protected int mSelectionDividerActiveAlpha;

    /**
     * The alpha of separators when they are is dimmed.
     */
    protected int mSelectionDividerDimmedAlpha;

    /**
     * Top and bottom items offset
     */
    protected int mItemOffsetPercent = 1;

    /**
     * Left and right padding value
     */
    protected int mItemsPadding;

    /**
     * Divider for showing item to be selected while scrolling
     */
    protected Drawable mSelectionDivider;

    // the rest

    /**
     * The {@link Paint} for drawing the selector.
     */
    protected Paint mSelectorWheelPaint;

    /**
     * The {@link Paint} for drawing the separators.
     */
    protected Paint mSeparatorsPaint;

    /**
     * for dimming the selector spinnerwheel.
     */
    protected Animator mDimSelectorWheelAnimator;

    /**
     * for dimming the selector spinnerwheel.
     */
    protected Animator mDimSeparatorsAnimator;

    /**
     * The property for setting the selector paint.
     */
    protected static final String PROPERTY_SELECTOR_PAINT_COEFF = "selectorPaintCoeff";

    /**
     * The property for setting the separators paint.
     */
    protected static final String PROPERTY_SEPARATORS_PAINT_ALPHA = "separatorsPaintAlpha";


    protected Bitmap mSpinBitmap;
    protected Bitmap mSeparatorsBitmap;


    //--------------------------------------------------------------------------
    //
    //  Constructor
    //
    //--------------------------------------------------------------------------

    public AbstractWheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //--------------------------------------------------------------------------
    //
    //  Initiating assets and setters for paints
    //
    //--------------------------------------------------------------------------

    @Override
    protected void initAttributes(AttributeSet attrs, int defStyle) {
        super.initAttributes(attrs, defStyle);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AbstractWheelView, defStyle, 0);
        mItemsDimmedAlpha = a.getInt(R.styleable.AbstractWheelView_itemsDimmedAlpha, DEF_ITEMS_DIMMED_ALPHA);
        mSelectionDividerActiveAlpha = a.getInt(R.styleable.AbstractWheelView_selectionDividerActiveAlpha, DEF_SELECTION_DIVIDER_ACTIVE_ALPHA);
        mSelectionDividerDimmedAlpha = a.getInt(R.styleable.AbstractWheelView_selectionDividerDimmedAlpha, DEF_SELECTION_DIVIDER_DIMMED_ALPHA);
        mItemOffsetPercent = a.getInt(R.styleable.AbstractWheelView_itemOffsetPercent, DEF_ITEM_OFFSET_PERCENT);
        mItemsPadding = a.getDimensionPixelSize(R.styleable.AbstractWheelView_itemsPadding, DEF_ITEM_PADDING);
        mSelectionDivider = a.getDrawable(R.styleable.AbstractWheelView_selectionDivider);
        a.recycle();
    }

    @Override
    protected void initData(Context context) {
        super.initData(context);

        // creating animators
        mDimSelectorWheelAnimator = ObjectAnimator.ofFloat(this, PROPERTY_SELECTOR_PAINT_COEFF, 1, 0);

        mDimSeparatorsAnimator = ObjectAnimator.ofInt(this, PROPERTY_SEPARATORS_PAINT_ALPHA,
                mSelectionDividerActiveAlpha, mSelectionDividerDimmedAlpha
        );

        // creating paints
        mSeparatorsPaint = new Paint();
        mSeparatorsPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mSeparatorsPaint.setAlpha(mSelectionDividerDimmedAlpha);

        mSelectorWheelPaint = new Paint();
        mSelectorWheelPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    /**
     * Recreates assets (like bitmaps) when layout size has been changed
     *
     * @param width  New spinnerwheel width
     * @param height New spinnerwheel height
     */
    @Override
    protected void recreateAssets(int width, int height) {
        mSpinBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mSeparatorsBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        setSelectorPaintCoeff(1);
    }

    /**
     * Sets the <code>alpha</code> of the {@link Paint} for drawing separators
     * spinnerwheel.
     *
     * @param alpha alpha value from 0 to 255
     */
    @SuppressWarnings("unused")  // Called via reflection
    public void setSeparatorsPaintAlpha(int alpha) {
        mSeparatorsPaint.setAlpha(alpha);
        invalidate();
    }

    /**
     * Sets the <code>coeff</code> of the {@link Paint} for drawing
     * the selector spinnerwheel.
     *
     * @param coeff Coefficient from 0 (selector is passive) to 1 (selector is active)
     */
    abstract public void setSelectorPaintCoeff(float coeff);


    //--------------------------------------------------------------------------
    //
    //  Processing scroller events
    //
    //--------------------------------------------------------------------------

    @Override
    protected void onScrollTouched() {
//        mDimSelectorWheelAnimator.cancel();
//        mDimSeparatorsAnimator.cancel();
        setSelectorPaintCoeff(1);
//        setSeparatorsPaintAlpha(mSelectionDividerActiveAlpha);
    }

    @Override
    protected void onScrollTouchedUp() {
        super.onScrollTouchedUp();
//        fadeSelectorWheel(750);
//        lightSeparators(750);
    }

    @Override
    protected void onScrollFinished() {
//        fadeSelectorWheel(500);
//        lightSeparators(500);
    }

    //----------------------------------
    //  Animating components
    //----------------------------------

    /**
     * Fade the selector spinnerwheel via an animation.
     *
     * @param animationDuration The duration of the animation.
     */
    private void fadeSelectorWheel(long animationDuration) {
        mDimSelectorWheelAnimator.setDuration(animationDuration);
        mDimSelectorWheelAnimator.start();
    }

    /**
     * Fade the selector spinnerwheel via an animation.
     *
     * @param animationDuration The duration of the animation.
     */
    private void lightSeparators(long animationDuration) {
        mDimSeparatorsAnimator.setDuration(animationDuration);
        mDimSeparatorsAnimator.start();
    }


    //--------------------------------------------------------------------------
    //
    //  Layout measuring
    //
    //--------------------------------------------------------------------------

    /**
     * Perform layout measurements
     */
    abstract protected void measureLayout();


    //--------------------------------------------------------------------------
    //
    //  Drawing stuff
    //
    //--------------------------------------------------------------------------

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewAdapter != null && mViewAdapter.getItemsCount() > 0) {
            if (rebuildItems()) {
                measureLayout();
            }
            doItemsLayout();
            drawItems(canvas);
        }
    }

    /**
     * Draws items on specified canvas
     *
     * @param canvas the canvas for drawing
     */
    abstract protected void drawItems(Canvas canvas);
}

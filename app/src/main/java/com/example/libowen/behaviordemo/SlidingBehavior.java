package com.example.libowen.behaviordemo;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * Created by libowen on 18-7-27.
 */

public class SlidingBehavior extends CoordinatorLayout.Behavior<SlidingCardLayout> {

    private int initOffset;

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, SlidingCardLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int offset = getChildOffsetHeight(child, parent);
        int parentHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
        int height = parentHeight - offset;
        child.measure(parentWidthMeasureSpec,View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        return true;
    }

    private int getChildOffsetHeight(SlidingCardLayout child, CoordinatorLayout parent) {
        int offset = 0;
        for (int i = 0; i < parent.getChildCount(); i ++){
            View view = parent.getChildAt(i);
            if (view instanceof SlidingCardLayout){
                if (view != child){
                    offset += ((SlidingCardLayout) view).getTextHeight();
                }
            }
        }
        return offset;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, SlidingCardLayout child, int layoutDirection) {
        parent.onLayoutChild(child,layoutDirection);
        int offset = 0;
        SlidingCardLayout previous = getPreviousSlidingCardLayout(child, parent);

        //获取到的上一个view有问题的
        if (previous != null){
            offset = previous.getTop() + previous.getTextHeight();
            child.offsetTopAndBottom(offset);
        }
        initOffset = child.getTop();
        return true;
    }

    private SlidingCardLayout getPreviousSlidingCardLayout(SlidingCardLayout child, CoordinatorLayout parent) {
        int index = parent.indexOfChild(child);
        for (int i = index - 1; i >= 0; i--){
            View view = parent.getChildAt(i);
            if (view instanceof SlidingCardLayout){
                return (SlidingCardLayout) view;
            }
        }
        return null;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout parent, @NonNull SlidingCardLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        consumed[1] = scroll(child, dy, initOffset, child.getHeight() + initOffset - child.getTextHeight());
//
//        shiftSliding(consumed[1], parent, child);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull SlidingCardLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        boolean isVertical = (axes & CoordinatorLayout.SCROLL_AXIS_VERTICAL) != 0;
        return isVertical && child == directTargetChild;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout parent, @NonNull SlidingCardLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        int shift = scroll(child, dyUnconsumed, initOffset, child.getHeight() + initOffset - child.getTextHeight());

        shiftSliding(shift, parent, child);

//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    private void shiftSliding(int shift, CoordinatorLayout parent, SlidingCardLayout child) {
        if (shift == 0 ){
            return;
        }
        if (shift > 0){
            //向上
            SlidingCardLayout current = child;
            SlidingCardLayout card = getPreviousSlidingCardLayout(current, parent);
            while (card != null){
                int offset = getOverlap(card, current);
                card.offsetTopAndBottom(-offset);
                current = card;
                card = getPreviousSlidingCardLayout(current,parent);
            }
        } else {
            //向下
            SlidingCardLayout current = child;
            SlidingCardLayout card = getNextChild(parent, current);
            while (card != null){
                int offset = getOverlap(current, card);
                card.offsetTopAndBottom(offset);
                current = card;
                card = getNextChild(parent,current);
            }
        }
    }

    private int getOverlap(SlidingCardLayout above, SlidingCardLayout below) {
        return above.getTop() + above.getTextHeight() - below.getTop();
    }

    private SlidingCardLayout getNextChild(CoordinatorLayout parent, SlidingCardLayout current) {
        int index = parent.indexOfChild(current);
        for (int i = index + 1; i < parent.getChildCount(); i++){
            View view = parent.getChildAt(i);
            if (view instanceof SlidingCardLayout){
                return (SlidingCardLayout) view;
            }
        }
        return null;
    }

    private int scroll(SlidingCardLayout child, int dy, int min, int max) {
        int initHeight = child.getTop();
        int offset = getOffset(initHeight - dy, min, max) - initHeight;
        child.offsetTopAndBottom(offset);
        return - offset;
    }

    private int getOffset(int i, int min, int max){
        if (i > max){
            return max;
        } else if (i < min){
            return min;
        } else {
            return i;
        }
    }

}

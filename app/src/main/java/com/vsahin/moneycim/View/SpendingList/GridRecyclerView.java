package com.vsahin.moneycim.View.SpendingList;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

public class GridRecyclerView extends RecyclerView {

    public GridRecyclerView(Context context) {
        super(context);
    }

    public GridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof GridLayoutManager || layout instanceof StaggeredGridLayoutManager){
            super.setLayoutManager(layout);
        } else {
            throw new ClassCastException("You should use a GridLayoutManager or StaggeredGridLayoutManager.");
        }
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {

        if (getAdapter() != null){

            GridLayoutAnimationController.AnimationParameters animationParams =
                    (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;

            if (animationParams == null) {
                animationParams = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParams;
            }

            int columns = 0;
            LayoutManager layoutManager = getLayoutManager();

            if(layoutManager instanceof StaggeredGridLayoutManager){
                columns = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            } else if(layoutManager instanceof GridLayoutManager){
                columns = ((GridLayoutManager) layoutManager).getSpanCount();
            }

            animationParams.count = count;
            animationParams.index = index;
            animationParams.columnsCount = columns;
            animationParams.rowsCount = count / columns;

            final int invertedIndex = count - 1 - index;
            animationParams.column = columns - 1 - (invertedIndex % columns);
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;

        } else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }
}

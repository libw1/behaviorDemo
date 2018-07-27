package com.example.libowen.behaviordemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libowen on 18-7-27.
 */

@CoordinatorLayout.DefaultBehavior(SlidingBehavior.class)
public class SlidingCardLayout extends FrameLayout {

    private int textHeight = 0;

    public SlidingCardLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingCardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingCardLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_sliding_card,this);
        RecyclerView recyclerView = findViewById(R.id.list);
        List<String> data = new ArrayList<>();
        data.add("周杰伦");
        data.add("陈奕迅");
        data.add("林俊杰");
        data.add("周杰伦");
        data.add("陈奕迅");
        data.add("林俊杰");
        data.add("周杰伦");
        data.add("陈奕迅");
        data.add("林俊杰");
        data.add("周杰伦");
        data.add("陈奕迅");
        data.add("林俊杰");
        SimpleAdapter adapter = new SimpleAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingCardLayout);
        int color = array.getColor(R.styleable.SlidingCardLayout_card_background, Color.BLUE);
        String name = array.getString(R.styleable.SlidingCardLayout_card_name_text);
        TextView card = findViewById(R.id.card_name);
        card.setText(name);
        card.setBackgroundColor(color);
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textHeight = findViewById(R.id.card_name).getMeasuredHeight();
    }

    public int getTextHeight(){
        return textHeight;
    }
}

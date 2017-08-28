package com.example.buyanim;

import android.graphics.Color;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FrameLayout rootview;
    private TextView formView,toview;
    public int[] startPoint,endPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        toview= (TextView) findViewById(R.id.tv_contain);
        rootview= (FrameLayout) findViewById(R.id.rootview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_adapter, getDatas())
        {
            @Override
            protected void convert(final ViewHolder holder, String s, int position) {
                TextView tv=holder.getView(R.id.textView2);
                tv.setText(s);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        formView=holder.getView(R.id.textView2);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initAnim();
                            }
                        });
                    }
                });
            }
        });
    }

    private void initAnim() {
        endPoint=new int[2];
        toview.getLocationInWindow(endPoint);
        startPoint=new int[2];
        formView.getLocationInWindow(startPoint);
        //
        startPoint[0]=startPoint[0]+formView.getWidth();
        startPoint[1]=startPoint[1]-DpUtils.getStatusBarHeight(this)-DpUtils.dip2px(this,56);
        endPoint[0]=endPoint[0]+toview.getWidth()/2-DpUtils.dip2px(this,15);
        //
        Path path = new Path();
        path.moveTo(startPoint[0], startPoint[1]);
        path.quadTo(endPoint[0], startPoint[1]-DpUtils.dip2px(this,50), endPoint[0], endPoint[1]-toview.getHeight()-DpUtils.dip2px(this,15));
        //
        final TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.circle_blue);
        textView.setText("1");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(DpUtils.dip2px(this,30), DpUtils.dip2px(this,30));
        rootview.addView(textView, lp);
        //

        ViewAnimator.animate(textView).path(path).duration(1000).onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {
                rootview.removeView(textView);
            }
        }).start();
    }

    private ArrayList<String> getDatas(){
        ArrayList<String> str=new ArrayList<>();
        for (int i=0;i<20;i++){
            str.add("商品"+i);
        }
        return str;
    }
}

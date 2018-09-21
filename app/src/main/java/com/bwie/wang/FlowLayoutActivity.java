package com.bwie.wang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.zhaoliang5156.flowlayout.FlowLayout;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout flow_layout;
    private String[] data = {"黄焖鸡", "麻辣烫", "盖饭", "披萨", "鸡丁饭", "米线", "饺子", "比萨",
            "猪肉大葱的包子", "红烧肉", "烤肠", "肉松饼", "捕鱼达人2", "机票", "游戏", "熊出没之熊大快跑",
            "美图秀秀", "浏览器", "单机游戏", "我的世界", "电影电视", "QQ空间", "旅游", "免费游戏", "2048",
            "刀塔传奇", "壁纸", "节奏大师", "锁屏", "装机必备", "天天动听", "备份", "网盘", "海淘网", "大众点评",
            "爱奇艺视频", "腾讯手机管家", "百度地图", "猎豹清理大师", "谷歌地图", "hao123上网导航", "京东", "有你",
            "万年历-农历黄历", "支付宝钱包"};

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        flow_layout = findViewById(R.id.flow_layout);
        button = findViewById(R.id.next);

        for (int i = 0; i < 20; i++) {
            TextView textView = new TextView(this);
            textView.setText(data[i % data.length]);
            textView.setPadding(10,20,10,10);
            textView.setBackground(getResources().getDrawable(R.drawable.go_pay));
            textView.setGravity(Gravity.CENTER);
            textView.setId(i);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FlowLayoutActivity.this,ZhanshiActivity.class);
                    startActivity(intent);
                }
            });
            flow_layout.addView(textView);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FlowLayoutActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });


        }
    }
}

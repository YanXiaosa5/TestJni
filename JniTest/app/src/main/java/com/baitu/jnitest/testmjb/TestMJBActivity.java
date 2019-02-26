package com.baitu.jnitest.testmjb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baitu.jnitest.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestMJBActivity extends AppCompatActivity {

    private LinkedHashMap<String,List<ADBean>> dataMap = new LinkedHashMap<>();

    //总数据
    private List<ADBean> adBeans;

    private String downLoadUrl = "http://static.phpjiayuan.com/chatapk/channel/fangyuan.apk";

    private String adIcon = "http://huajian.h9a9.top/lmmy/data/star/5/4.jpg";

    /**
     * 点击改变
     */
    private Button btn_click;

    /**
     * 显示索引
     */
    private TextView tv_msg;

    /**
     * 显示信息
     */
    private TextView tv_bean;

    /**
     * 父数据索引
     */
    private int parentIndex = 0;

    /**
     * 子数据索引
     */
    private int childIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mjb);

        btn_click = findViewById(R.id.btn_click);

        tv_msg = findViewById(R.id.tv_msg);
        tv_bean = findViewById(R.id.tv_bean);

        adBeans = new ArrayList<>();

        //获取上一次缓存的索引
        parentIndex = SharePreferenceUtils.getIndex(1,this);
        childIndex = SharePreferenceUtils.getIndex(2,this);

        //创造元数据
        for (int i = 0; i < 100; i++) {
            if(i < 10){
                adBeans.add(new ADBean(downLoadUrl,"com.qingshu520.chat",(i),"哈哈哈",adIcon,1,1));
            }else if(i >= 10 && i < 20){
                adBeans.add(new ADBean(downLoadUrl,"com.yr.huajian",(i),"呵呵呵",adIcon,1,1));
            }else if(i >= 20 && i < 30){
                adBeans.add(new ADBean(downLoadUrl,"com.jumang.shortvideo",(i),"呢嫩嫩",adIcon,1,1));
            }else if(i >= 30 && i < 40){
                adBeans.add(new ADBean(downLoadUrl,"com.se.chat",(i),"qq",adIcon,1,1));
            }else if(i >= 40 && i < 50){
                adBeans.add(new ADBean(downLoadUrl,"com.qq.chat",(i),"ww",adIcon,1,1));
            }else if(i >= 50 && i < 60){
                adBeans.add(new ADBean(downLoadUrl,"com.ww.chat",(i),"aa",adIcon,1,1));
            }else if(i >= 60 && i < 70){
                adBeans.add(new ADBean(downLoadUrl,"com.ee.chat",(i),"zz",adIcon,1,1));
            }else if(i >= 70 && i < 80){
                adBeans.add(new ADBean(downLoadUrl,"com.rr.chat",(i),"xx",adIcon,1,1));
            }else if(i >= 80 && i < 90){
                adBeans.add(new ADBean(downLoadUrl,"com.tt.chat",(i),"sds",adIcon,1,1));
            }else if(i >= 90 && i < 99){
                adBeans.add(new ADBean(downLoadUrl,"com.yy.chat",(i),"ff",adIcon,1,1));
            }else{
                adBeans.add(new ADBean(downLoadUrl,"com.mm.chat",(i),"pp",adIcon,1,1));
            }
        }

        //从元数据中取出所有的key值,进行分类,以便于map添加分组数据
        final List<String> keyList = new ArrayList<>();
        for (int i = 0; i < adBeans.size(); i++) {
            ADBean adBean = adBeans.get(i);
            if(!keyList.contains(adBean.getPackageName())){
                keyList.add(adBean.getPackageName());
            }
        }

        if(keyList.size() <= 0){
            return;
        }

        //双层循环,将key和对应的adbean分组,一个key(包名)对应多个渠道的app(adbean)
        for (int i = 0; i < keyList.size(); i++) {//key数据

            String key = keyList.get(i);//key值
            List<ADBean> categoryList = new ArrayList<>();

            for (int j = 0; j < adBeans.size(); j++) {//元数据
                ADBean adBean = adBeans.get(j);//每一个广告信息
                String packageName = adBean.getPackageName();

                if(packageName.equals(key)){
                    categoryList.add(adBean);
                }
            }
            dataMap.put(key,categoryList);
        }

        System.out.println(dataMap.size()+"分类数据长度");

        //打印map中所有信息
        for (Map.Entry<String, List<ADBean>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<ADBean> adBeans = entry.getValue();
            System.out.println("\n包名key:" + key+"对应:-->"+new Gson().toJson(adBeans));
        }

        tv_msg.setText("parent"+parentIndex+"\n"+"childIndex"+childIndex);
        tv_bean.setText("包名"+keyList.get(parentIndex)+"\n"+dataMap.get(keyList.get(parentIndex)).get(childIndex).getSortNum());


        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_click.setClickable(false);


                choiceOne(keyList);


                btn_click.setClickable(true);

            }
        });

    }

    /**
     * 顺序选择
     * @param keyList
     */
    public void choiceOne(List<String> keyList){

        List<ADBean> adBeans = dataMap.get(keyList.get(parentIndex));

        if(adBeans != null && adBeans.size() > 0){
            String packageName = adBeans.get(0).getPackageName();
            if(!StringUtils.isAvilible(TestMJBActivity.this,packageName)){
                if(childIndex < adBeans.size() - 1){
                    childIndex++;
                }else{
                    if(parentIndex < dataMap.size() - 1) {
                        parentIndex++;
                    }else{
                        parentIndex = 0;
                    }
                    childIndex = 0;
                }

            }else{//如果已经安装
                for (int i = parentIndex; i < keyList.size(); i++) {
                    if(!StringUtils.isAvilible(TestMJBActivity.this,keyList.get(i))){
                        parentIndex = i;
                        break;
                    }
                }

                if(parentIndex >= dataMap.size() - 1) {
                    parentIndex = 0;
                }
                childIndex = 0;
            }
            tv_msg.setText("parent"+parentIndex+"\n"+"childIndex"+childIndex);
            tv_bean.setText(keyList.get(parentIndex)+"\n"+adBeans.get(childIndex).getSortNum());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharePreferenceUtils.save(this,parentIndex,childIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharePreferenceUtils.save(this,parentIndex,childIndex);
    }
}

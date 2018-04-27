package com.example.dabutaizha.lines.mvp.presenter;

import android.widget.RemoteViews;

import com.example.dabutaizha.lines.ChineseCharToEnUtil;
import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.GroupSentencesInfo;
import com.example.dabutaizha.lines.bean.SearchInfo;
import com.example.dabutaizha.lines.bean.SentencesModel;
import com.example.dabutaizha.lines.mvp.BaseApplication;
import com.example.dabutaizha.lines.mvp.contract.CollectionActivityContract;
import com.example.dabutaizha.lines.mvp.model.CollectionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class CollectionPresenter implements CollectionActivityContract.Presenter {

    private CollectionActivityContract.View mView;
    private CollectionActivityContract.Model mModel;

    public CollectionPresenter(CollectionActivityContract.View view) {
        mView = view;
    }

    @Override
    public void initData() {
        mModel = new CollectionModel(this);
    }

    @Override
    public void process() {
        mModel.getUserCollectData();
    }

    @Override
    public void refreshCollectList(List<SentencesModel> sentencesModels) {
        List<GroupSentencesInfo> groups = new ArrayList<>();
        SentencesModel tempModel = new SentencesModel();
        String tempWord = "";
        ChineseCharToEnUtil wordUtil = new ChineseCharToEnUtil();

        for (int i = 0; i < sentencesModels.size(); i++) {
            SearchInfo.SentencesItem item = SentenceUtil.model2item(sentencesModels.get(i));
            String article = item.getArticle();

            //为第一个组创建头部，并插入内容
            if (i == 0) {
                tempModel = sentencesModels.get(i);
                String firstWord = wordUtil.getFirstLetter(article);
                tempWord = firstWord;

                groups.add(new GroupSentencesInfo(true, item.getArticle(), firstWord));
                groups.add(new GroupSentencesInfo(item));
            }

            //遍历对比之后的数据
            if (i > 0) {
                //如果是相同作品直接插入内容
                if (tempModel.getArticle().equals(article)) {
                    groups.add(new GroupSentencesInfo(item));
                //不同作品先对比首字母是否相同，相同首字母设为空
                } else {
                    String firstWord = wordUtil.getFirstLetter(article);
                    if (tempWord.equals(firstWord)) {
                        firstWord = "";
                    } else {
                        tempWord = firstWord;
                    }
                    groups.add(new GroupSentencesInfo(true, item.getArticle(), firstWord));
                    groups.add(new GroupSentencesInfo(item));
                }
                tempModel = sentencesModels.get(i);
            }


        }

        mView.refreshCollectList(groups);
    }

    @Override
    public void showMessage(String msg) {
        mView.showMessage(msg);
    }
}

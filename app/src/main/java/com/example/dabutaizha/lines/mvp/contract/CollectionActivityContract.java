package com.example.dabutaizha.lines.mvp.contract;

import com.example.dabutaizha.lines.bean.info.GroupSentencesInfo;
import com.example.dabutaizha.lines.bean.model.SentencesModel;

import java.util.List;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public interface CollectionActivityContract {

    interface View {
        void
        refreshCollectList(List<GroupSentencesInfo> groups);

        void showMessage(String msg);
    }

    interface Presenter {
        void initData();

        void process();

        void refreshCollectList(List<SentencesModel> sentencesModels);

        void showMessage(String msg);
    }

    interface Model {
        void getUserCollectData();
    }

}

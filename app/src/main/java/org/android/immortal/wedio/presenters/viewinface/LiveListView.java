package org.android.immortal.wedio.presenters.viewinface;

import org.android.immortal.wedio.model.LiveInfoJson;

import java.util.ArrayList;


/**
 * 列表页面回调
 */
public interface LiveListView extends MvpView {

    void showFirstPage(ArrayList<LiveInfoJson> livelist);
}

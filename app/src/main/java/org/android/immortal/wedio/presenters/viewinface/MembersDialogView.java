package org.android.immortal.wedio.presenters.viewinface;

import org.android.immortal.wedio.model.MemberInfo;

import java.util.ArrayList;


/**
 * 成员列表回调
 */
public interface MembersDialogView extends MvpView {

    void showMembersList(ArrayList<MemberInfo> data);

}

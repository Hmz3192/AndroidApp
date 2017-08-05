package com.example.lenovo.model;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.example.lenovo.model.bean.GroupInfo;
import com.example.lenovo.model.bean.InvationInfo;
import com.example.lenovo.model.bean.UserInfo;
import com.example.lenovo.utils.CallReceiver;
import com.example.lenovo.utils.Constant;
import com.example.lenovo.utils.SpUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2016/9/24.
 */
// 全局事件监听类
public class EventListener {

    private Context mContext;
    private final LocalBroadcastManager mLBM;
    private CallReceiver callReceiver;

    public EventListener(Context context) {
        mContext = context;

        // 创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(mContext);
        setGlobalListeners();
        // 注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);

        // 注册一个群信息变化的监听
        EMClient.getInstance().groupManager().addGroupChangeListener(eMGroupChangeListener);
    }


    /**
     * set global listener
     */
    protected void setGlobalListeners(){
        EMConnectionListener connectionListener =  new EMConnectionListener() {

            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(int i) {

            }
        };
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if(callReceiver == null){
            callReceiver = new CallReceiver();
        }
        //注册通话广播接收者
        mContext.registerReceiver(callReceiver, callFilter);
    }

    // 群信息变化的监听
    private final EMGroupChangeListener eMGroupChangeListener = new EMGroupChangeListener() {

        //收到 群邀请
        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
            // 数据更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupName, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_GROUP_INVITE);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送广播
            mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
        }

        //收到 群申请通知
        @Override
        public void onApplicationReceived(String groupId, String groupName, String applicant, String reason) {

            // 数据更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupName, groupId, applicant));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送广播
            mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
        }

        //收到 群申请被接受
        @Override
        public void onApplicationAccept(String groupId, String groupName, String accepter) {

            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setGroup(new GroupInfo(groupName,groupId,accepter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送广播
            mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
        }

        //收到 群申请被拒绝
        @Override
        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupName, groupId, decliner));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送广播
            mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
        }

        //收到 群邀请被同意
        @Override
        public void onInvitationAccepted(String groupId, String inviter, String reason) {

            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupId, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送广播
            mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
        }

        //收到 群邀请被拒绝
        @Override
        public void onInvitationDeclined(String groupId, String inviter, String reason) {
            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupId, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送广播
            mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
        }

        //收到 群成员被删除
        @Override
        public void onUserRemoved(String groupId, String groupName) {

        }

        //收到 群被解散
        @Override
        public void onGroupDestroyed(String groupId, String groupName) {

        }

        //收到 群邀请被自动接受
        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(inviteMessage);
            invitationInfo.setGroup(new GroupInfo(groupId, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送广播
            mLBM.sendBroadcast(new Intent(Constant.GROUP_INVITE_CHANGED));
        }
    };
    // 注册一个联系人变化的监听
    private final EMContactListener emContactListener = new EMContactListener() {
        // 联系人增加后执行的方法
        @Override
        public void onContactAdded(String hxid) {
            // 数据更新
            Model.getInstance().getDbManager().getContactTableDao().saveContact(new UserInfo(hxid), true);

            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        // 联系人删除后执行的方法
        @Override
        public void onContactDeleted(String hxid) {
            // 数据更新
            Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(hxid);
            Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(hxid);

            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        // 接受到联系人的新邀请
        @Override
        public void onContactInvited(String hxid, String reason) {
            // 数据库更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setUser(new UserInfo(hxid));
            invitationInfo.setReason(reason);
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_INVITE);// 新邀请

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        // 别人同意了你的好友邀请
        @Override
        public void onContactAgreed(String hxid) {
            // 数据库更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setUser(new UserInfo(hxid));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);// 别人同意了你的邀请

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        // 别人拒绝了你好友邀请
        @Override
        public void onContactRefused(String s) {
            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };
}

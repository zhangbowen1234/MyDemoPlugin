package io.rong.callkit;

import android.view.SurfaceView;

import io.rong.callkit.util.IncomingCallExtraHandleUtil;
import io.rong.calllib.IRongCallListener;
import io.rong.calllib.ReportUtil;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.common.RLog;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/** Created by jiangecho on 2016/10/27. */
public class RongCallProxy implements IRongCallListener {

    private static final String TAG = "RongCallProxy";
    private IRongCallListener mCallListener;
    private Queue<CallDisconnectedInfo> mCachedCallQueue;
    private static RongCallProxy mInstance;

    private RongCallProxy() {
        mCachedCallQueue = new LinkedBlockingQueue<>();
    }

    public static synchronized RongCallProxy getInstance() {
        if (mInstance == null) {
            mInstance = new RongCallProxy();
        }
        return mInstance;
    }

    public void setCallListener(IRongCallListener listener) {
        RLog.d(TAG, "setCallListener listener = " + listener);
        this.mCallListener = listener;
        //        if (listener != null) {
        //            CallDisconnectedInfo callDisconnectedInfo = mCachedCallQueue.poll();
        //            if (callDisconnectedInfo != null) {
        //                listener.onCallDisconnected(callDisconnectedInfo.mCallSession,
        // callDisconnectedInfo.mReason);
        //            }
        //        }
    }

    @Override
    public void onCallOutgoing(RongCallSession callSession, SurfaceView localVideo) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, callSession, "state|desc", "onCallOutgoing", getDescription());
        if (mCallListener != null) {
            mCallListener.onCallOutgoing(callSession, localVideo);
        }
    }

    @Override
    public void onCallConnected(RongCallSession callSession, SurfaceView localVideo) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, callSession, "state|desc", "onCallConnected", getDescription());
        if (mCallListener != null) {
            mCallListener.onCallConnected(callSession, localVideo);
        }
    }

    @Override
    public void onCallDisconnected(
            RongCallSession callSession, RongCallCommon.CallDisconnectedReason reason) {
        RLog.d(TAG, "RongCallProxy onCallDisconnected mCallListener = " + mCallListener);
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, callSession, "state|reason|desc", "onCallDisconnected",reason.getValue(), getDescription());
        if (mCallListener != null) {
            mCallListener.onCallDisconnected(callSession, reason);
        } else if (!IncomingCallExtraHandleUtil.needNotify()) {
            mCachedCallQueue.offer(new CallDisconnectedInfo(callSession, reason));
        }
    }

    @Override
    public void onRemoteUserRinging(String userId) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|desc", userId,"onRemoteUserRinging", getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteUserRinging(userId);
        }
    }

    @Override
    public void onRemoteUserJoined(
            String userId,
            RongCallCommon.CallMediaType mediaType,
            int userType,
            SurfaceView remoteVideo) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|desc", userId, "onRemoteUserJoined", getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteUserJoined(userId, mediaType, userType, remoteVideo);
        }
    }

    @Override
    public void onRemoteUserInvited(String userId, RongCallCommon.CallMediaType mediaType) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|desc", userId, "onRemoteUserInvited", getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteUserInvited(userId, mediaType);
        }
    }

    @Override
    public void onRemoteUserLeft(String userId, RongCallCommon.CallDisconnectedReason reason) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|reason|desc", userId, "onRemoteUserLeft",reason.getValue(), getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteUserLeft(userId, reason);
        }
    }

    @Override
    public void onMediaTypeChanged(
            String userId, RongCallCommon.CallMediaType mediaType, SurfaceView video) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|mediaType|desc", userId, "onMediaTypeChanged",mediaType.getValue(), getDescription());
        if (mCallListener != null) {
            mCallListener.onMediaTypeChanged(userId, mediaType, video);
        }
    }

    @Override
    public void onError(RongCallCommon.CallErrorCode errorCode) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "state|code|desc", "onError",errorCode.getValue(), getDescription());
        if (mCallListener != null) {
            mCallListener.onError(errorCode);
        }
    }

    @Override
    public void onRemoteCameraDisabled(String userId, boolean disabled) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|disabled|desc",userId, "onRemoteCameraDisabled",disabled, getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteCameraDisabled(userId, disabled);
        }
    }

    @Override
    public void onRemoteMicrophoneDisabled(String userId, boolean disabled) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|disabled|desc",userId, "onRemoteMicrophoneDisabled",disabled, getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteMicrophoneDisabled(userId, disabled);
        }
    }

    @Override
    public void onNetworkSendLost(int lossRate, int delay) {
        if (mCallListener != null) {
            mCallListener.onNetworkSendLost(lossRate, delay);
        }
    }

    @Override
    public void onFirstRemoteVideoFrame(String userId, int height, int width) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|desc",userId, "onFirstRemoteVideoFrame", getDescription());
        if (mCallListener != null) {
            mCallListener.onFirstRemoteVideoFrame(userId, height, width);
        }
    }

    @Override
    public void onAudioLevelSend(String audioLevel) {
        if (mCallListener != null) {
            mCallListener.onAudioLevelSend(audioLevel);
        }
    }

    public void onRemoteUserPublishVideoStream(
            String userId, String streamId, String tag, SurfaceView surfaceView) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|streamId|desc", userId, "onRemoteUserPublishVideoStream", streamId, getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteUserPublishVideoStream(userId, streamId, tag, surfaceView);
        }
    }

    @Override
    public void onAudioLevelReceive(HashMap<String, String> audioLevel) {
        if (mCallListener != null) {
            mCallListener.onAudioLevelReceive(audioLevel);
        }
    }

    public void onRemoteUserUnpublishVideoStream(String userId, String streamId, String tag) {
        ReportUtil.appStatus(ReportUtil.TAG.CALL_LISTENER, "userId|state|streamId|desc", userId, "onRemoteUserUnpublishVideoStream", streamId, getDescription());
        if (mCallListener != null) {
            mCallListener.onRemoteUserUnpublishVideoStream(userId, streamId, tag);
        }
    }

    @Override
    public void onNetworkReceiveLost(String userId, int lossRate) {
        if (mCallListener != null) {
            mCallListener.onNetworkReceiveLost(userId, lossRate);
        }
    }

    private static class CallDisconnectedInfo {
        RongCallSession mCallSession;
        RongCallCommon.CallDisconnectedReason mReason;

        public CallDisconnectedInfo(
                RongCallSession callSession, RongCallCommon.CallDisconnectedReason reason) {
            this.mCallSession = callSession;
            this.mReason = reason;
        }
    }

    private String getDescription() {
        if (mCallListener != null) {
            return mCallListener.getClass().getSimpleName();
        }
        return "no callListener set";
    }
}

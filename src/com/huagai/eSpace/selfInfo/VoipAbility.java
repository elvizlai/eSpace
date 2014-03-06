package com.huagai.eSpace.selfInfo;

/**
 * Created by huagai on 14-3-6.
 */


public class VoipAbility implements Cloneable {
    //    /**
//     * 无缝切换
//     */
    private boolean referToFlag;
    /**
     * H264点到点视频通话功能位
     */
    private boolean bIsVideoCallAbility = false;
    /**
     * 呼叫保持功能位
     */
    private boolean bIsCallHoldAbility;
    /**
     * 一键转移默认号码
     */
    private String snrNumber;

    private String snrAccessCode;

    private boolean bIsSnrAbility;

    /**
     * 语音邮箱号码
     */
    private String mailBoxNum;

    private String mailBoxPassword;

    private String voiceMailAccessCode;

    public boolean isReferTo() {
//        return referToFlag;
        return false;
    }

    public void setReferTo(Boolean rf) {
        referToFlag = rf;
    }

    public boolean isCallHoldAbility() {
        return bIsCallHoldAbility;
    }

    public void setCallHoldAbility(boolean isCallHoldAbility) {
        this.bIsCallHoldAbility = isCallHoldAbility;
    }

    public String getSnrNumber() {
        return snrNumber;
    }

    public void setSnrNumber(String snrNumber) {
        this.snrNumber = snrNumber;
    }

    public boolean isVideoCallAbility() {
        return bIsVideoCallAbility;
    }

    public void setVideoCallAbility(boolean isVideoCallAbility) {
        this.bIsVideoCallAbility = isVideoCallAbility;
    }

    public String getSnrAccessCode() {
        return snrAccessCode;
    }

    public void setSnrAccessCode(String snrAccessCode) {
        this.snrAccessCode = snrAccessCode;
    }

    public boolean isSnrAbility() {
//        return isSnrAbility;
        return false;
    }

    public void setSnrAbility(boolean isSnrAbility) {
        this.bIsSnrAbility = isSnrAbility;
    }

    public String getMailBoxNum() {
        return mailBoxNum;
    }

    public void setMailBoxNum(String mailBoxNum) {
        this.mailBoxNum = mailBoxNum;
    }

    public String getMailBoxPassword() {
        return mailBoxPassword;
    }

    public void setMailBoxPassword(String mailBoxPassword) {
        this.mailBoxPassword = mailBoxPassword;
    }

    public String getVoiceMailAccessCode() {
        return voiceMailAccessCode;
    }

    public void setVoiceMailAccessCode(String voiceMailAccessCode) {
        this.voiceMailAccessCode = voiceMailAccessCode;
    }

    @Override
    public VoipAbility clone() throws CloneNotSupportedException {
        return (VoipAbility) super.clone();
    }
}

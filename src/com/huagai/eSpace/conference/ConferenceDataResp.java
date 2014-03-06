package com.huagai.eSpace.conference;

import com.huawei.data.BaseResponseData;
import com.huawei.data.CtcMemberEntity;
import com.huawei.ecs.mip.common.BaseMsg;

import java.util.List;


public class ConferenceDataResp extends BaseResponseData {
    private static final long serialVersionUID = 3350323223640344672L;

    private List<CtcMemberEntity> confMembers;

    public ConferenceDataResp(BaseMsg msg) {
        super(msg);
    }

    /**
     * @return the confMembers
     */
    public List<CtcMemberEntity> getConfMembers() {
        return confMembers;
    }

    /**
     * @param confMembers the confMembers to set
     */
    public void setConfMembers(List<CtcMemberEntity> confMembers) {
        this.confMembers = confMembers;
    }
}

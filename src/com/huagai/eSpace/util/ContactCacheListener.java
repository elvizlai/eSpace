package com.huagai.eSpace.util;

import com.huawei.common.PersonalContact;

/**
 * Created by huagai on 14-3-6.
 */

public interface ContactCacheListener {
    /**
     * 联系人添加时被调用
     *
     * @param contact
     */
    void onAdded(PersonalContact contact);

    /**
     * 联系人数据被清除时调用
     */
    void onUpdate();
}

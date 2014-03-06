package com.huagai.eSpace.util;

import com.huawei.common.PersonalContact;
import com.huawei.contacts.ContactListener;
import com.huawei.contacts.ContactLogic;
import com.huawei.contacts.Contacts;
import com.huawei.utils.StringUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huagai on 14-3-6.
 */


public final class ContactCache implements ContactListener {
    /*
     * 自定义联系人key需要加前缀,以区分eSpace account
     */
    private static final String CUSTOM_CONTACT_PREFIX = "id:";

    private static ContactCache cache = null;

    private ConcurrentHashMap<String, PersonalContact> contactsMap = new ConcurrentHashMap<String, PersonalContact>();

    private List<ContactCacheListener> listeners = new LinkedList<ContactCacheListener>();

    private final Object LISTENLOCK = new Object();

    private ContactCache() {
        Contacts.contacts.addEventListen(this);
    }

    synchronized public static ContactCache getIns() {
        if (cache == null) {
            cache = new ContactCache();
        }

        return cache;
    }

    public static void clearIns() {
        cache = null;
    }

    public boolean addEventListen(ContactCacheListener listener) {
        if (listener == null) {
            return false;
        }

        synchronized (LISTENLOCK) {
            listeners.add(listener);
        }

        return true;
    }

    public boolean removeEventListen(ContactCacheListener listener) {
        if (listener == null) {
            return false;
        }

        synchronized (LISTENLOCK) {
            listeners.remove(listener);
        }

        return true;
    }

    private void onCacheAdded(PersonalContact contact) {
        synchronized (LISTENLOCK) {
            int size = listeners.size();

            for (int i = 0; i < size; i++) {
                listeners.get(i).onAdded(contact);
            }
        }
    }

    private void onCacheUpdate() {
        synchronized (LISTENLOCK) {
            int size = listeners.size();

            for (int i = 0; i < size; i++) {
                listeners.get(i).onUpdate();
            }
        }
    }

    private boolean containsInCache(PersonalContact contact) {
        String key = getMapKey(contact);

        if (StringUtil.isStringEmpty(key)) {
            return false;
        }

        return contactsMap.containsKey(key);
    }

    public boolean contains(PersonalContact contact) {
        if (containsInCache(contact)) {
            return true;
        }

        return Contacts.contacts.contains(contact);
    }

    public void add(PersonalContact contact) {
        String key = getMapKey(contact);

        if (!StringUtil.isStringEmpty(key)) {
            contactsMap.put(key, contact);
            onCacheAdded(contact);
        }
    }

    public void remove(PersonalContact contact) {
        String account = getMapKey(contact);

        if (!StringUtil.isStringEmpty(account)) {
            contactsMap.remove(account);
        }
    }

    /**
     * 通过eSpace account取得PersonalContact对象
     *
     * @param account
     * @return 没有找到返回null
     */
    public PersonalContact getContactByAccount(String account) {
        if (StringUtil.isStringEmpty(account)) {
            return null;
        }

        PersonalContact p = contactsMap.get(account);

        if (p == null) {
            p = Contacts.contacts.getContactByAccount(account);
        }

        return p;
    }

    /**
     * 通过eSpace contactID取得PersonalContact对象
     * 获取eSpace联系人推荐调用 {@link #getContactByAccount(String)},以改善性能
     *
     * @param contactID
     * @return 没有找到返回null
     */
    public PersonalContact getContactByID(String contactID) {
        if (StringUtil.isStringEmpty(contactID)) {
            return null;
        }

        PersonalContact contact = contactsMap.get(CUSTOM_CONTACT_PREFIX
                + contactID);

        if (contact == null) {
            contact = Contacts.contacts.getContactByID(contactID);
        }

        //如果通过ContactID来查找eSpace联系人,则需要通过遍历的方式来查找
        if (contact == null) {
            for (PersonalContact c : contactsMap.values()) {
                if (contactID.equals(c.getContactId())) {
                    contact = c;
                    break;
                }
            }
        }

        return contact;
    }

    public void clear() {
        contactsMap.clear();
        listeners.clear();
        clearIns();
    }

    public void updateContactInfo(PersonalContact contact) {
        if (null == contact) {
            return;
        }

        PersonalContact cachePerson = null;

        if (!StringUtil.isStringEmpty(contact.getEspaceNumber())) {
            cachePerson = getContactByAccount(contact.getEspaceNumber());
        } else if (!StringUtil.isStringEmpty(contact.getContactId())) {
            cachePerson = getContactByAccount(contact.getContactId());
        }

        if (cachePerson == null || cachePerson.isAllInfo()) {
            return;
        }
        if (null != contact.getName()) {
            cachePerson.setName(contact.getName());
        }
        if (null != contact.getEspaceNumber()) {
            cachePerson.setEspaceNumber(contact.getEspaceNumber());
        }
        if (null != contact.getSex()) {
            cachePerson.setSex(contact.getSex());
        }
        if (null != contact.getPhone()) {
            cachePerson.setPhone(contact.getPhone());
        }
        if (null != contact.getNativeName()) {
            cachePerson.setNativeName(contact.getNativeName());
        }
        if (null != contact.getMobile()) {
            cachePerson.setMobile(contact.getMobile());
        }
        if (null != contact.getFax()) {
            cachePerson.setFax(contact.getFax());
        }
        if (null != contact.getEmail()) {
            cachePerson.setEmail(contact.getEmail());
        }
        if (null != contact.getHead()) {
            cachePerson.setHead(contact.getHead());
        }
        if (null != contact.getShortphone()) {
            cachePerson.setShortphone(contact.getShortphone());
        }
        if (null != contact.getOfficephone()) {
            cachePerson.setOfficephone(contact.getOfficephone());
        }
        if (null != contact.getBinderNumber()) {
            cachePerson.setBinderNumber(contact.getBinderNumber());
        }
        if (null != contact.getIsFriend()) {
            cachePerson.setIsFriend(contact.getIsFriend());
        }
        if (null != contact.getNickname()) {
            cachePerson.setNickname(contact.getNickname());
        }
        if (null != contact.getSignature()) {
            cachePerson.setSignature(contact.getSignature());
        }
        if (null != contact.getAddress()) {
            cachePerson.setAddress(contact.getAddress());
        }
        if (null != contact.getDepartmentName()) {
            cachePerson.setDepartmentName(contact.getDepartmentName());
        }
        if (null != contact.getNamePinyin()) {
            cachePerson.setNamePinyin(contact.getNamePinyin());
        }
        if (null != contact.getOriginMobile()) {
            cachePerson.setOriginMobile(contact.getOriginMobile());
        }
        if (null != contact.getOriginOffice()) {
            cachePerson.setOriginOffice(contact.getOriginOffice());
        }
        if (null != contact.getNativeName()) {
            cachePerson.setNativeName(contact.getNativeName());
        }
        if (null != contact.getDomain()) {
            cachePerson.setDomain(contact.getDomain());
        }
        if (null != contact.getVoipNumber()) {
            cachePerson.setVoipNumber(contact.getVoipNumber());
        }
        if (null != contact.getVoipNumber2()) {
            cachePerson.setVoipNumber2(contact.getVoipNumber2());
        }
        if (null != contact.getDeptDesc()) {
            cachePerson.setDeptDesc(contact.getDeptDesc());
        }

        cachePerson.setAllInfo(true);
        onCacheUpdate();
    }

    @Override
    public void onAdded(PersonalContact contact) {
        if (containsInCache(contact)) {
            remove(contact);
        }
    }

    @Override
    public void onRemoved(PersonalContact contact) {
        if (contact == null) {
            return;
        }

        // 删除好友后，需要把这两个字段置为null
        contact.setNickname(null);
        contact.setIsFriend(null);

        // 此处没有做是否存在的判断，因为默认以好友的信息为准，如果缓存之前存在，就替换掉
        add(contact);
    }

    @Override
    public void onClear() {
    }

    private String getMapKey(PersonalContact contact) {
        if (contact == null) {
            return null;
        }

        String mapKey = contact.getEspaceNumber();

        if (StringUtil.isStringEmpty(mapKey)) {
            if (!StringUtil.isStringEmpty(contact.getContactId())) {
                mapKey = CUSTOM_CONTACT_PREFIX + contact.getContactId();
            }
        }

        return mapKey;
    }

    public PersonalContact getContactByNumber(String number) {
        if (StringUtil.isStringEmpty(number)) {
            return null;
        }

        PersonalContact contact = null;

        contact = ContactLogic.getIns().getContactByPhoneNumber(number);

        return contact;
    }
}

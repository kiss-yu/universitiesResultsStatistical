package com.vote.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class Const {
    public static final String VOTE_PERMISSION_VALUE = "kiss4400";
    private static final ConcurrentHashMap<String,AtomicInteger> VOTE = new ConcurrentHashMap<>();
    private static boolean openSetting = true;//是否可以设置投票系统信息
    private static String type;
    private static String title;
    private static boolean voteOpen = false;
    private static final AtomicLong SUM_VOTE = new AtomicLong(0);

    /**
     * 初始化投票队伍与票数键值
     * */
    public final static synchronized void initVote(String[] keys){
        for (String key:keys)
            VOTE.put(key,new AtomicInteger(0));
    }
    /**
     * 给某个队伍加一票
     * */
    public final static void addOneTicket(String key){
        VOTE.get(key).getAndIncrement();
        SUM_VOTE.getAndIncrement();
    }

    /**
     * 获取整个投票系统的键值信息
     * */
    public final static Map<String,Integer> getVoteMsg(){
        Map<String,Integer> map = new HashMap<>();
        synchronized (VOTE){
            for (ConcurrentHashMap.Entry<String,AtomicInteger> entry:VOTE.entrySet()){
                map.put(entry.getKey(),entry.getValue().intValue());
            }
        }
        return map;
    }

    /**
     * 获取当前投票总的有效票数
     * */
    public static Long getSumVote(){
        Long sum ;
        synchronized (SUM_VOTE){
            sum = SUM_VOTE.longValue();
        }
        return sum;
    }


    /**
     * 获取现在是否可以设置投票系统
     * @param isOpen
     *  当设置为true时清空VOTE内容
     * */
    public static void setOpenSetting(boolean isOpen){
        if (isOpen){
            VOTE.clear();
            SUM_VOTE.set(0L);
        }
        openSetting = isOpen;
    }

    /**
     * 获取现在是否能够设置投票系统
     * */
    public static boolean getOpenSetting(){
        return openSetting;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Const.type = type;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Const.title = title;
    }

    public static boolean isVoteOpen() {
        return voteOpen;
    }

    public static void setVoteOpen(boolean voteOpen) {
        Const.voteOpen = voteOpen;
    }
}

package com.project.common.listener;

import com.project.common.constant.SystemConstant;
import com.project.common.util.LogUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

/**
 * Redis订阅发布，当该系统订阅失效事件，key失效的时候推送事件
 */
@Component
public class Subscriber extends JedisPubSub {

    /**
     * REDIS 事件key以_分割
     */
    public static final String REDIS_KEY = "";


    /**
     * 事件响应
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        LogUtils.INFO("Redis Message received. Channel:" + channel + " Msg:" + message);
        //失效事件
        if (SystemConstant.CHANNEL_EXPIRED.equals(channel)) {
            String[] arry = message.split("_");
            String key = null;
            String id = null;
            if (arry.length == 3) {
                key = arry[1];
                id = arry[2];
            } else {
                key = arry[0];
                id = arry[1];
            }
            switch (key) {
                case REDIS_KEY:
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 初始化订阅时候的处理
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        // logger.debug("onSubscribe. Channel:"+channel+" subscribedChannels:"+subscribedChannels);
    }

    /**
     * 取消订阅时候的处理
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        // logger.debug("onUnsubscribe. Channel:"+channel+" subscribedChannels:"+subscribedChannels);
    }

    /**
     * 初始化按表达式的方式订阅时候的处理
     */
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        // logger.debug("onPSubscribe. pattern:"+pattern+" subscribedChannels:"+subscribedChannels);
    }

    /**
     * 取消按表达式的方式订阅时候的处理
     */
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        // logger.debug("onPUnsubscribe. pattern:"+pattern+" subscribedChannels:"+subscribedChannels);
    }

    /**
     * 取得按表达式的方式订阅的消息后的处理
     */
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(("onPMessage. pattern:" + pattern + " channel:" + channel + " message:" + message));
    }
}
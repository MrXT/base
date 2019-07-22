package com.project;

import com.project.common.bean.LockUser;
import com.project.common.bean.redis.RedisClient;
import com.project.common.constant.SystemConstant;
import com.project.common.listener.Subscriber;
import com.project.common.util.CommonUtils;
import com.project.common.util.LogUtils;
import com.project.common.util.SpringContextUtils;
import com.project.entity.User;
import com.project.module.sys.dao.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 入口类
 */
@Controller
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class ProjectApplication {

    public static void main(final String[] args) {
        //初始化进程
        CommonUtils.init();
        SpringApplication app = new SpringApplication(ProjectApplication.class);
        SpringContextUtils.setApplicationContext(app.run(args));
        //初始化锁定用户
        initUser();
        //初始化redis订阅事件
        initRedisSub();

    }

    /**
     * 主页跳转
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/")
    public void index(final HttpServletResponse response) throws IOException {
        response.sendRedirect("login/main");
    }


    /**
     * 初始化锁定用户
     */
    private static void initUser() {
        // 初始化LockUser锁定用户id
        User user = new User();
        user.setValid(true);
        // 锁定用户状态3
        user.setStatus(3);
        user.setAppUser(true);
        Set<String> userIds = SpringContextUtils.getBean(UserMapper.class).select(user).stream().map(user1 -> user1.getUserId()).collect(Collectors.toSet());

        if (userIds != null) {
            // 赋值给userIds
            LockUser.userIds = userIds;
        }
    }

    /**
     * 初始化订阅事件
     */
    private static void initRedisSub() {
        //是否注册订阅事件
        Boolean open = Boolean.parseBoolean(CommonUtils.readResource("redis.sub.open"));
        if (open) {
            //添加redis的订阅事件
            Subscriber subscriber = SpringContextUtils.getBean(Subscriber.class);
            RedisClient redisClient = SpringContextUtils.getBean(RedisClient.class);
            if (redisClient.getJedisCluster() != null) {
                redisClient.getJedisCluster().subscribe(subscriber, SystemConstant.CHANNEL_EXPIRED);
            }
            if (redisClient.getShardedJedisPool() != null) {
                redisClient.getShardedJedisPool().getResource().getAllShards().forEach(jedis -> {
                    new Thread(() -> {
                        while (true) {
                            try {
                                jedis.subscribe(subscriber, SystemConstant.CHANNEL_EXPIRED);
                            } catch (Exception e) {
                                try {
                                    Thread.sleep(10 * 1000);
                                } catch (InterruptedException ex) {
                                }
                                LogUtils.LOGEXCEPTION(e);
                            }
                        }
                    }
                    ).start();
                });
            }
        }
    }

}

package com.rib.wx.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.jboss.jandex.Main;
import org.springframework.stereotype.Controller;


@Controller
public class TokenThreadStarter {

    @PostConstruct
    public void initThread() {
        Executor executor = Executors.newSingleThreadExecutor();
/*//                new ThreadFactory() {

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setDaemon(true);
                t.setName("--------------消息推送线程---------------");
                return t;
            }
        });
*/      
        //executor.execute(new TokenThread());不启动
    }
    
}

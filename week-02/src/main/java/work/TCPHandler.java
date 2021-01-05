package work; /**
 * fshows.com
 * Copyright (C) 2020-2021 All Rights Reserved.
 */

import work.HandlerState;
import work.ReadState;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangj
 * @version TCPHandler.java, v 0.1 2021-01-03 9:48 下午 yangj
 */
public class TCPHandler implements Runnable {

    private final SelectionKey sk;
    private final SocketChannel sc;
    private static final int THREAD_COUNTING = 10;
    private static ThreadPoolExecutor pool;

    /**
     * 状态模式的Handler
     */
    HandlerState state;

    public TCPHandler(SelectionKey sk, SocketChannel sc) {
        // 线程池
        pool = new ThreadPoolExecutor(
                THREAD_COUNTING,
                THREAD_COUNTING,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        this.sk = sk;
        this.sc = sc;
        // 设置初始状态READING
        state = new ReadState();
        // 线程池最大线程数
        pool.setMaximumPoolSize(32);
    }

    @Override
    public void run() {
        try {
            state.handle(this, sk, sc, pool);
        } catch (IOException e) {
            System.out.println("处理异常，连接关闭");
            closeChannel();
        }
    }

    public void closeChannel() {
        try {
            sk.cancel();
            sc.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void setState(HandlerState state) {
        this.state = state;
    }
}

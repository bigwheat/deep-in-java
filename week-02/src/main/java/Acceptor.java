/**
 * fshows.com
 * Copyright (C) 2020-2021 All Rights Reserved.
 */

import work.TCPHandler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author yangj
 * @version Acceptor.java, v 0.1 2021-01-03 9:47 下午 yangj
 */
public class Acceptor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public Acceptor(Selector selector, ServerSocketChannel ssc) {
        this.ssc=ssc;
        this.selector=selector;
    }

    @Override
    public void run() {
        try {
            // 接受连接请求
            SocketChannel sc= ssc.accept();
            System.out.println(sc.socket().getRemoteSocketAddress().toString() + " 连接");

            if(sc!=null) {
                // 非阻塞
                sc.configureBlocking(false);
                // SocketChannel向selector注册OP_READ事件，返回通道key
                SelectionKey sk = sc.register(selector, SelectionKey.OP_READ);
                // 阻塞的selector操作立即返回
                selector.wakeup();
                sk.attach(new TCPHandler(sk, sc));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
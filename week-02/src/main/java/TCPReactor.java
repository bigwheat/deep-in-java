/**
 * fshows.com
 * Copyright (C) 2020-2021 All Rights Reserved.
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yangj
 * @version TCPReactor.java, v 0.1 2021-01-03 9:46 下午 yangj
 */
public class TCPReactor implements Runnable {

    private final ServerSocketChannel ssc;
    private final Selector selector;

    public TCPReactor(int port) throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(port);
        // 绑定监听端口
        ssc.socket().bind(addr);
        // 设置非阻塞
        ssc.configureBlocking(false);
        // ServerSocketChannel向selector注册OP_ACCEPT事件，返回通道key
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);
        // 给key附加Acceptor对象
        sk.attach(new Acceptor(selector, ssc));
    }

    @Override
    public void run() {
        // 线程中断前继续执行
        while (!Thread.interrupted()) {
            System.out.println("端口监听: " + ssc.socket().getLocalPort() + "...");
            try {
                // 没有事件不继续执行
                if (selector.select() == 0) {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 所有事件key集合
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectedKeys.iterator();
            while (it.hasNext()) {
                // 调度
                dispatch((SelectionKey) (it.next()));
                it.remove();
            }
        }
    }

    /**
     * 调度方法，根据事件绑定的对象开新线程
     *
     * @param key
     */
    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            r.run();
        }
    }
}
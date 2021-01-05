package work; /**
 * fshows.com
 * Copyright (C) 2020-2021 All Rights Reserved.
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yangj
 * @version ReadState.java, v 0.1 2021-01-03 9:50 下午 yangj
 */
public class ReadState implements HandlerState {

    private SelectionKey sk;

    public ReadState() {
    }

    @Override
    public void changeState(TCPHandler h) {
        h.setState(new WorkState());
    }

    @Override
    public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                       ThreadPoolExecutor pool) throws IOException { // read()
        this.sk = sk;
        byte[] arr = new byte[1024];
        ByteBuffer buf = ByteBuffer.wrap(arr);
        int numBytes = sc.read(buf);
        if(numBytes == -1)
        {
            System.out.println("【error】无数据，连接关闭");
            h.closeChannel();
            return;
        }
        String str = new String(arr);
        if ((str != null) && !str.equals(" ")) {
            h.setState(new WorkState());
            pool.execute(new WorkerThread(h, str));
            System.out.println(sc.socket().getRemoteSocketAddress().toString()
                    + " > " + str);
        }

    }

    /**
     * 处理Write
     *
     * @param h
     * @param str
     */
    synchronized void process(TCPHandler h, String str) {
        h.setState(new WriteState(str));
        // 改变注册事件
        this.sk.interestOps(SelectionKey.OP_WRITE);
        this.sk.selector().wakeup();
    }

    /**
     * 工作线程
     */
    class WorkerThread implements Runnable {

        TCPHandler h;
        String str;

        public WorkerThread(TCPHandler h, String str) {
            this.h = h;
            this.str=str;
        }

        @Override
        public void run() {
            process(h, str);
        }

    }
}

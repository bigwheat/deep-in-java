package work; /**
 * fshows.com
 * Copyright (C) 2020-2021 All Rights Reserved.
 */

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yangj
 * @version HandlerState.java, v 0.1 2021-01-03 9:49 下午 yangj
 */
public interface HandlerState {

    /**
     * 状态变更
     *
     * @param h
     */
    void changeState(TCPHandler h);

    /**
     * 处理
     *
     * @param h
     * @param sk
     * @param sc
     * @param pool
     * @throws IOException
     */
    void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                ThreadPoolExecutor pool) throws IOException;
}

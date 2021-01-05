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
 * @version WriteState.java, v 0.1 2021-01-03 9:51 下午 yangj
 */
public class WriteState implements HandlerState {

    private String param;

    public WriteState(String readStr) {
        param = readStr;
    }

    @Override
    public void changeState(TCPHandler h) {
        h.setState(new ReadState());
    }

    @Override
    public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                       ThreadPoolExecutor pool) throws IOException {
        String str = String.format("地址：%s,消息：%s\r\n",sc.socket().getLocalSocketAddress().toString(),param.trim());
        // wrap自动把buf的position设为0，不需要再flip()
        ByteBuffer buf = ByteBuffer.wrap(str.getBytes());
        while (buf.hasRemaining()) {
            sc.write(buf);
        }
        // 改变状态
        h.setState(new ReadState());
        // 改变通道注册事件
        sk.interestOps(SelectionKey.OP_READ);
        sk.selector().wakeup();
    }
}

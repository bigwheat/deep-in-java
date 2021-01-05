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
 * @version WorkState.java, v 0.1 2021-01-03 9:50 下午 yangj
 */
public class WorkState implements HandlerState {

    public WorkState() {
    }

    @Override
    public void changeState(TCPHandler h) {
        h.setState(new WriteState(""));
    }

    @Override
    public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc,
                       ThreadPoolExecutor pool) throws IOException {

    }

}

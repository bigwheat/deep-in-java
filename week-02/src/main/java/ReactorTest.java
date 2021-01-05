/**
 * fshows.com
 * Copyright (C) 2020-2021 All Rights Reserved.
 */

import java.io.IOException;

/**
 * @author yangj
 * @version ReactorTest.java, v 0.1 2021-01-03 9:52 下午 yangj
 */
public class ReactorTest {

    public static void main(String[] args) {
        try {
            TCPReactor reactor = new TCPReactor(8888);
            reactor.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

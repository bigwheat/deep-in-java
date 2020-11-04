/**
 * fshows.com
 * Copyright (C) 2020-2020 All Rights Reserved.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 自定义类加载
 *
 * @author yangj
 * @version MyClassLoader.java, v 0.1 2020-11-04 3:08 下午 yangj
 */
public class MyClassLoader extends ClassLoader {

    private final String filePath;

    public MyClassLoader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] cLassBytes = null;
        try {
            //加载
            byte[] bytes = getClassFileBytes(new File(filePath));

            //解码
            cLassBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                cLassBytes[i] = (byte) ((byte) 255 - bytes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.defineClass(name, cLassBytes, 0, cLassBytes.length);
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    private byte[] getClassFileBytes(File file) throws Exception {
        //NIO读取文件
        FileInputStream fis = new FileInputStream(file);
        FileChannel fileC = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel outC = Channels.newChannel(baos);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            int i = fileC.read(buffer);
            if (i == 0 || i == -1) {
                break;
            }
            buffer.flip();
            outC.write(buffer);
            buffer.clear();
        }
        fis.close();
        return baos.toByteArray();
    }

}

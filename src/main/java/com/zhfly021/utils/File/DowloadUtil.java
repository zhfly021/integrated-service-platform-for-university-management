package com.zhfly021.utils.File;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DowloadUtil {

    public static void download(String filename, HttpServletResponse res) throws IOException {
        // 发送给客户端的数据
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取filename   千万注意文件的路径
        bis = new BufferedInputStream(new FileInputStream(new File("./static/excel/" + filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }

}

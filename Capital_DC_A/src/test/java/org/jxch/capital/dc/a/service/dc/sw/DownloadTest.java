package org.jxch.capital.dc.a.service.dc.sw;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;


public class DownloadTest
{
    /**
     * Rigorous Test :-)
     */
    @SneakyThrows
    @Test
    public void shouldAnswerWithTrue()
    {
        String urlStr = "http://www.swsindex.com/downloadfiles.aspx?swindexcode=SwClass&type=530&columnid=8892";
        String file = "D:\\xicheng_jiang\\下载\\浏览器\\SW.html";
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }
}


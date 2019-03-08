package com.mycode.io.resource;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * 蛮小江
 * 2018/8/31 9:47
 */
public class FileDemo {
    public static void main(String[] args) throws IOException {
         File file = new File(System.getProperty("user.dir"));
         //下面两行等价
        /*System.out.println(file.getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));*/

        URI uri =file.toURI();
        URL url = uri.toURL();
        System.out.println(System.getProperty("user.dir"));
        System.out.println("uri : "+uri);
        System.out.println("url :"+url);
        InputStream inputStream = url.openStream();

        int count = -1;
        byte [] buffer = new byte[1024];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        StringBuilder sb = new StringBuilder();
       while ((count =   bufferedInputStream.read(buffer))!=-1){
           sb.append(new String(buffer));
       }
        System.out.println(sb.toString().trim());
        inputStream.close();

        System.out.println("-----------------------");

        //URL url1 = new URL("https://www.baidu.com");
        URL url1 = new URL("https://start.spring.io");
        URLConnection urlConnection = url1.openConnection();
        urlConnection.connect();
        InputStream inputStream1 = urlConnection.getInputStream();
        //处理字节 到字符的乱码
        InputStreamReader reader = new InputStreamReader(inputStream1);

        StringBuilder sb1 = new StringBuilder();
        int count1;
        /*int available = inputStream1.available();
        while (available>0){
            sb1.append((char)inputStream1.read());
            available = inputStream1.available();
        }*/

        //jdk 操作每次读取一个字节
       /* while ((count1 =   reader.read())!=-1){
            sb1.append((char)count1);
        }*/


        //缓冲区读
        byte [] buffer1 = new byte[1024];
        BufferedInputStream bufferedInputStream1 = new BufferedInputStream(inputStream1);
        while ((count =   bufferedInputStream1.read(buffer1))!=-1){
            sb1.append(new String(buffer1,0,count));
        }



        //借助commons-io
        byte [] bytes = new byte[4096];
        int read ;
        while((read= IOUtils.read(inputStream1, bytes))!=0){
            System.out.println("+++=====read:"+read);
            sb1.append(new  java.lang.String(bytes,0,read,"UTF-8"));
        }
        System.out.println(sb1.toString().trim());

        //IOUtils.readFully(inputStream1,bytes);
        //IOUtils.read(inputStream1,bytes);

        //借鉴Spring的做法
        //String s = copyToString(inputStream1, Charset.forName("UTF-8"));
       // String s = copyToString(inputStream1);
       // System.out.println(s);



        IOUtils.close(urlConnection);
       // inputStream1.close();


    }

    public static java.lang.String copyToString(InputStream in) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringBuilder out = new StringBuilder();
            byte[] buffer = new byte[4096];
            boolean var5 = true;

            int bytesRead;
            while((bytesRead = in.read(buffer)) != -1) {
                out.append(new   java.lang.String(buffer, 0, bytesRead));
            }

            return out.toString();
        }
    }


    public static String copyToString(InputStream in, Charset charset) throws IOException {
        if (in == null) {
            return "";
        } else {
            StringBuilder out = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(in, charset);
            char[] buffer = new char[4096];
            boolean var5 = true;

            int bytesRead;
            while((bytesRead = reader.read(buffer)) != -1) {
                out.append(buffer, 0, bytesRead);
            }

            return out.toString();
        }
    }
}

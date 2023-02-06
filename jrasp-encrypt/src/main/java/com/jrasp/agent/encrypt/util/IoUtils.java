package com.jrasp.agent.encrypt.util;

import java.io.*;
import java.util.List;
import java.util.zip.CRC32;

public class IoUtils {

    /**
     * 写文件
     *
     * @param file      文件
     * @param fileBytes 字节
     */
    public static void writeFile(File file, byte[] fileBytes) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(fileBytes, 0, fileBytes.length);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
    }

    /**
     * 读取文件
     *
     * @param file 文件
     * @return 字节
     */
    public static byte[] readFileToByte(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return toBytes(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * stream2byte[]
     *
     * @param input 输入流
     * @return 字节
     * @throws IOException IOException
     */
    public static byte[] toBytes(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } finally {
            close(output, input);
        }
    }

    /**
     * 枚举所有文件，包括文件夹
     *
     * @param filess 返回的文件列表
     * @param dir    目录
     */
    public static void listFile(List<File> filess, File dir) {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录[" + dir.getAbsolutePath() + "]不存在");
        }
        File[] files = dir.listFiles();
        for (File f : files) {
            filess.add(f);
            if (f.isDirectory()) {
                listFile(filess, f);
            }
        }
    }

    /**
     * 删除整个目录
     *
     * @param dir 目录
     */
    public static void delete(File dir) {
        if (!dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            dir.delete();
        } else {
            File[] files = dir.listFiles();
            for (File f : files) {
                delete(f);
            }
        }
        dir.delete();
    }

    /**
     * 计算cec
     *
     * @param bytes 字节
     * @return crc值
     */
    public static long crc32(byte[] bytes) {
        CRC32 crc = new CRC32();
        crc.update(bytes);
        return crc.getValue();
    }


    /**
     * 关闭流
     *
     * @param outs Closeable
     */
    public static void close(Closeable... outs) {
        if (outs != null) {
            for (Closeable out : outs) {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

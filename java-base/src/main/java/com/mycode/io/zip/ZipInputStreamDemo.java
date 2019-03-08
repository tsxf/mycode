package com.mycode.io.zip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 蛮小江
 * 2018/9/13 14:30
 */
public class ZipInputStreamDemo {
    public static void main(String[] args) throws IOException {
        //testReadZip();
        try( ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("test.zip"))) {
            ZipEntry zipEntry = new ZipEntry("fileName");
            zos.putNextEntry(zipEntry);
            zos.write(Files.readAllBytes(Paths.get("employee.dat")));
            zos.closeEntry();
        }
        System.out.println("success");

    }

    private static void testReadZip() throws IOException {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(""))) {
               ZipEntry entry = null;
            while ((entry = zin.getNextEntry()) != null) {

                  zin.closeEntry();
            }
        }
    }
}

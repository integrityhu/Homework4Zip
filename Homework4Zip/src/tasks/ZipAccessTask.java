package tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipAccessTask
{
   final static int BUFSIZE = 2048;
   
   public static void extract(String zippath, String destdir) throws IOException
   {
      File dest = new File(destdir);
      if (!dest.exists())
         dest.mkdir();
      try (ZipInputStream zis = new ZipInputStream
              (new BufferedInputStream(new FileInputStream(zippath))))
      {
         ZipEntry ze = zis.getNextEntry();
         while (ze != null)
         {
            String filepath = destdir+File.separator+ze.getName();
            new File(filepath).getParentFile().mkdirs();
            if (!ze.isDirectory())
               extractFile(zis, filepath);
            zis.closeEntry();
            ze = zis.getNextEntry();
         }
      }
   }
   static void extractFile(ZipInputStream zis, String path) throws IOException
   {
      try (BufferedOutputStream bos =
              new BufferedOutputStream(new FileOutputStream(path)))
      {
         byte[] buffer = new byte[BUFSIZE];
         int len;
         while ((len = zis.read(buffer, 0, BUFSIZE)) != -1)
            bos.write(buffer, 0, len);
      }
   }
}

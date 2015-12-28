package tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCreateTask
{
   final static int BUFSIZE = 2048;
   static File archive;

   public static void createZip(String fileName, String[] args) throws IOException
   {
      try (ZipOutputStream zos = new ZipOutputStream
            (new BufferedOutputStream(new FileOutputStream(fileName))))
      {
         archive = new File(fileName);
         for (int i = 0; i < args.length; i++)
         {
            File file = new File(args[i]);
            if (file.isDirectory()) {
               addDir(file, file.getParentFile(), zos);
            } else {
               addFile(file, file.getParentFile(), zos);
            }
         }
      }
   }
   static void addDir(File dir, File parent, ZipOutputStream zos) throws IOException
   {
      File[] files = dir.listFiles();
      for (File file: files)
      {
         if (file.getCanonicalPath().equals(archive.getCanonicalPath()))
            continue;
         if (file.isDirectory()) {
            addDir(file, parent, zos);
         } else {
            addFile(file, parent, zos);
         }
      }
   }
   
   static void addFile(File file, File parent, ZipOutputStream zos) throws IOException
   {
      if (file.getCanonicalPath().equals(archive.getCanonicalPath()))
         return;
      String entryName;
      if (parent != null) {
          entryName = file.getAbsolutePath().substring(parent.getAbsolutePath().length());
      } else {
          entryName = file.getAbsolutePath();
      }
      zos.putNextEntry(new ZipEntry(entryName));
      try (BufferedInputStream bis =
              new BufferedInputStream(new FileInputStream(file)))
      {
         byte[] buffer = new byte[BUFSIZE];
         int len;
         while ((len = bis.read(buffer, 0, BUFSIZE)) != -1)
            zos.write(buffer, 0, len);
      }
      zos.closeEntry();
   }
}

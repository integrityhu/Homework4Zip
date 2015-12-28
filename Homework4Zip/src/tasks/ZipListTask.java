package tasks;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.paint.Color;

import javafx.stage.Stage;

public class ZipListTask extends Application
{
   private String zipfile;

   @Override
   @SuppressWarnings("unchecked")
   public void start(final Stage primaryStage)
   {
      Application.Parameters params = getParameters();
      List<String> uparams = params.getUnnamed();
      zipfile = uparams.get(0);

      ObservableList<Entry> entries = FXCollections.observableArrayList();
      Charset charset = Charset.defaultCharset();
      try 
      {
         ZipInputStream zis =
                  new ZipInputStream(new FileInputStream(zipfile),charset);
         System.out.println("Gathering zip entries...");
         byte[] buffer = new byte[4096];
         ZipEntry ze;
         while ((ze = zis.getNextEntry()) != null)
         {
            System.out.print(".");
            if (ze.isDirectory()) // Ignore directory-only entries stored in
               continue;          // archive.
            Entry entry = new Entry();
            entry.setName(ze.getName());
            entry.setCompressedSize(ze.getCompressedSize());
            entry.setSize(ze.getSize());
            entries.add(entry);
         }
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error");
         Platform.exit();
      }

      TableView<Entry> table = new TableView<>(entries);
      TableColumn<Entry, String> nameCol = new TableColumn<>("Name");
      nameCol.setCellValueFactory(new PropertyValueFactory("name"));
      nameCol.setPrefWidth(200.0);
      TableColumn<Entry, String> compressedSizeCol;
      compressedSizeCol = new TableColumn<>("Compressed Size");
      compressedSizeCol.
         setCellValueFactory(new PropertyValueFactory("compressedSize"));
      compressedSizeCol.setPrefWidth(150.0);
      TableColumn<Entry, String> sizeCol;
      sizeCol = new TableColumn<>("Size");
      sizeCol.setCellValueFactory(new PropertyValueFactory("size"));
      sizeCol.setPrefWidth(150.0);
      table.getColumns().setAll(nameCol, compressedSizeCol, sizeCol);

      Scene scene = new Scene(table, 500, 300);
      primaryStage.setScene(scene);
      primaryStage.setTitle("ZipList: "+zipfile);
      primaryStage.show();
   }
   
   public static void main(String fileName)
   {
       String[] args = {fileName};
       launch(args);      
   }
}

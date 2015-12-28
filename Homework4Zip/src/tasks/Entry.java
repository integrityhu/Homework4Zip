package tasks;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Entry
{
   private StringProperty name;
   public void setName(String value)
   {
      nameProperty().set(value);
   }
   public String getName()
   {
      return nameProperty().get();
   }
   public StringProperty nameProperty()
   {
      if (name == null)
         name = new SimpleStringProperty(this, "name");
      return name;
   }

   private LongProperty compressedSize;
   public void setCompressedSize(long value)
   {
      compressedSizeProperty().set(value);
   }
   public long getCompressedSize()
   {
      return compressedSizeProperty().get();
   }
   public LongProperty compressedSizeProperty()
   {
      if (compressedSize == null)
         compressedSize = new SimpleLongProperty(this, "compressedSize");
      return compressedSize;
   }

   private LongProperty size;
   public void setSize(long value)
   {
      sizeProperty().set(value);
   }
   public long getSize()
   {
      return sizeProperty().get();
   }
   public LongProperty sizeProperty()
   {
      if (size == null)
         size = new SimpleLongProperty(this, "size");
      return size;
   }
}

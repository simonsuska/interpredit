package de.example.data.datasources;

/** A type which provides read and write access to a static datasource.  */
public interface Datasource {
    boolean write(String content);
    String read();
}

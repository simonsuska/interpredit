package de.example.data.datasources;

public interface Datasource {
    boolean write(String content);
    String read();
}

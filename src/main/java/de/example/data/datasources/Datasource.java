package de.example.data.datasources;

public interface Datasource {
    void write(String content);
    String read();
}

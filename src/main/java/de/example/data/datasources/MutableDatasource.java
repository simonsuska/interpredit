package de.example.data.datasources;

public interface MutableDatasource extends Datasource {
    void set(String datasource);
    void unset();
}

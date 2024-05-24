package de.example.data.datasources;

public interface MutableDatasource extends Datasource {
    boolean set(String datasource);
    boolean unset();
}

package de.example.data.datasources;

/** A type which provides read and write access to a mutable datasource */
public interface MutableDatasource extends Datasource {
    boolean set(String datasource);
    boolean unset();
    boolean delete();
}

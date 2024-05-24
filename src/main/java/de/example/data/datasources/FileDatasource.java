package de.example.data.datasources;

import java.io.File;

public class FileDatasource implements MutableDatasource {
    private File file;

    @Override
    public boolean write(String content) {
        // TODO: Implement
        return false;
    }

    @Override
    public String read() {
        // TODO: Implement
        return null;
    }

    @Override
    public boolean set(String datasource) {
        // TODO: Implement
        return false;
    }

    @Override
    public boolean unset() {
        // TODO: Implement
        return false;
    }
}

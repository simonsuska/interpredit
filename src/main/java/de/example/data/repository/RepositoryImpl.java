package de.example.data.repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.data.datasources.MutableDatasource;
import de.example.domain.repository.Repository;

import java.util.Objects;

/**
 * This class grants access to the underlying mutable datasource. Thus,
 * it serves as a bridge between the datasource and the application logic.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, the mutable datasource contains the path
 * referencing to the file which is currently opened in the editor.
 */
public class RepositoryImpl implements Repository {

    //: SECTION: - ATTRIBUTES

    private final MutableDatasource datasource;

    //: SECTION: - CONSTRUCTORS

    /**
     * This constructor creates a new repository with the given datasource.
     * @param datasource The mutable datasource this repository will grant access to
     */
    @Inject
    public RepositoryImpl(@Named(Di.MUTABLE_DATASOURCE) MutableDatasource datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    //: SECTION: - METHODS

    /**
     * This method opens the datasource identified by the given
     * path and subsequently reads its content. Multiple lines
     * are joined by the `\n` character.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, the datasource is equal
     * to the absolute path of a file in the file system.
     * @param filename The absolute path to the datasource
     * @return The content from the datasource or {@code null}, if the
     *         content could not been read successfully
     */
    @Override
    public String open(String filename) {
        if (this.datasource.set(filename)) {
            return this.datasource.read();
        }
        return null;
    }

    /**
     * This method closes the datasource.
     *
     * <br><br><b>Discussion</b><br>
     * In this context, "close" does not refer to closing the
     * connection but rather removing the reference to it.
     * In the context of Interpredit, closing the file refers
     * to closing it in the editor. However, the file remains
     * in the file system.
     * @return {@code true} if the datasource was successfully
     *         closed, otherwise {@code false}
     */
    @Override
    public boolean close() {
        return this.datasource.unset();
    }

    /**
     * This method saves the given content in the datasource.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, the file content is
     * completely replaced by the given one. Therefore, be
     * aware that the previous content will not be accessible
     * after calling this method.
     * @param content The content to be saved
     * @return {@code true} if the content was successfully saved in
     *         the datasource, otherwise {@code false}
     */
    @Override
    public boolean save(String content) {
        return this.datasource.write(content);
    }

    /**
     * This method deletes the datasource.
     *
     * <br><br><b>Discussion</b><br>
     * In the context of Interpredit, it refers to deleting the
     * currently opened file in the filesystem.
     * @return {@code true} if the datasource was successfully deleted,
     *         otherwise {@code false}
     */
    @Override
    public boolean delete() {
        return this.datasource.delete();
    }
}

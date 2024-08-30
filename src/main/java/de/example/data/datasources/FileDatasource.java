package de.example.data.datasources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * This type represents a mutable file datasource.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, this datasource contains the path to the file which is currently opened in the editor.
 */
public class FileDatasource implements MutableDatasource {

    //: SECTION: - ATTRIBUTES

    private Path filepath;
    private Path backupFilepath;

    //: SECTION: - CONSTRUCTORS

    /** This constructor creates a new empty file datasource. */
    public FileDatasource() {
        this.filepath = null;
        this.backupFilepath = null;
    }

    //: SECTION: - METHODS

    /**
     * This method resolves the backup filepath from the file referenced by the underlying path.
     *
     * @return The resolved backup filepath or {@code null}, if the
     *         underlying filepath is {@code null}
     */
    private Path resolveBackupFilepath() {
        if (this.filepath != null) {
            String fullFilename = this.filepath.getFileName().toString();
            Path parentDirectory = this.filepath.getParent();

            int dotIndex = fullFilename.lastIndexOf('.');
            String filename = (dotIndex == -1) ? fullFilename : fullFilename.substring(0, dotIndex);
            String extension = (dotIndex == -1) ? "" : fullFilename.substring(dotIndex);

            String backupFilename = filename + "-A8sJ9kL3pQr2" + extension;
            return parentDirectory.resolve(backupFilename);
        }

        return null;
    }

    /**
     * This method writes the given content into the file referenced by the underlying path.
     *
     * <br><br><b>Discussion</b><br>
     * Note that the content of the file will be completely overridden by the given one. It is not appended.
     *
     * @param content The content to be written
     * @return {@code true} if the content was successfully written into the file, otherwise {@code false}
     */
    @Override
    public boolean write(String content) {
        if (this.filepath != null)
            try (BufferedWriter bw = Files.newBufferedWriter(this.filepath)) {
                bw.write(content);
                return true;
            } catch (IOException e) {
                return false;
            }

        return false;
    }

    /**
     * This method reads the content from the file referenced by the underlying path. Multiple lines are joined
     * by the {@code \n} character.
     *
     * @return The content from the file or {@code null}, if the content could not been read successfully
     */
    @Override
    public String read() {
        try {
            List<String> fileContents = Files.readAllLines(filepath);
            return String.join("\n", fileContents);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * This method replaces the underlying path with the given one. Subsequent method calls will access the
     * file referenced by this path rather than the previous one.
     *
     * @param datasource The absolute path to the new file
     * @return {@code true} if the given path is valid and refers to a file, otherwise {@code false}. If this method
     *         returns `{@code true}, the new path has successfully been set, otherwise the old path remains.
     */
    @Override
    public boolean set(String datasource) {
        Path filepath = Paths.get(datasource);
        File file = filepath.toFile();

        if (file.exists() && file.isFile()) {
            this.filepath = filepath;
            this.backupFilepath = resolveBackupFilepath();
            return true;
        }

        return false;
    }

    /**
     * This method deletes the underlying path which will result in an empty file datasource. The file itself will
     * remain in the file system.
     *
     * @return {@code true} if a path previously existed, otherwise {@code false}
     */
    @Override
    public boolean unset() {
        if (filepath != null) {
            this.filepath = null;
            this.backupFilepath = null;
            return true;
        }

        return false;
    }

    /**
     * This method deletes both the underlying path and the file from the file system which will result in an empty
     * file datasource.
     *
     * @return {@code true} if both the underlying path and the file have successfully been deleted, otherwise
     *         {@code false}
     */
    @Override
    public boolean delete() {
        if (this.filepath != null || this.backupFilepath != null)
            if (this.backup()) // Backup the file content
                if (this.filepath.toFile().delete()) { // Try to delete the file
                    this.filepath = null; // Close the file in the editor

                    if (this.purge()) { // Delete the backup
                        this.backupFilepath = null; // Delete the backup filepath
                        return true;
                    } else {
                        if (this.restore()) // Restore the file content if closing the file fails
                            return this.purge(); // Delete the backup after restoring the file content
                    }
                }

        return false;
    }

    /**
     * This method creates a duplicate of the file referenced by the underlying path for backup purposes.
     *
     * @return {@code true} if the duplicate was successfully created, otherwise {@code false}
     */
    public boolean backup() {
        try {
            Files.copy(this.filepath, this.backupFilepath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * This method restores the content from the backup file and writes it to the file referenced by the underlying path.
     *
     * @return {@code true} if the restoration was successful, otherwise {@code false}
     */
    public boolean restore() {
        try {
            Files.copy(this.backupFilepath, this.filepath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * This method deletes the backup file.
     *
     * @return {@code true} if the backup file was successfully deleted, otherwise {@code false}
     */
    public boolean purge() {
        return this.backupFilepath.toFile().delete();
    }
}

package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.repository.Repository;

import java.util.Objects;
import java.util.function.Function;

/**
 * This type transmits the event for opening the datasource to the
 * repository after the user has tapped the menu item and selected
 * a valid datasource.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, it transmits the event for opening the
 * file after the user has tapped the menu item and selected a valid file.
 */
public class OpenUsecase implements Function<String, String> {

    //: SECTION: - ATTRIBUTES

    private final Repository repository;

    //: SECTION: - CONSTRUCTORS

    /** This constructor initializes this use case with the appropriate repository. */
    @Inject
    public OpenUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    //: SECTION: - METHODS

    /**
     * This method causes the repository to open the datasource.
     * @param filename The absolute path to the datasource
     * @return The content from the datasource or {@code null}, if the
     *         content could not been read successfully
     */
    @Override
    public String apply(String filename) {
        System.out.println(filename);
        return this.repository.open(filename);
    }
}

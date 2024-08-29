package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.repository.Repository;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * This type transmits the event for closing the datasource to the
 * repository after the user has tapped the menu item.
 *
 * <br><br><b>Discussion</b><br>
 * In the context of Interpredit, it transmits the event for closing the
 * file after the user has tapped the menu item.
 */
public class CloseUsecase implements Supplier<Boolean> {

    //: SECTION: - ATTRIBUTES

    private final Repository repository;

    //: SECTION: - CONSTRUCTORS

    /** This constructor initializes this use case with the appropriate repository. */
    @Inject
    public CloseUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    //: SECTION: - METHODS

    /**
     * This method causes the repository to close the datasource.
     * @return {@code true} if the datasource was successfully
     *         closed, otherwise {@code false}
     */
    @Override
    public Boolean get() {
        return this.repository.close();
    }
}

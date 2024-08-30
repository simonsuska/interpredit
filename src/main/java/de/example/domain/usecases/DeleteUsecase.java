package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.repository.Repository;
import java.util.Objects;
import java.util.function.Supplier;

/** This type notifies the repository to delete the file after the user has clicked on the delete menu item. */
public class DeleteUsecase implements Supplier<Boolean> {

    //: SECTION: - ATTRIBUTES

    private final Repository repository;

    //: SECTION: - CONSTRUCTORS

    /** This constructor initializes this use case with the appropriate repository. */
    @Inject
    public DeleteUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    //: SECTION: - METHODS

    /**
     * This method notifies the repository to delete the file.
     *
     * @return {@code true} if the file was successfully deleted, otherwise {@code false}
     */
    @Override
    public Boolean get() {
        return this.repository.delete();
    }
}

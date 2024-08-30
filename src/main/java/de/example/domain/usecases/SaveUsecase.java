package de.example.domain.usecases;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import de.example.core.di.Di;
import de.example.domain.repository.Repository;
import java.util.Objects;
import java.util.function.Function;

/** This type notifies the repository to save the file after the user has clicked on the save menu item. */
public class SaveUsecase implements Function<String, Boolean> {

    //: SECTION: - ATTRIBUTES

    private final Repository repository;

    //: SECTION: - CONSTRUCTORS

    /** This constructor initializes this use case with the appropriate repository. */
    @Inject
    public SaveUsecase(@Named(Di.REPOSITORY) Repository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    //: SECTION: - METHODS

    /**
     * This method notifies the repository to save the file.
     *
     * @param content The content to be saved
     * @return {@code true} if the content was successfully saved in the file, otherwise {@code false}
     */
    @Override
    public Boolean apply(String content) {
        return this.repository.save(content);
    }
}

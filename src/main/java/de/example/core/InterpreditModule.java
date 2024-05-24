package de.example.core;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import de.example.data.datasources.FileDatasource;
import de.example.data.datasources.MutableDatasource;
import de.example.data.repository.RepositoryImpl;
import de.example.domain.entities.Buffer;
import de.example.domain.entities.machines.Decoder;
import de.example.domain.entities.machines.Machine;
import de.example.domain.entities.machines.ram.RandomAccessMachine;
import de.example.domain.entities.machines.ram.RandomAccessMachineDecoder;
import de.example.domain.repository.Repository;
import de.example.domain.usecases.*;
import de.example.presentation.Model;

public class InterpreditModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MutableDatasource.class)
                .annotatedWith(Names.named(Di.MUTABLE_DATASOURCE))
                .to(FileDatasource.class);

        bind(Buffer.class)
                .annotatedWith(Names.named(Di.RAM_INT_BUFFER))
                .to(Buffer.class);

        bind(Decoder.class)
                .annotatedWith(Names.named(Di.RAM_DECODER))
                .to(RandomAccessMachineDecoder.class);

        bind(Repository.class)
                .annotatedWith(Names.named(Di.REPOSITORY))
                .to(RepositoryImpl.class);

        bind(DeleteUsecase.class)
                .annotatedWith(Names.named(Di.DELETE_USECASE))
                .to(DeleteUsecase.class);

        bind(OpenUsecase.class)
                .annotatedWith(Names.named(Di.OPEN_USECASE))
                .to(OpenUsecase.class);

        bind(RunUsecase.class)
                .annotatedWith(Names.named(Di.RUN_USECASE))
                .to(RunUsecase.class);

        bind(SaveUsecase.class)
                .annotatedWith(Names.named(Di.SAVE_USECASE))
                .to(SaveUsecase.class);

        bind(StopUsecase.class)
                .annotatedWith(Names.named(Di.STOP_USECASE))
                .to(StopUsecase.class);

        bind(Model.class)
                .annotatedWith(Names.named(Di.MODEL))
                .to(Model.class);

        bind(Machine.class)
                .annotatedWith(Names.named(Di.MACHINE))
                .to(RandomAccessMachine.class);
    }
}

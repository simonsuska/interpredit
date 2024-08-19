package de.example.core.di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import de.example.data.datasources.FileDatasource;
import de.example.data.datasources.MutableDatasource;
import de.example.data.repository.RepositoryImpl;
import de.example.domain.entities.Buffer;
import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Decoder;
import de.example.domain.entities.machines.Machine;
import de.example.domain.entities.machines.ram.RandomAccessMachine;
import de.example.domain.entities.machines.ram.RandomAccessMachineDecoder;
import de.example.domain.repository.Repository;
import de.example.domain.usecases.*;
import de.example.presentation.Model;
import de.example.presentation.PrinterThread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;

public class InterpreditModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MutableDatasource.class)
                .annotatedWith(Names.named(Di.MUTABLE_DATASOURCE))
                .to(FileDatasource.class)
                .in(Singleton.class);

        bind(new TypeLiteral<Buffer<String>>(){})
                .annotatedWith(Names.named(Di.RAM_STRING_BUFFER))
                .to(new TypeLiteral<>(){});

        bind(Decoder.class)
                .annotatedWith(Names.named(Di.RAM_DECODER))
                .to(RandomAccessMachineDecoder.class);

        bind(Repository.class)
                .annotatedWith(Names.named(Di.REPOSITORY))
                .to(RepositoryImpl.class)
                .in(Singleton.class);

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
                .to(Model.class)
                .in(Singleton.class);

        bind(Machine.class)
                .annotatedWith(Names.named(Di.MACHINE))
                .to(RandomAccessMachine.class)
                .in(Singleton.class);

        bind(OutputUsecase.class)
                .annotatedWith(Names.named(Di.OUTPUT_USECASE))
                .to(OutputUsecase.class);

        bind(InputUsecase.class)
                .annotatedWith(Names.named(Di.INPUT_USECASE))
                .to(InputUsecase.class);

        bind(CloseUsecase.class)
                .annotatedWith(Names.named(Di.CLOSE_USECASE))
                .to(CloseUsecase.class);

        bind(new TypeLiteral<Exchanger<Status>>(){})
                .annotatedWith(Names.named(Di.RUN_EXCHANGER))
                .to(new TypeLiteral<>(){})
                .in(Singleton.class);

        bind(PrinterThread.class)
                .annotatedWith(Names.named(Di.PRINTER_THREAD))
                .to(PrinterThread.class);

        bind(CyclicBarrier.class)
                .annotatedWith(Names.named(Di.RUN_CYCLIC_BARRIER))
                .toInstance(new CyclicBarrier(2));

        bind(CyclicBarrier.class)
                .annotatedWith(Names.named(Di.QUIT_CYCLIC_BARRIER))
                .toInstance(new CyclicBarrier(2));
    }
}

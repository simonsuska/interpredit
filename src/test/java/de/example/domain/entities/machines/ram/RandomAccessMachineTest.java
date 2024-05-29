package de.example.domain.entities.machines.ram;

import de.example.domain.entities.Buffer;
import de.example.domain.entities.exit.builder.ExitStatus;
import de.example.domain.entities.exit.status.Status;
import de.example.domain.entities.machines.Decoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomAccessMachineTest {
    private Buffer<Integer> buffer;
    private RandomAccessMachine ram;

    private static final int MEMORY_SIZE = 5;

    @BeforeEach
    void setUp() {
        buffer = mock(Buffer.class);
        Decoder decoder = new RandomAccessMachineDecoder();
        ram = new RandomAccessMachine(new int[MEMORY_SIZE], buffer , decoder);
    }

    @Test
    void run() {
        ExitStatus exitStatus;

        exitStatus = ram.run(null);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.run("");
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.run("SET");
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.run("SET A");
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.run("SET 5");
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.run("OUT 1");
        assertEquals(exitStatus.getStatus(), Status.OUTPUT);

        when(buffer.isEmpty()).thenReturn(true);
        exitStatus = ram.run("INP 1");
        assertEquals(exitStatus.getStatus(), Status.INPUT);

        when(buffer.isEmpty()).thenReturn(false);
        exitStatus = ram.run("INP 1");
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.run("HLT 99");
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.run(" SET 5  ");
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.run(" SET  5  ");
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void requestOutputWithFilledBuffer() {
        when(buffer.read()).thenReturn(174);

        String result = ram.requestOutput();

        verify(buffer, times(1)).read();
        assertEquals(result, "174");
    }

    @Test
    void requestOutputWithEmptyBuffer() {
        when(buffer.read()).thenReturn(null);

        String result = ram.requestOutput();

        verify(buffer, times(1)).read();
        assertEquals(result, "");
    }

    @Test
    void deliverInput() {
        ExitStatus exitStatus;

        exitStatus = ram.deliverInput(null);
        verify(buffer, never()).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.deliverInput("");
        verify(buffer, never()).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.deliverInput("A");
        verify(buffer, never()).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.deliverInput("1");
        verify(buffer, times(1)).write(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.deliverInput(" 2  ");
        verify(buffer, times(1)).write(2);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.deliverInput("17");
        verify(buffer, times(1)).write(17);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void set() {
        ExitStatus exitStatus;

        exitStatus = ram.set(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.set(0);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.set(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void add() {
        ExitStatus exitStatus;

        exitStatus = ram.add(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.add(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.add(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.add(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.add(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.add(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void sub() {
        ExitStatus exitStatus;

        exitStatus = ram.sub(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.sub(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.sub(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.sub(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.sub(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.sub(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void mul() {
        ExitStatus exitStatus;

        exitStatus = ram.mul(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.mul(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.mul(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.mul(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.mul(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.mul(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void div() {
        ExitStatus exitStatus;

        exitStatus = ram.div(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.div(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.div(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.div(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.div(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.div(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void lda() {
        ExitStatus exitStatus;

        exitStatus = ram.lda(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.lda(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.lda(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.lda(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.lda(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.lda(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void ldk() {
        ExitStatus exitStatus;

        exitStatus = ram.ldk(-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.ldk(0);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.ldk(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);
    }

    @Test
    void sta() {
        ExitStatus exitStatus;

        exitStatus = ram.sta(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.sta(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.sta(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.sta(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.sta(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.sta(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void inpWithEmptyBuffer() {
        ExitStatus exitStatus;
        when(buffer.isEmpty()).thenReturn(true);

        exitStatus = ram.inp(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.inp(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.inp(1);
        assertEquals(exitStatus.getStatus(), Status.INPUT);

        exitStatus = ram.inp(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.INPUT);

        exitStatus = ram.inp(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.INPUT);

        exitStatus = ram.inp(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void inpWithFilledBuffer() {
        ExitStatus exitStatus;
        when(buffer.isEmpty()).thenReturn(false);

        exitStatus = ram.inp(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.inp(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.inp(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.inp(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.inp(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.inp(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void out() {
        ExitStatus exitStatus;

        exitStatus = ram.out(-1);
        verify(buffer, never()).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.out(0);
        verify(buffer, never()).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.out(1);
        verify(buffer, times(1)).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.OUTPUT);

        exitStatus = ram.out(MEMORY_SIZE-1);
        verify(buffer, times(2)).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.OUTPUT);

        exitStatus = ram.out(MEMORY_SIZE);
        verify(buffer, times(3)).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.OUTPUT);

        exitStatus = ram.out(MEMORY_SIZE+1);
        verify(buffer, times(3)).write(anyInt());
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void jmp() {
        ExitStatus exitStatus;

        exitStatus = ram.jmp(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jmp(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jmp(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jmp(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jmp(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jmp(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void jez() {
        ExitStatus exitStatus;

        exitStatus = ram.jez(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jez(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jez(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jez(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jez(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jez(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void jne() {
        ExitStatus exitStatus;

        exitStatus = ram.jne(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jne(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jne(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jne(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jne(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jne(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void jlz() {
        ExitStatus exitStatus;

        exitStatus = ram.jlz(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jlz(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jlz(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jlz(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jlz(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jlz(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void jle() {
        ExitStatus exitStatus;

        exitStatus = ram.jle(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jle(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jle(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jle(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jle(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jle(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void jgz() {
        ExitStatus exitStatus;

        exitStatus = ram.jgz(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jgz(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jgz(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jgz(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jgz(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jgz(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void jge() {
        ExitStatus exitStatus;

        exitStatus = ram.jge(-1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jge(0);
        assertEquals(exitStatus.getStatus(), Status.QUIT);

        exitStatus = ram.jge(1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jge(MEMORY_SIZE-1);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jge(MEMORY_SIZE);
        assertEquals(exitStatus.getStatus(), Status.CONTINUE);

        exitStatus = ram.jge(MEMORY_SIZE+1);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }

    @Test
    void hlt() {
        ExitStatus exitStatus = ram.hlt(99);
        assertEquals(exitStatus.getStatus(), Status.QUIT);
    }
}

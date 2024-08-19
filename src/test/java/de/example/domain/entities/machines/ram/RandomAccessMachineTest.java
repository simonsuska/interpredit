package de.example.domain.entities.machines.ram;

import de.example.domain.entities.Buffer;
import de.example.domain.entities.Status;
import de.example.domain.entities.machines.Decoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomAccessMachineTest {
    private Buffer<String> buffer;
    private RandomAccessMachine ram;

    private static final int MEMORY_SIZE = 5;

    @BeforeEach
    void setUp() {
        buffer = mock(Buffer.class);
        Decoder decoder = new RandomAccessMachineDecoder();
        ram = new RandomAccessMachine(new int[MEMORY_SIZE+1], buffer , decoder);
    }

    @Test
    void run() throws InterruptedException {
        Status status;

        status = ram.run(null);
        assertEquals(status, Status.DECODE_ERROR);

        status = ram.run("");
        assertEquals(status, Status.HOP);

        status = ram.run("SET");
        assertEquals(status, Status.DECODE_ERROR);

        status = ram.run("SET A");
        assertEquals(status, Status.DECODE_ERROR);

        status = ram.run("SET 5");
        assertEquals(status, Status.OK);

        status = ram.run(" SET 5  ");
        assertEquals(status, Status.OK);

        status = ram.run(" SET  5  ");
        assertEquals(status, Status.OK);

        status = ram.run("OUT 1");
        assertEquals(status, Status.OUTPUT);

        when(buffer.isEmpty()).thenReturn(true);
        status = ram.run("INP 1");
        assertEquals(status, Status.INPUT);

        when(buffer.isEmpty()).thenReturn(false);
        status = ram.run("INP 1");
        assertEquals(status, Status.OK);

        status = ram.run("HLT 99");
        assertEquals(status, Status.FINISH);

        status = ram.run("SET -1");
        assertEquals(status, Status.SET_ERROR);

        status = ram.run("ADD " + MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.run("CMD 1");
        assertEquals(status, Status.COMMAND_ERROR);
    }

    @Test
    void requestOutput() throws InterruptedException {
        when(buffer.read()).thenReturn("174");

        String result = ram.requestOutput();

        verify(buffer, times(1)).read();
        assertEquals(result, "174");
    }

    @Test
    void deliverInput() {
        boolean result;

        result = ram.deliverInput(null);
        verify(buffer, never()).write(anyString());
        assertFalse(result);

        result = ram.deliverInput("");
        verify(buffer, times(1)).write("");
        assertTrue(result);

        result = ram.deliverInput("A");
        verify(buffer, times(1)).write("A");
        assertTrue(result);

        result = ram.deliverInput("1");
        verify(buffer, times(1)).write("1");
        assertTrue(result);

        result = ram.deliverInput(" 17  ");
        verify(buffer, times(1)).write(" 17  ");
        assertTrue(result);
    }

    @Test
    void set() {
        Status status;

        status = ram.set(-1);
        assertEquals(status, Status.SET_ERROR);

        status = ram.set(0);
        assertEquals(status, Status.OK);

        status = ram.set(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void hop() {
        Status status;

        status = ram.hop(-1);
        assertEquals(status, Status.HOP);

        status = ram.hop(0);
        assertEquals(status, Status.HOP);

        status = ram.hop(1);
        assertEquals(status, Status.HOP);
    }

    @Test
    void add() {
        Status status;

        status = ram.add(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.add(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.add(1);
        assertEquals(status, Status.OK);

        status = ram.add(MEMORY_SIZE-1);
        assertEquals(status, Status.OK);

        status = ram.add(MEMORY_SIZE);
        assertEquals(status, Status.OK);

        status = ram.add(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void sub() {
        Status status;

        status = ram.sub(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.sub(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.sub(1);
        assertEquals(status, Status.OK);

        status = ram.sub(MEMORY_SIZE-1);
        assertEquals(status, Status.OK);

        status = ram.sub(MEMORY_SIZE);
        assertEquals(status, Status.OK);

        status = ram.sub(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void mul() {
        Status status;

        status = ram.mul(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.mul(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.mul(1);
        assertEquals(status, Status.OK);

        status = ram.mul(MEMORY_SIZE-1);
        assertEquals(status, Status.OK);

        status = ram.mul(MEMORY_SIZE);
        assertEquals(status, Status.OK);

        status = ram.mul(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void div() {
        Status status;

        status = ram.div(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.div(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.div(1);
        assertEquals(status, Status.DIVISION_BY_ZERO_ERROR);

        status = ram.div(MEMORY_SIZE-1);
        assertEquals(status, Status.DIVISION_BY_ZERO_ERROR);

        status = ram.div(MEMORY_SIZE);
        assertEquals(status, Status.DIVISION_BY_ZERO_ERROR);

        status = ram.div(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void lda() {
        Status status;

        status = ram.lda(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.lda(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.lda(1);
        assertEquals(status, Status.OK);

        status = ram.lda(MEMORY_SIZE-1);
        assertEquals(status, Status.OK);

        status = ram.lda(MEMORY_SIZE);
        assertEquals(status, Status.OK);

        status = ram.lda(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void ldk() {
        Status status;

        status = ram.ldk(-1);
        assertEquals(status, Status.OK);

        status = ram.ldk(0);
        assertEquals(status, Status.OK);

        status = ram.ldk(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void sta() {
        Status status;

        status = ram.sta(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.sta(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.sta(1);
        assertEquals(status, Status.OK);

        status = ram.sta(MEMORY_SIZE-1);
        assertEquals(status, Status.OK);

        status = ram.sta(MEMORY_SIZE);
        assertEquals(status, Status.OK);

        status = ram.sta(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void inpWithEmptyBuffer() {
        Status status;
        when(buffer.isEmpty()).thenReturn(true);

        status = ram.inp(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.inp(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.inp(1);
        assertEquals(status, Status.INPUT);

        status = ram.inp(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    // because when called twice, it will wait until an input occurs
    // -> the test will be freezed
    @Test
    void inpWithEmptyBuffer1() {
        Status status;
        when(buffer.isEmpty()).thenReturn(true);

        status = ram.inp(MEMORY_SIZE-1);
        assertEquals(status, Status.INPUT);
    }

    // because when called twice, it will wait until an input occurs
    // -> the test will be freezed
    @Test
    void inpWithEmptyBuffer2() {
        Status status;
        when(buffer.isEmpty()).thenReturn(true);

        status = ram.inp(MEMORY_SIZE);
        assertEquals(status, Status.INPUT);
    }

    @Test
    void inpWithFilledBuffer() {
        Status status;
        when(buffer.isEmpty()).thenReturn(false);

        status = ram.inp(-1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.inp(0);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.inp(1);
        assertEquals(status, Status.OK);

        status = ram.inp(MEMORY_SIZE-1);
        assertEquals(status, Status.OK);

        status = ram.inp(MEMORY_SIZE);
        assertEquals(status, Status.OK);

        status = ram.inp(MEMORY_SIZE+1);
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void out() {
        Status status;

        status = ram.out(-1);
        verify(buffer, never()).write(anyString());
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.out(0);
        verify(buffer, never()).write(anyString());
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);

        status = ram.out(1);
        verify(buffer, times(1)).write(anyString());
        assertEquals(status, Status.OUTPUT);

        status = ram.out(MEMORY_SIZE-1);
        verify(buffer, times(2)).write(anyString());
        assertEquals(status, Status.OUTPUT);

        status = ram.out(MEMORY_SIZE);
        verify(buffer, times(3)).write(anyString());
        assertEquals(status, Status.OUTPUT);

        status = ram.out(MEMORY_SIZE+1);
        verify(buffer, times(3)).write(anyString());
        assertEquals(status, Status.MEMORY_ADDRESS_ERROR);
    }

    @Test
    void jmp() {
        Status status;

        status = ram.jmp(-1);
        assertEquals(status, Status.OK);

        status = ram.jmp(0);
        assertEquals(status, Status.OK);

        status = ram.jmp(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void jez() {
        Status status;

        status = ram.jez(-1);
        assertEquals(status, Status.OK);

        status = ram.jez(0);
        assertEquals(status, Status.OK);

        status = ram.jez(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void jne() {
        Status status;

        status = ram.jne(-1);
        assertEquals(status, Status.OK);

        status = ram.jne(0);
        assertEquals(status, Status.OK);

        status = ram.jne(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void jlz() {
        Status status;

        status = ram.jlz(-1);
        assertEquals(status, Status.OK);

        status = ram.jlz(0);
        assertEquals(status, Status.OK);

        status = ram.jlz(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void jle() {
        Status status;

        status = ram.jle(-1);
        assertEquals(status, Status.OK);

        status = ram.jle(0);
        assertEquals(status, Status.OK);

        status = ram.jle(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void jgz() {
        Status status;

        status = ram.jgz(-1);
        assertEquals(status, Status.OK);

        status = ram.jgz(0);
        assertEquals(status, Status.OK);

        status = ram.jgz(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void jge() {
        Status status;

        status = ram.jge(-1);
        assertEquals(status, Status.OK);

        status = ram.jge(0);
        assertEquals(status, Status.OK);

        status = ram.jge(1);
        assertEquals(status, Status.OK);
    }

    @Test
    void hlt() {
        Status status = ram.hlt(99);
        assertEquals(status, Status.FINISH);
    }
}

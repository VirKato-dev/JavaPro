package virkato.otus.iterator;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BoxTest {

    @Test
    void testBox() {
        Iterator<String> it = new Box(List.of("a", "b", "c"), List.of("d", "e", "f"), List.of("g", "h", "i"), List.of("j", "k", "l"))
                .iterator();

        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
        }

        assertEquals("abcdefghijkl", sb.toString());
    }
}
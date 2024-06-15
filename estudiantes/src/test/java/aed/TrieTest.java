package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TrieTest {
    Trie<Integer> nuevoTrie = new Trie<Integer>();

    @Test
    void test_agregar(){
        
        nuevoTrie.agregar("nueve", 9);
        assertEquals(nuevoTrie.obtener("nueve"), 9); 

        nuevoTrie.agregar("nueve", 10);
        assertEquals(nuevoTrie.obtener("nueve"), 10); 

        nuevoTrie.agregar("noventa", 90);
        assertEquals(nuevoTrie.obtener("noventa"), 90); 

        nuevoTrie.agregar("novecientos", 900);
        assertEquals(nuevoTrie.obtener("novecientos"), 900); 
    }

    @Test
    void test_borrar() {

        Trie<Integer> trie = new Trie<Integer>();

        trie.agregar("copia", 9);
        assertEquals(trie.obtener("copia"), 9); 
        trie.agregar("nuev", 8);
        assertEquals(trie.obtener("nuev"), 8); 
        trie.agregar("nueve", 9);
        assertEquals(trie.obtener("nueve"), 9); 
        trie.agregar("nuevos", 1);
        assertEquals(trie.obtener("nuevos"), 1);
        trie.agregar("nueva", 2);
        assertEquals(trie.obtener("nueva"), 2);
        trie.agregar("nuevo", 13);
        assertEquals(trie.obtener("nuevo"), 13);

        assertTrue(trie.pertenece("copia"));
        assertTrue(trie.pertenece("nuev"));
        assertTrue(trie.pertenece("nuevos"));
        assertTrue(trie.pertenece("nueva"));
        assertTrue(trie.pertenece("nuevo"));

        trie.borrar("nuevo");
        assertTrue(trie.pertenece("nuevos"));
        assertFalse(trie.pertenece("nuevo"));

        trie.borrar("nuev");
        assertTrue(trie.pertenece("copia"));
        assertFalse(trie.pertenece("nuev"));
        assertTrue(trie.pertenece("nuevos"));
        assertTrue(trie.pertenece("nueva"));
        assertFalse(trie.pertenece("nuevo"));
    }


}

package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TrieTest {
    Trie<Integer> nuevoTrie = new Trie();

    @Test
    void test_agregar(){
        
        nuevoTrie.agregar("nueve", 9);
        assertEquals(nuevoTrie.obtenerSignificado("nueve"), 9); 
    }
}

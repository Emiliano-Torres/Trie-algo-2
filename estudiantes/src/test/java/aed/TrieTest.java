import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;import Junit.Assertions.*;

public class TrieTest {
    Trie<Integer> nuevoTrie = new Trie();

    @Test
    void test_agregar(){
        
        nuevoTrie.agregar("nueve", 9);
        assertEquals(nuevoTrie.obtener("nueve"), 9); 
    }
}

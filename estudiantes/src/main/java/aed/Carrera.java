package aed;

public class Carrera {
    
    private Trie<Materia> materias = new Trie<>();

    public Trie<Materia> getMaterias(){
        return this.materias;
    }
}

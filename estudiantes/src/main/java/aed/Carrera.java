package aed;
/*INV REP : (paraToda infomateria en infomaterias)(paraTodo parCarreraMateria en infomateria)(si la carrera "c" esta dentro de la infomateria 
            todas las materias que estan en el trie tienen a "c" como carrera)*/
public class Carrera {
    //O(1)
    private Trie<Materia> materias = new Trie<>();
    //O(1)
    public Trie<Materia> getMaterias(){
        return this.materias;
    }
}

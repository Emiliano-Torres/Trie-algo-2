package aed;
/*INV REP : (para todo i entero)(0<=i<=|profesores|->L profesores[i]>=0)
            |infomateria|>0
            |C|>=|carreras|>=0
            |E|>=|alumnos|>=0
*/
public class Materia {
    private Lista_enlazada<Carrera> carreras;
    private InfoMateria info;
    private Lista_enlazada<String> alumnos;
    private int[] profesores = new int[4];
    /* 
     * profesores[3]=AY2
     * profesores[2]=AY1
     * profesores[1]=JTP
     * profesores[0]=PROF
     * 
    */
    //O(1)
    public Materia (InfoMateria info){
        this.info=info;
        carreras= new Lista_enlazada<>();
        alumnos= new Lista_enlazada<>();
        
    }
    //O(1)
    public Lista_enlazada<String> getAlumnos(){
        return this.alumnos;
    }
    //O(1)
    public Lista_enlazada<Carrera> getCarreras(){
        return this.carreras;
    }
    //O(1)
    public int[] getProfesores(){
        return this.profesores;        
    }
    //O(1)
    public InfoMateria getInfoMateria(){
        return this.info;
    }
}

package aed;

public class Materia {
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
    public Materia (InfoMateria info){
        this.info=info;
        alumnos= new Lista_enlazada<>();
        
    }
    
    public Lista_enlazada<String> getAlumnos(){
        return this.alumnos;
    }

    public int[] getProfesores(){
        return this.profesores;        
    }
    public InfoMateria getInfoMateria(){
        return this.info;
    }
}

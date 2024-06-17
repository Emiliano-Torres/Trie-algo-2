package aed;

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
    public Materia (InfoMateria info){
        this.info=info;
        carreras= new Lista_enlazada<>();
        alumnos= new Lista_enlazada<>();
        
    }
    
    public Lista_enlazada<String> getAlumnos(){
        return this.alumnos;
    }
    public Lista_enlazada<Carrera> getCarreras(){
        return this.carreras;
    }

    public int[] getProfesores(){
        return this.profesores;        
    }
    public InfoMateria getInfoMateria(){
        return this.info;
    }
}

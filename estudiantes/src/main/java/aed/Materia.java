package aed;

public class Materia {
    private InfoMateria info;
    private Lista_enlazada<Alumno> alumnos;
    private int[] profesores = new int[4];
    
    public Materia (InfoMateria info){
        this.info=info;
        alumnos= new Lista_enlazada<>();
        
    } 
}

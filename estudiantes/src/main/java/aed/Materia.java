package aed;

public class Materia {
    private InfoMateria info;
    private Lista_enlazada<String> alumnos;
    private int[] profesores = new int[4];
    
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
    
}

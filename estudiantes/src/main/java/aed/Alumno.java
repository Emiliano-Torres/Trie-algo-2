package aed;
/*
 * INV REP: |M| > Inscripciones >=0 con M siendo el conjunto de todas las materias
 *          LU esta acotado
 *          
*/
public class Alumno {
    
    // Cantidad de materias en las que esta inscripto el alumno
    private int inscripciones;

    // L.U del alumno

    private String LU;

    // Constructor
    //O(1)
    public Alumno(String LU) {
        inscripciones = 0;
        this.LU = LU;
    }

    // Metodo para obtener la cantidad de materias en las que esta inscripto el alumno
    //O(1)
    public int obtenerInscripciones() {
        return inscripciones;
    }

    // Metodo para aumentar la cantidad de materias en las que esta inscripto el alumno
    //O(1)
    public void aumentarInscripciones() {
        inscripciones +=1 ;
    }

    // Metodo para obtener la L.U del alumno
    //O(1)
    public String obtenerLU() {
        return LU;
    }
    //O(1)
    public void reducirInscripciones() {
        inscripciones -=1 ;
    }
}

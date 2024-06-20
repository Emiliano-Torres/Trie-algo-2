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
    public Alumno(String LU) {
        inscripciones = 0;
        this.LU = LU;
    }

    // Metodo para obtener la cantidad de materias en las que esta inscripto el alumno
    public int obtenerInscripciones() {
        return inscripciones;
    }

    // Metodo para aumentar la cantidad de materias en las que esta inscripto el alumno
    public void aumentarInscripciones() {
        inscripciones +=1 ;
    }

    // Metodo para obtener la L.U del alumno
    public String obtenerLU() {
        return LU;
    }

    public void reducirInscripciones() {
        inscripciones -=1 ;
    }
}

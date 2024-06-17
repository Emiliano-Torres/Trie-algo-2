package aed;

public class SistemaSIU {

    public Trie<Alumno> alumnos = new Trie<>();
    public Trie <Carrera> carreras = new Trie<>();

    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }
    //O(Sumatoria desde 1 a |M| de (Sumatoria desde 1 a |C| de (|c|+|Mc| + E)
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        // O(E)
        for (String libreta : libretasUniversitarias){
            Alumno alumno = new Alumno(libreta);
            this.alumnos.agregar(libreta, alumno);
        } 
        //O(Sumatoria desde 1 a |M| de (Sumatoria desde 1 a |C| de (|c|+|Mc|) 
        for (InfoMateria infomateria : infoMaterias){
            Materia materia = new Materia(infomateria);
            /*O(Sumatoria desde 1 a |Nm| de (|c|+|Mc|) pero nos quedamos con esta 
            O(Sumatoria desde 1 a |C| de (|c|+|Mc|  porque es cota superior,
            porque vale esto |Carreras| >= |Nm|)*/
            for (ParCarreraMateria par : infomateria.getParesCarreraMateria()) {
                //O(|c|+|Mc|)              
                if (carreras.pertenece(par.getCarrera())) {

                    Carrera carrera = carreras.obtener(par.getCarrera());
                    carrera.getMaterias().agregar(par.getNombreMateria(),materia);

                }
                else{

                    Carrera carrera = new Carrera();
                    this.carreras.agregar(par.getCarrera(),carrera);
                    carrera.getMaterias().agregar(par.getNombreMateria(),materia);
                }
                }
        }
    }

    public void inscribir(String estudiante, String carrera, String materia){
        //O(|carrera|+|materia|+6) = O(|carrera|+|materia|)
        Alumno inscripto=this.alumnos.obtener(estudiante);
        inscripto.aumentarInscripciones();

        //O(|carrera|+|materia|)
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        //O(|materia|)
        materias.obtener(materia).getAlumnos().agregarAdelante(estudiante);
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int[] plantelDocente(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void cerrarMateria(String materia, String carrera){
        InfoMateria infomateria = this.carreras.obtener(carrera).getMaterias().obtener(materia).getInfoMateria();
        Lista_enlazada lista = this.carreras.obtener(carrera).getMaterias().obtener(materia).getAlumnos();
        for (int i = 0; i < lista.longitud(); i++){
            String lu = (String)lista.obtener(i);
            alumnos.obtener(lu).reducirInscripciones();
        }
        for (ParCarreraMateria par : infomateria.getParesCarreraMateria()){
            carreras.obtener(par.getCarrera()).getMaterias().borrar(par.getNombreMateria());
        }
    }

    public int inscriptos(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public boolean excedeCupo(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }
}

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
        //O(|carrera|+|materia|+7) = O(|carrera|+|materia|)
        Alumno inscripto=this.alumnos.obtener(estudiante);
        inscripto.aumentarInscripciones();

        //O(|carrera|+|materia|)
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        //O(|materia|)
        materias.obtener(materia).getAlumnos().agregarAdelante(estudiante);
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        int[] profesores = materias.obtener(materia).getProfesores();
        if (cargo==CargoDocente.AY2) {
            profesores[3]++;
        }
        if (cargo==CargoDocente.AY1) {
            profesores[2]++;
        }
        if (cargo==CargoDocente.JTP) {
            profesores[1]++;
        }
        if (cargo==CargoDocente.PROF) {
            profesores[0]++;
        }	    
    }

    public int[] plantelDocente(String materia, String carrera){
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        return materias.obtener(materia).getProfesores();
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        int cant_alumnos = materias.obtener(materia).getAlumnos().longitud();
        return cant_alumnos;
    }

    public boolean excedeCupo(String materia, String carrera){
        int cant_alumnos = inscriptos(materia, carrera);
        int cupo = cupo(materia, carrera);
        return cant_alumnos > cupo;

    }

    public int cupo(String materia, String carrera) {
        int[] plantelDocente = plantelDocente(materia, carrera);
        plantelDocente[0] = plantelDocente[0]*250;
        plantelDocente[1] = plantelDocente[1]*100;
        plantelDocente[2] = plantelDocente[2]*20;
        plantelDocente[3] = 30*plantelDocente[3];
        int res = plantelDocente[0];
        for (int i : plantelDocente) {
            if (i < res) {
                res = i;
            }
        }
        return res;
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        Alumno alumno = this.alumnos.obtener(estudiante);
        return alumno.obtenerInscripciones();
    }
}

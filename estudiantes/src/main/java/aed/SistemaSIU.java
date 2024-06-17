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
                //tomamos como peor caso que la carrera no este en el sistema
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
    //O(|carrera|+|materia|)
    public void inscribir(String estudiante, String carrera, String materia){
        //O(1)
        Alumno inscripto=this.alumnos.obtener(estudiante);
        inscripto.aumentarInscripciones();

        //O(|carrera|)
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        //O(|materia|)
        materias.obtener(materia).getAlumnos().agregarAdelante(estudiante);
    }
    //O(|carrera|+|materia|)
    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        //O(|carrera|)
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        //O(|materia|)
        int[] profesores = materias.obtener(materia).getProfesores();
        //O(1)
        if (cargo==CargoDocente.AY2) {
            profesores[3]++;
        }
        //O(1)
        if (cargo==CargoDocente.AY1) {
            profesores[2]++;
        }
        //O(1)
        if (cargo==CargoDocente.JTP) {
            profesores[1]++;
        }
        //O(1)
        if (cargo==CargoDocente.PROF) {
            profesores[0]++;
        }	    
    }
    //O(|carrera|+|materia|)
    public int[] plantelDocente(String materia, String carrera){
        //O(|carrera|)
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        //O(|materia|)
        return materias.obtener(materia).getProfesores();
    }

    public void cerrarMateria(String materia, String carrera){
        InfoMateria infomateria = this.carreras.obtener(carrera).getMaterias().obtener(materia).getInfoMateria();
        Lista_enlazada<String> lista = this.carreras.obtener(carrera).getMaterias().obtener(materia).getAlumnos();
        for (int i = 0; i < lista.longitud(); i++){
            String lu = (String)lista.obtener(i);
            alumnos.obtener(lu).reducirInscripciones();
        }
        for (ParCarreraMateria par : infomateria.getParesCarreraMateria()){
            carreras.obtener(par.getCarrera()).getMaterias().borrar(par.getNombreMateria());
        } 
    }
    //O(|carrera|+|Materia|)
    public int inscriptos(String materia, String carrera){
        //O(|carrera|)
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();
        //O(|materia|)
        int cant_alumnos = materias.obtener(materia).getAlumnos().longitud();
        //O(1)
        return cant_alumnos;
    }
    
    public boolean excedeCupo(String materia, String carrera){
        int cant_alumnos = inscriptos(materia, carrera);
        int cupo = cupo(materia, carrera);
        return cant_alumnos > cupo;

    }

    public int cupo(String materia, String carrera) {
        int[] plantelDocente = plantelDocente(materia, carrera);
        int[] array = new int[4];
        array[0] = plantelDocente[0]*250;
        array[1] = plantelDocente[1]*100;
        array[2] = plantelDocente[2]*20;
        array[3] = 30*plantelDocente[3];
        int res = array[0];
        for (int i : array) {
            if (i < res) {
                res = i;
            }
        }
        return res;
    }

    public String[] carreras(){
        return carreras.toStringArray();
    }

    public String[] materias(String carrera){
        Carrera objeto_carrera = carreras.obtener(carrera);
        return objeto_carrera.getMaterias().toStringArray();
    }

    public int materiasInscriptas(String estudiante){
        Alumno alumno = this.alumnos.obtener(estudiante);
        return alumno.obtenerInscripciones();
    }
}

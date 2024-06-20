package aed;
/*  INV REP: Todas las ramas del trie alumnos tienen la misma longitud.
             Toda rama de ambos tries termina en un significado, en el trie de alumnos en un objeto de clase Alumno, en el de carreras en un objeto Carrera.
             Toda rama en el trie de materias que esta en cada carrera, termina en un significado, un objeto  de clase Materia.
             Toda rama tiene al menos un caracter.
             Existe al menos una rama en el trie carreras.
*/

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
        for (String libreta : libretasUniversitarias) {
            // Agregar cada estudiante al Trie de alumnos
            Alumno alumno = new Alumno(libreta);
            alumnos.agregar(libreta, alumno);
        }
        
        for (InfoMateria infoMateria: infoMaterias) {
            // Iterar sobre cada ParCarreraMateria en InfoMateria
            Materia materiaNueva = new Materia(infoMateria);
            Lista_enlazada<Carrera> lista_carreras = materiaNueva.getCarreras();
            for (ParCarreraMateria par: infoMateria.getParesCarreraMateria()) {
                String carrera = par.getCarrera();
                String materia = par.getNombreMateria();

                // Verificar si la carrera ya existe en el trie de carreras
                if (carreras.pertenece(carrera)) {
                    // Si existe, obtener la carrera y agregar la materia
                    Carrera carreraExistente = carreras.obtener(carrera);
                    carreraExistente.getMaterias().agregar(materia, materiaNueva);
                    lista_carreras.agregarAdelante(carreraExistente);
                } else{
                    // Si no existe, crear una nueva carrera y agregar la materia
                    Carrera nuevCarrera = new Carrera();
                    nuevCarrera.getMaterias().agregar(materia, materiaNueva);
                    carreras.agregar(carrera, nuevCarrera);
                    lista_carreras.agregarAdelante(nuevCarrera);
                }
                
            }
        }

        /* 
        //O(Sumatoria desde 1 a |M| de (Sumatoria desde 1 a |C| de (|c|+|Mc|) 
        for (int i =0; i<infoMaterias.length;i++){
            Materia materia = new Materia(infoMaterias[i]);
            Lista_enlazada<Carrera> lista_carreras = materia.getCarreras();
            // O(Sumatoria desde 1 a |Nm| de (|c|+|Mc|) pero nos quedamos con esta 
            // O(Sumatoria desde 1 a |C| de (|c|+|Mc|  porque es cota superior,
            // porque vale esto |Carreras| >= |Nm|)
            for (ParCarreraMateria par : infoMaterias[i].getParesCarreraMateria()) {
                //tomamos como peor caso que la carrera no este en el sistema
                //O(|c|+|Mc|)              
                if (carreras.pertenece(par.getCarrera())) {

                    Carrera carrera = carreras.obtener(par.getCarrera());
                    carrera.getMaterias().agregar(par.getNombreMateria(),materia);
                    lista_carreras.agregarAdelante(carrera);
                    
                }
                else{
                    Carrera carrera = new Carrera();
                    this.carreras.agregar(par.getCarrera(),carrera);
                    carrera.getMaterias().agregar(par.getNombreMateria(),materia);
                    lista_carreras.agregarAdelante(carrera);
                }
                }
        }*/
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
    // O(|carrera| + |nombre de la materia|+Em+Sumatoria para todo n en Nm de |n|)
    public void cerrarMateria(String materia, String carrera){
        //O(|carrera| + |nombre de la materia|)
        Materia materia_a_borrar=this.carreras.obtener(carrera).getMaterias().obtener(materia);
        //O(1)
        ParCarreraMateria[] Lista_nombres = materia_a_borrar.getInfoMateria().getParesCarreraMateria();
        //O(1)
        Lista_enlazada<String> lista = materia_a_borrar.getAlumnos();
        //O(Em)
        int cantidad_alumnos_a_desinscribir=lista.longitud();
        for (int i = 0; i < cantidad_alumnos_a_desinscribir; i++){
            //O(1)
            String lu = (String)lista.obtenerUltimo();
            //O(1)
            alumnos.obtener(lu).reducirInscripciones();
            //O(1)
            lista.eliminar_ultimo();
        }
        //O(1)
        Lista_enlazada<Carrera> carreras_donde_borrar= materia_a_borrar.getCarreras();
        // Sumartoria para todo n en Nm de |n| + |Nm|
        int cantidad_de_carreras_a_borrar=carreras_donde_borrar.longitud();
        for (int i=0; i<cantidad_de_carreras_a_borrar; i++){
            //O(1)
            Carrera carrera_actual = carreras_donde_borrar.obtenerUltimo();
            //O(|Nm|)
            carrera_actual.getMaterias().borrar(Lista_nombres[i].getNombreMateria());             
            //O(1)
            carreras_donde_borrar.eliminar_ultimo();
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
    
     // O(|carrera|+|materia|)
     public boolean excedeCupo(String materia, String carrera){
        //O(|carrera|+|materia|)
        int cant_alumnos = inscriptos(materia, carrera);
        //O(|carrera|+|materia|)
        int cupo = cupo(materia, carrera);
        return cant_alumnos > cupo;

    }

    // O(|c|+|m|)
    public int cupo(String materia, String carrera) {
        // O(|c|+|m|)
        int[] plantelDocente = plantelDocente(materia, carrera);
        // O(1)
        int[] array = new int[4];
        // O(1)
        array[0] = plantelDocente[0]*250;
        // O(1)
        array[1] = plantelDocente[1]*100;
        // O(1)
        array[2] = plantelDocente[2]*20;
        // O(1)
        array[3] = 30*plantelDocente[3];
        // O(1)
        int res = array[0];
        // O(1) porque el bucle se repite una cantidad acotada de veces
        for (int i : array) {
            // O(1)
            if (i < res) {
                // O(1)
                res = i;
            }
        }
        // O(1)
        return res;
    }
    
    // O(Sumatoria desde 1 a |C| de (|c|)
    // Con "C" el conjunto de carreras y "c" el nombre (String) de una (cada) carrera.
    public String[] carreras(){
        // O(Sumatoria desde 1 a |C| de (|c|)
        // Ver el método ".toStringArray()" de la clase Trie.
        return carreras.toStringArray();
    }

    // O(|c| + Sumatoria desde 1 a |Mc| de (|mc|)
    // Con "c" el nombre (String) de la carrera pasada por parámetro, "Mc" el conjunto de materias correspondiente a dicha materia, y "mc" el nombre (string) de una (cada) materia.
    public String[] materias(String carrera){
        // obtener(c) tiene O(|c|)
        Carrera objeto_carrera = carreras.obtener(carrera);
        // O(Sumatoria desde 1 a |Mc| de (|mc|)
        // Ver el método ".toStringArray()" de la clase Trie.
        return objeto_carrera.getMaterias().toStringArray();
    }

    // O(1)
    public int materiasInscriptas(String estudiante){
        // O(1) pues la LU de cada estudiante esta acotada
        Alumno alumno = this.alumnos.obtener(estudiante);
        // O(1)
        return alumno.obtenerInscripciones();
    }
}


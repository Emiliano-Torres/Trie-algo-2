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
    //O(Sumatoria c en C de(|c|*|Mc|) + sumatoria m en M de(sumatoria n en Nm de(|n|))+ E)
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        
        // O(E)
        for (String libreta : libretasUniversitarias) {
            // Agregar cada estudiante al Trie de alumnos
            Alumno alumno = new Alumno(libreta);
            alumnos.agregar(libreta, alumno);
        }
        /*  O(Sumatoria m en M de(sumatoria n en Nm de(|c|)) + sumatoria m en M de(sumatoria n en Nm de(|n|))
         *
         * Luego sumar todos los nombres de todas las materias es lo mismo que sumar todos los nombres por carrera para todas las carreras.
         *  
         *  O(Sumatoria m en M de(sumatoria n en Nm de(|c|)) = O(Sumatoria c en C de(sumatoria mc en Mc de(|c|))
         *  
         * Luego |c| no depende de mc en Mc, sale multiplicando de la sumatoria
         * 
         *  O(Sumatoria m en M de(sumatoria n en Nm de(|c|))=O(Sumatoria c en C de(|c|*sumatoria mc en Mc de(1))
         *  
         * Luego:
         *  
         * O(Sumatoria m en M de(sumatoria n en Nm de(|c|))=O(Sumatoria c en C de(|c|*|Mc|)
         * 
         * 
         * Reemplazando en el primer renglón, obtenemos que:
         * 
         * O(Sumatoria m en M de(sumatoria n en Nm de(|c|)) + sumatoria m en M de(sumatoria n en Nm de(|n|)) = O(Sumatoria c en C de(|c|*|Mc|) + sumatoria m en M de(sumatoria n en Nm de(|n|))
         * 
         * Lo que nos asegura la complejidad requerida en la definición de la función
        */
        for (InfoMateria infoMateria: infoMaterias/*|M|*/) {
            // Iterar sobre cada ParCarreraMateria en InfoMateria
            Materia materiaNueva = new Materia(infoMateria);//O(1)
            Lista_enlazada<Carrera> lista_carreras = materiaNueva.getCarreras();//O(1)

            /*O(sumatoria n en Nm de(|c|+|n|) */
            for (ParCarreraMateria par: infoMateria.getParesCarreraMateria()/*|Nm|*/) {
                String carrera = par.getCarrera();
                String materia = par.getNombreMateria();
                // Verificar si la carrera ya existe en el trie de carreras
                if (carreras.pertenece(carrera) /*|c|*/) {
                    // Si existe, obtener la carrera y agregar la materia
                    Carrera carreraExistente = carreras.obtener(carrera); //O(c)
                    carreraExistente.getMaterias().agregar(materia, materiaNueva); //O(n)
                    lista_carreras.agregarAdelante(carreraExistente); //O(1)
                } else{
                    // Si no existe, crear una nueva carrera y agregar la materia
                    Carrera nuevCarrera = new Carrera(); //O(1)
                    nuevCarrera.getMaterias().agregar(materia, materiaNueva); //O(n)
                    carreras.agregar(carrera, nuevCarrera); //O(c)
                    lista_carreras.agregarAdelante(nuevCarrera); //O(1)
                }
                
            }
        }
    }
    //O(|c|+|m|)
    public void inscribir(String estudiante, String carrera, String materia){
        
        Alumno inscripto=this.alumnos.obtener(estudiante); //O(1)
        inscripto.aumentarInscripciones();

        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias(); //O(|c|)
        materias.obtener(materia).getAlumnos().agregarAdelante(estudiante); //O(|m|)
    }

    //O(|c|+|m|)
    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias();  //O(|c|)
        int[] profesores = materias.obtener(materia).getProfesores(); //O(|m|)
        
        if (cargo==CargoDocente.AY2) { //O(1)
            profesores[3]++;
        }
        
        if (cargo==CargoDocente.AY1) { //O(1)
            profesores[2]++;
        }
        
        if (cargo==CargoDocente.JTP) { //O(1)
            profesores[1]++;
        }
        
        if (cargo==CargoDocente.PROF) { //O(1)
            profesores[0]++;
        }	    
    }

    //O(|c|+|m|)
    public int[] plantelDocente(String materia, String carrera){
        
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias(); //O(|c|)
        return materias.obtener(materia).getProfesores();  //O(|m|)
    }

    // O(|c| + |n| + Em + Sumatoria para todo n en Nm de |n|)
    public void cerrarMateria(String materia, String carrera){
        
        Materia materia_a_borrar=this.carreras.obtener(carrera).getMaterias().obtener(materia); //O(|c| + |n|)
        
        ParCarreraMateria[] Lista_nombres = materia_a_borrar.getInfoMateria().getParesCarreraMateria();  //O(1)
       
        Lista_enlazada<String> lista = materia_a_borrar.getAlumnos(); //O(1)
        
        int cantidad_alumnos_a_desinscribir=lista.longitud(); //O(1)
        for (int i = 0; i < cantidad_alumnos_a_desinscribir; i++){ //O(Em)
            
            String lu = (String)lista.obtenerUltimo(); //O(1)
            
            alumnos.obtener(lu).reducirInscripciones(); //O(1)
            
            lista.eliminar_ultimo(); //O(1)
        }
        
        Lista_enlazada<Carrera> carreras_donde_borrar= materia_a_borrar.getCarreras(); //O(1)
        int cantidad_de_carreras_a_borrar=carreras_donde_borrar.longitud();
        for (int i=0; i<cantidad_de_carreras_a_borrar; i++){ // Sumartoria para todo n en Nm de |n| 
           
            Carrera carrera_actual = carreras_donde_borrar.obtenerUltimo(); //O(1)
            carrera_actual.getMaterias().borrar(Lista_nombres[i].getNombreMateria()); //O(|n|)             
            carreras_donde_borrar.eliminar_ultimo();  //O(1)
        } 
    }

    //O(|c|+|m|)
    public int inscriptos(String materia, String carrera){
        
        Trie <Materia> materias = this.carreras.obtener(carrera).getMaterias(); //O(|c|)
        
        int cant_alumnos = materias.obtener(materia).getAlumnos().longitud(); //O(|m|)
        
        return cant_alumnos;
    }
    
     // O(|c|+|m|)
     public boolean excedeCupo(String materia, String carrera){
        
        int cant_alumnos = inscriptos(materia, carrera); //O(|c|+|m|)
        int cupo = cupo(materia, carrera); //O(|c|+|m|)
        return cant_alumnos > cupo;

    }

    // O(|c|+|m|)
    public int cupo(String materia, String carrera) {
        
        int[] plantelDocente = plantelDocente(materia, carrera); // O(|c|+|m|)
        
        int[] array = new int[4]; // O(1)
       
        array[0] = plantelDocente[0]*250; // O(1)
       
        array[1] = plantelDocente[1]*100; // O(1)
        
        array[2] = plantelDocente[2]*20; // O(1)
        
        array[3] = 30*plantelDocente[3]; // O(1)
       
        int res = array[0];
        // O(1) porque el bucle se repite una cantidad acotada de veces
        for (int i : array) {
            
            if (i < res) {  // O(1)
               
                res = i;  // O(1)
            }
        }
        return res;
    }
    
    // O(Sumatoria c en |C| de (|c|)
    public String[] carreras(){
        // O(Sumatoria c a |C| de (|c|)
        // Ver el método ".toStringArray()" de la clase Trie.
        return carreras.toStringArray();
    }

    // O(|c| + Sumatoria mc en|Mc| de (|mc|)
    public String[] materias(String carrera){
        
        Carrera objeto_carrera = carreras.obtener(carrera); //O(|c|)
        // O(Sumatoria mc en Mc de (|mc|)
        // Ver el método ".toStringArray()" de la clase Trie.
        return objeto_carrera.getMaterias().toStringArray();
    }

    // O(1)
    public int materiasInscriptas(String estudiante){
        
        Alumno alumno = this.alumnos.obtener(estudiante); // O(1) pues la LU de cada estudiante esta acotada
        return alumno.obtenerInscripciones(); // O(1)
    }
}


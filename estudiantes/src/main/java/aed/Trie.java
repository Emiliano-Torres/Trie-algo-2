package aed;

//Inv rep: raíz es una instancia válida de Nodo_Trie.
public class Trie<T>{
    
    private Nodo_Trie<T> raiz;
    
    /*
    INV rep de la clase Nodo: hijos.length == 256.
                        paraTodo i in 0..255: hijos[i] == null || hijos[i] es instancia de Nodo_Trie
                        nodo es final de una palabra si y solo si nodo.significado != null.
                        nodo no es final de una palabra si y solo si nodo.significado == null.
                        forall hijo in hijos: hijo == null || hijo cumple todo lo que dice arriba.
    */

    private class Nodo_Trie<T> {

        // Array para los hijos (256 signos del codigo ASCII)
        private Nodo_Trie<T>[] hijos;

        // Significado del nodo
        private T significado;

         // Constructor.  O(1)
         public Nodo_Trie() {
            hijos = new Nodo_Trie[256];
            significado = null;
        }

        // Métodos para obtener y establecer hijos. O(1)
        public Nodo_Trie<T>[] obtenerHijos() {
           return hijos;
        }

        // Métodos para obtener significado. O(1)
        public T obtenerSignificado() {
           return significado;
        }

        // Métodos para definir significado. O(1)
        public void definirSignificado(T significado) {
           this.significado = significado;
        }

        // Método para verificar si tiene hijos. O(1) 
        public boolean tieneHijos() {
            // O(256) = O(2560)
           for (Nodo_Trie<T> hijo : hijos) {
               // O(1)
               if (hijo != null) {
                   // O(1)
                   return true;
               }
           }
           // O(1)
           return false;
        }

        // Método para la cantidad de hijos. O(1) 
        public int cantidadDeHijos() {
            // O(1)
            int res = 0;
            // O(256) = O(1)
            for (Nodo_Trie<T> hijo : hijos) {
                // O(1)
                if (hijo != null) {
                    // O(1)
                    res = res + 1;
                }
            }
            // O(1)
            return res;
         }
    }

    // Constructor. O(1)
    public Trie() {
       raiz = new Nodo_Trie<T>();
    }

    // Método para agregar una palabra. O(|palabra|)
    public void agregar(String palabra, T significado) {
        // O(1)
        Nodo_Trie<T> actual = raiz;
        // O(|palabra|)
        char[] arreglo_caracteres = palabra.toCharArray(); 
        // El metodo toCharArray tiene complejidad O(|palabra|) debido a que el metodo funciona de la siguiente manera:
        // Primero accede a cada caracter (acceder a cada carácter de la cadena original para copiarlo en el nuevo array y acceder a un caracter es O(1))
        // Luego, el método recorre todos los caracteres de la cadena en un bucle, copiándolos uno por uno en el nuevo array, ese bucle se repite |palabra| veces.
        // Finalmente, crear un nuevo array de caracteres también es una operación O(|palabra|) porque la memoria tiene que ser asignada para |palabra| elementos.
        // Si juntas todo te queda complejidad de O(|palabra|).
        
        // O(|palabra|) porque el bucle se repite |palabra| veces 
        for (char c : arreglo_caracteres) { 
            // O(1)
            int index = c; // El índice es el valor ASCII del carácter
            // O(1)
            if (actual.obtenerHijos()[index] == null) {
                // O(1)
                actual.obtenerHijos()[index] = new Nodo_Trie<T>();
            }
            // O(1)
            actual = actual.obtenerHijos()[index];
        } 
        // O(1)
        actual.definirSignificado(significado);
    }

    // Método para verificar si una palabra pertenece al trie
    // O(|palabra|)
    public boolean pertenece(String palabra) {
        // O(1)
        Nodo_Trie<T> actual = raiz;
        // O(|palabra|)
        char[] arreglo_caracteres = palabra.toCharArray(); 
        // O(|palabra|) porque el bucle se repite |palabra| veces 
        for (char c : arreglo_caracteres) {
            // O(1)
            int index = c; // El índice es el valor ASCII del carácter
            // O(1)
            if (actual.obtenerHijos()[index] == null) {
                // O(1)
                return false;
            }
            // O(1)
            actual = actual.obtenerHijos()[index];
        }
        // O(1)
        if (actual.obtenerSignificado() == null) {
            // O(1)
            return false;
        }
        // O(1)
        return true;
    }

    // Método para obtener el significado de unaa palabra en el trie
    // O(|palabra|)
    public T obtener(String palabra) {
        // O(1)
        Nodo_Trie<T> actual = raiz;
        // O(|palabra|)
        char[] arreglo_caracteres = palabra.toCharArray(); 
        // O(|palabra|) porque el bucle se repite |palabra| veces.
        for (char c : arreglo_caracteres) {
            // O(1)
            int index = c; // El índice es el valor ASCII del carácter
            // O(1)
            actual = actual.obtenerHijos()[index];
        }
        // O(1)
        return actual.obtenerSignificado();
    }

    // O(|palabra|)
    public void borrar(String palabra) {
        // O(1)
        Nodo_Trie<T> actual = raiz;
        // O(1)
        Nodo_Trie<T> ultimoImportante = raiz;
        // O(1)
        char u = palabra.charAt(0);
        // O(1)
        int ultimaDireccion = u;

        // O(|palabra|) pues el bucle se repite |palabra| veces
        for (int i=0; i < palabra.length(); i++) {
            // O(1)
            char c = palabra.charAt(i);
            // O(1)
            int charIndex = c; // El índice es el valor ASCII del carácter
            // O(1)
            if (actual.cantidadDeHijos() > 1 || actual.significado != null){
                // O(1)
                ultimoImportante = actual;
                // O(1)
                ultimaDireccion = charIndex;
            }
            // O(1)
            actual = actual.obtenerHijos()[charIndex];
        }
        // O(1)
        if (actual.tieneHijos()){
            // O(1)
            actual.significado = null;
        } else {
            // O(1)
            ultimoImportante.obtenerHijos()[ultimaDireccion] = null;
        }
    }


    // O(Sumatoria desde 1 a |A| de (|string|)
    // Con "A" el Trie y "string" el camino (clave)
    public String[] toStringArray() {
        // O(1)
        Lista_enlazada<String> lista = new Lista_enlazada();
        // O(1)
        String camino = "";
        
        // O(Sumatoria desde 1 a |A| de (|string|)
        toListRecursivo(raiz, lista, camino);
        
        // O(1)
        String[] res = new String[lista.longitud()];
        
        // O(lista.longitud())
        for (int i=0; i<lista.longitud(); i++){
            // O(1)
            String elemento = (String) lista.obtener(i);
            // O(1)
            res[i] = elemento;
        }
        
        // O(1)
        return res;
        // Finalmente O(lista.longitud() + Sumatoria desde 1 a |A| de (|string|) = O (Sumatoria desde 1 a |A| de (|string|)
    }



    // O(Sumatoria desde 1 a |A| de (|string|)
    private void toListRecursivo(Nodo_Trie<T> nodo, Lista_enlazada<String> lista, String camino){
        // O(1)
        if (nodo.significado != null){
            // O(1)
            lista.agregarAtras(camino);
        }

        // O(Sumatoria desde 1 a |A| de (|string|)
        for (int i=0; i<256; i++){
            // O(Sumatoria desde 1 a |A| de (|string|) = O(Sumatoria desde 1 a |A| de (|string|)
            if (nodo.obtenerHijos()[i] != null){
                // O(1)
                Nodo_Trie<T> hijo = nodo.obtenerHijos()[i];
                // O(1)
                char caracter = (char) i;
                // O(1)
                String nuevo_camino = camino + caracter;
                // O(Sumatoria desde 1 a |B| de (|sub-string|)
                // Con "B" el "Sub-Trie" y "sub-string" el camino restante (parte de la clave que falta)
                // Nótese que |B| (osea la cantidad de claves en el subconjunto), y |sub-string| se achican a lo largo de la recursión
                toListRecursivo(hijo, lista, nuevo_camino);
            }
        }
    }


}
package aed;

public class Trie<T>{

    private Nodo_Trie<T> raiz;

    private class Nodo_Trie<T> {

        // Array para los hijos (256 signos del codigo ASCII)
        private Nodo_Trie<T>[] hijos;

        // Significado del nodo
        private T significado;

         // Constructor
         public Nodo_Trie() {
            hijos = new Nodo_Trie[256];
            significado = null;
        }

        // Métodos para obtener y establecer hijos y la marca de fin de palabra

        public Nodo_Trie<T>[] obtenerHijos() {
           return hijos;
        }

        public T obtenerSignificado() {
           return significado;
        }

        public void definirSignificado(T significado) {
           this.significado = significado;
        }

        // Método para verificar si tiene hijos
        public boolean tieneHijos() {
           for (Nodo_Trie<T> hijo : hijos) {
               if (hijo != null) {
                   return true;
               }
           }
           return false;
        }

        public int cantidadDeHijos() {
            int res = 0;
            for (Nodo_Trie<T> hijo : hijos) {
                if (hijo != null) {
                    res = res + 1;
                }
            }
            return res;
         }
    }

    // Constructor
    public Trie() {
       raiz = new Nodo_Trie<T>();
    }

    // Método para agregar una palabra
    public void agregar(String palabra, T significado) {
        Nodo_Trie<T> actual = raiz;
        for (char c : palabra.toCharArray()) { 
            // El metodo toCharArray tiene complejidad O(n) debido a que el metodo funciona de la siguiente manera:
            // Primero accede a cada caracter (acceder a cada carácter de la cadena original para copiarlo en el nuevo array y acceder a un caracter es O(1))
            // Luego, el método recorre todos los caracteres de la cadena en un bucle, copiándolos uno por uno en el nuevo array, ese bucle se repite n veces.
            // Finalmente, crear un nuevo array de caracteres también es una operación O(n) porque la memoria tiene que ser asignada para n elementos.
            // Si juntas todo te queda complejidad de O(n).
            int index = c; // El índice es el valor ASCII del carácter
            if (actual.obtenerHijos()[index] == null) {
                actual.obtenerHijos()[index] = new Nodo_Trie<T>();
            }
            actual = actual.obtenerHijos()[index];
        } 
        actual.definirSignificado(significado);
    }

    // Método para verificar si una palabra pertenece al trie
    public boolean pertenece(String palabra) {
        Nodo_Trie<T> actual = raiz;
        for (char c : palabra.toCharArray()) {
            int index = c; // El índice es el valor ASCII del carácter
            if (actual.obtenerHijos()[index] == null) {
                return false;
            }
            actual = actual.obtenerHijos()[index];
        }
        if (actual.obtenerSignificado() == null) {
            return false;
        }
        return true;
    }

    // Método para obtener el significado de unaa palabra en el trie
    public T obtener(String palabra) {
        Nodo_Trie<T> actual = raiz;
        for (char c : palabra.toCharArray()) {
            int index = c; // El índice es el valor ASCII del carácter
            actual = actual.obtenerHijos()[index];
        }
        return actual.obtenerSignificado();
    }

    public void borrar(String palabra) {
        Nodo_Trie<T> actual = raiz;
        Nodo_Trie<T> ultimoImportante = raiz;
        
        char u = palabra.charAt(0);
        int ultimaDireccion = u;

        for (int i=0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            int charIndex = c; // El índice es el valor ASCII del carácter

            if (actual.cantidadDeHijos() > 1 || actual.significado != null){
                ultimoImportante = actual;
                ultimaDireccion = charIndex;
            }
            actual = actual.obtenerHijos()[charIndex];
        }

        if (actual.tieneHijos()){
            actual.significado = null;
        } else {
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
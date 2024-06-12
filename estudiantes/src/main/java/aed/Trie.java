package aed;

public class Trie{

    private Nodo_Trie raiz;

    private class Nodo_Trie {

        // Array para los hijos (256 signos del codigo ASCII)
        private Nodo_Trie[] hijos;

        // Significado del nodo
        private T significado;

         // Constructor
         public Nodo_Trie() {
            hijos = new Nodo_Trie[256];
            significado = null;
        }

        // Métodos para obtener y establecer hijos y la marca de fin de palabra

        public Nodo_Trie[] obtenerHijos() {
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
           for (Nodo_Trie hijo : hijos) {
               if (hijo != null) {
                   return true;
               }
           }
           return false;
        }
    }

    // Constructor
    public Trie() {
       raiz = new Nodo_Trie();
    }

    // Método para agregar una palabra
    public void agregar(String palabra, T significado) {
        Nodo_Trie actual = raiz;
        for (char c : palabra.toCharArray()) {
            int index = c; // El índice es el valor ASCII del carácter
            if (actual.obtenerHijos()[index] == null) {
                actual.obtenerHijos()[index] = new Nodo_Trie();
            }
            actual = actual.obtenerHijos()[index];
        } 
        actual.definirSignificado(significado);
    }

    // Método para verificar si una palabra pertenece al trie
    public boolean pertenece(String palabra) {
        Nodo_Trie actual = raiz;
        for (char c : palabra.toCharArray()) {
            int index = c; // El índice es el valor ASCII del carácter
            if (actual.obtenerHijos()[index] == null) {
                return false;
            }
            actual = actual.obtenerHijos()[index];
        }
        return true;
    }

    // Método para borrar una palabra
    public void borrar(String palabra) {
        borrarRecursivo(raiz, palabra, 0);
    }

    public void borrarRecursivo(Nodo_Trie actual, String palabra, int index) {
        if (index == palabra.length()) {
            if (actual.obtenerSignificado() != null) {
                actual.definirSignificado(null);
            }
            return;
        }

        char c = palabra.charAt(index);
        int charIndex = c; // El índice es el valor ASCII del carácter
        Nodo_Trie nodo = actual.obtenerHijos()[charIndex];
        if (nodo == null){
            return; // La palabra no está en el Trie
        }

        borrarRecursivo(nodo, palabra, index + 1);

        // Eliminar el nodo hijo si es redundante y no es el final de una palabra
        if (nodo.obtenerSignificado() == null && !nodo.tieneHijos()) {
            actual.obtenerHijos()[index] = null;
        }
    }

}

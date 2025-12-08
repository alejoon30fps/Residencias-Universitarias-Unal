package Modulos;
import Estructuras.AVLEstudiante;
import Estructuras.Estudiante;
import Estructuras.MinHeap;
import Estructuras.UniversalHashTable;

public class Residencia {

    private AVLEstudiante ordenPrioridad;
    private MinHeap colaPrioridad;
    private UniversalHashTable estudiantesPorID;
    private UniversalHashTable aceptados;
    private int cupos;

    public Residencia(){
        cupos=16; // cargo predeterminado de cupos
        ordenPrioridad= new AVLEstudiante();
        colaPrioridad= new MinHeap<Estudiante>(cupos);
        estudiantesPorID= new UniversalHashTable<Estudiante>();
        aceptados=new UniversalHashTable<Estudiante>();
        
    }

    @SuppressWarnings("unchecked")
    public void registrarEstudiante(String nombre, int pbm, String correo){
        Estudiante nuevoEstudiante=new Estudiante(nombre, pbm, correo);
        ordenPrioridad.insertar(nuevoEstudiante);
        colaPrioridad.insert(nuevoEstudiante);
        estudiantesPorID.insert(nuevoEstudiante.getId(), nuevoEstudiante);
    }

    public String queryEstudianteId(int id){
        return estudiantesPorID.get(id).toString();
    }

    @SuppressWarnings("unchecked")
    public void changeValue(int nuevoPbm,int idEstudiante){
        Estudiante cambioEstudiante= (Estudiante) estudiantesPorID.get(idEstudiante);
        ordenPrioridad.changeKey(cambioEstudiante, nuevoPbm); //dentro de este metodo cambiamos el valor del pbm
        colaPrioridad.changeKey(cambioEstudiante, cambioEstudiante);//aqui simplemente actualizamos el mismo objeto pero con atributos modificados
    }

    @SuppressWarnings("unchecked")
    public void asigCupos(){
        Estudiante cupo;
        while(cupos!=0){
            cupo=(Estudiante) colaPrioridad.extractMin();
            aceptados.insert(cupo.getId(),cupo);
            cupos--;
        }
    }

    public String listaEntera(){
        String[]listaCompleta=ordenPrioridad.listaInOrder(aceptados);
        return "***Estudiantes Admitidos***:\n"+listaCompleta[0]+"\n***Estudiantes no admitidos:\n***"+listaCompleta[1];
    }

    public boolean estadoFinal(int idEstudiante){
        if(aceptados.get(idEstudiante)==null) return false;  //Estos metodos tienen problemas cuando los objtos son ints en lugar de 0 jaja
        return true;
    }

    public boolean eliminarEstudiante(long id) {

        // 1. Buscar estudiante en la HashTable (acceso O(1))
        int key = Long.valueOf(id).hashCode();
        Estudiante estudiante = (Estudiante) estudiantesPorID.get(key);


        if (estudiante == null) {
            System.out.println(" No existe un estudiante con ID " + id);
            return false;
        }

        // 2. Eliminar del Heap
        boolean heapOK = colaPrioridad.remove(estudiante);


        // 3. Eliminar del AVL
        boolean avlOK = ordenPrioridad.remove((Estudiante) estudiante);

        // 4. Eliminar de la tabla hash
        boolean hashOK = estudiantesPorID.remove(id);


        System.out.println("\n--- RESULTADOS ELIMINACIÓN ---");
        System.out.println("AVL:  " + avlOK);
        System.out.println("HEAP: " + heapOK);
        System.out.println("HASH: " + hashOK);

        return heapOK && avlOK && hashOK;
    }
}

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
        ordenPrioridad= new AVLEstudiante();
        colaPrioridad= new MinHeap<Estudiante>();
        estudiantesPorID= new UniversalHashTable<Estudiante>();
        aceptados=new UniversalHashTable<Estudiante>();
        cupos=16; // cargo predeterminado de cupos
    }

    public void registrarEstudiante(String nombre, int pbm, String correo){
        Estudiante nuevoEstudiante=new Estudiante(nombre, pbm, correo);
        ordenPrioridad.insertar(nuevoEstudiante);
        colaPrioridad.insert(nuevoEstudiante);
        estudiantesPorID.insert(nuevoEstudiante.getId(), nuevoEstudiante);
    }

    public String queryEstudianteId(int id){
        return estudiantesPorID.get(id).toString();
    }

    public void changeValue(int nuevoPbm,int idEstudiante){
        Estudiante cambioEstudiante= (Estudiante) estudiantesPorID.get(idEstudiante);
        ordenPrioridad.changeKey(cambioEstudiante, nuevoPbm); //dentro de este metodo cambiamos el valor del pbm
        colaPrioridad.changeKey(cambioEstudiante);//aqui simplemente actualizamos
    }

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

}

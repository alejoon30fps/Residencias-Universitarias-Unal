#  Sistema de Asignación de Residencias Universitarias - UNAL

> **Proyecto final de Estructuras de Datos.** > Sistema optimizado para la gestión y asignación prioritaria de cupos de vivienda universitaria basado en vulnerabilidad socioeconómica (PBM).

---

## 📋 Descripción del Proyecto

Este software soluciona el problema de asignación de cupos limitados mediante un algoritmo de priorización dinámica. Utiliza una arquitectura híbrida de estructuras de datos para garantizar eficiencia, escalabilidad y justicia en el proceso.

### 🛠️ Estructuras de Datos Implementadas
* **Tabla Hash:** Para validación de unicidad y acceso rápido por ID ($O(1)$).
* **Min-Heap:** Para la asignación prioritaria de cupos basada en el menor PBM ($O(\log n)$).
* **Árbol AVL:** Para mantener el ordenamiento total y generar reportes ($O(\log n)$).
* **ArrayList:** Para la presentación final de resultados en la GUI.

---

##  Instrucciones de Instalación y Ejecución

Para ejecutar el proyecto correctamente y realizar las pruebas de rendimiento, es **indispensable** seguir este orden de pasos para la generación de datos de prueba.

### 1. Clonar el repositorio
```bash
git clone [https://github.com/alejoon30fps/Residencias-Universitarias-Unal.git](https://github.com/alejoon30fps/Residencias-Universitarias-Unal.git)
##  Instrucciones de Ejecución

### 2. Iniciar la Aplicación (Modo Normal)
Para usar el sistema simplemente:
1.  Navega al paquete `gui`.
2.  Ejecuta la clase `Main`.
3.  La interfaz gráfica se abrirá lista para usar.

---

### 3. Ejecutar Pruebas de Rendimiento (Benchmarks)
 **Requisito Previo:** Para poder analizar los tiempos de ejecución con grandes volúmenes de datos, el sistema necesita encontrar archivos específicos en la ruta raíz.

**Pasos obligatorios:**

A.  **Inicializar Generador:** Primero debes ejecutar el archivo/clase `MockupGenerator`.
B.  **Verificar Archivos:** Esta acción creará automáticamente los siguientes 4 archivos CSV en la carpeta raíz del proyecto:
    * `mockup_100.csv`
    * `mockup_1000.csv`
    * `mockup_10000.csv`
    * `mockup_100000.csv`
C.  **Ejecutar Pruebas:** Es **necesario** que estos archivos estén en la raíz para que la ruta del método pueda encontrarlos. Una vez generados, ya podrás ejecutar el benchmark de manera correcta y analizar los tiempos de prueba para cada cantidad $N$ de datos.

    * `mockup_10000.csv`
    * `mockup_100000.csv`
3.  **Ejecutar Pruebas:** Es **necesario** que estos archivos estén en la raíz para que la ruta del método pueda encontrarlos. Una vez generados, ya podrás ejecutar el benchmark de manera correcta y analizar los tiempos de prueba para cada cantidad $N$ de datos.

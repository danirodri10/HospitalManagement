package org.example;

import org.example.entitys.Doctor;
import org.example.entitys.Paciente;
import org.example.repositorios.RepoCita;
import org.example.repositorios.RepoDoctor;
import org.example.repositorios.RepoHospital;
import org.example.repositorios.RepoPaciente;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        boolean condicion = true;
        int entrada;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();

        RepoDoctor repoDoctor = new RepoDoctor(session);
        RepoPaciente repoPaciente = new RepoPaciente(session);
        RepoCita repoCita = new RepoCita(session);
        RepoHospital repoHospital = new RepoHospital(session);

        do {
            try {
                System.out.println("Selecciona una opción del 1 al 13:\n" + "1. Crear un nuevo doctor\n" + "2. Eliminar un doctor\n" + "3. Modificar datos de un doctor\n" + "4. Crear un nuevo paciente\n" + "5. Eliminar un paciente por nombre\n" + "6. Modificar datos de un paciente\n" + "7. Asignar doctor a paciente para una cita\n" + "8. Indicar fecha de fin del tratamiento de un paciente\n" + "9. Cambiar el hospital de un tratamiento\n" + "10. Mostrar los datos de un paciente\n" + "11. Mostrar datos de tratamientos y hospital\n" + "12. Mostrar cantidad de tratamientos de cada hospital\n" + "13. Salir");

                //leemos la entrada del usuario por teclado
                entrada = teclado.nextInt();
                teclado.nextLine();

                switch (entrada) {

                    case 1://creación de un doctor
                        System.out.println("introduce el nombre del doctor:");
                        String nombre = teclado.nextLine();
                        System.out.println("Introduce la especialidad del doctor: ");
                        String especialidad = teclado.nextLine();
                        System.out.println("Introduce el número de teléfono del doctor: ");
                        String telefono = teclado.nextLine();

                        Doctor doctor = new Doctor(nombre, especialidad, telefono);
                        repoDoctor.crearDoctor(doctor);
                        break;

                    case 2://eliminar un doctor
                        System.out.println("Introduce el id del doctor que deseas eliminar: ");
                        int id = teclado.nextInt();
                        teclado.nextLine();

                        repoDoctor.eliminarDoctor(id);
                        break;

                    case 3://modificar un doctor
                        System.out.println("Por favor, introduzca el id del doctor que desea modificar");
                        id = teclado.nextInt();
                        teclado.nextLine();
                        System.out.println("introduce el nuevo nombre del doctor");
                        nombre = teclado.nextLine();
                        System.out.println("Introduce la nueva especialidad del doctor: ");
                        especialidad = teclado.nextLine();
                        System.out.println("Introduce el nuevo número de teléfono del doctor: ");
                        telefono = teclado.nextLine();

                        repoDoctor.modificarDoctor(id, nombre, especialidad, telefono);
                        break;

                    case 4://crear un paciente
                        System.out.println("Introduce el nombre del nuevo paciente: ");
                        nombre = teclado.nextLine();
                        System.out.println("Introduce la fecha de nacimiento del paciente: ");//introducir año/mes/día
                        String fecha = teclado.nextLine();
                        LocalDate fechaNacimiento = LocalDate.parse(fecha);
                        System.out.println("Introduce la dirección del paciente: ");
                        String direccion = teclado.nextLine();

                        Paciente paciente = new Paciente(nombre, fechaNacimiento, direccion);
                        repoPaciente.crearPaciente(paciente);
                        break;

                    case 5://eliminar un paciente
                        System.out.println("Introduce el nombre del paciente a eliminar");
                        nombre = teclado.nextLine();
                        repoPaciente.borrarPaciente(nombre);
                        break;

                    case 6://modificar un paciente
                        System.out.println("Introduce el id del paciente a modificar");
                        id = teclado.nextInt();
                        teclado.nextLine();
                        System.out.println("Introduce el nuevo nombre del paciente");
                        nombre = teclado.nextLine();
                        System.out.println("Introduce la nueva fecha del paciente");//introducir año/mes/día
                        fecha = teclado.nextLine();
                        fechaNacimiento = LocalDate.parse(fecha);
                        System.out.println("Introduce la dirección del paciente");
                        direccion = teclado.nextLine();

                        repoPaciente.modificarPaciente(id, nombre, fechaNacimiento, direccion);
                        break;

                    case 7://asignar un doctor a un paciente
                        System.out.println("Introduce la fecha de la cita");
                        fecha = teclado.nextLine();
                        LocalDate fechaCita = LocalDate.parse(fecha);
                        System.out.println("Introduce el estado de la cita");
                        String estado = teclado.nextLine();
                        System.out.println("Introduce el nombre del doctor");
                        String nombreDoctor = teclado.nextLine();
                        System.out.println("Introduce el nombre del paciente");
                        String nombrePaciente = teclado.nextLine();

                        //recuperamos el Doctor y Paciente con dichos nombres
                        Doctor doctor1 = repoDoctor.recuperarDoctorPorNombre(nombreDoctor);
                        Paciente paciente1 = repoPaciente.recuperarPacientePorNombre(nombrePaciente);

                        //llamamos al método pasándole los atributos y objetos recogidos para la asignación
                        repoCita.asignarDoctorPaciente(fechaCita, estado, doctor1, paciente1);
                        break;

                    case 8: //indicar la fecha de fin del tratamiento de un paciente
                        System.out.println("Introduce el nombre del paciente: ");
                        nombrePaciente = teclado.nextLine();
                        System.out.println("Introduce la fecha de inicio del tratamiento: ");//introducir año/mes/día
                        fecha = teclado.nextLine();
                        LocalDate fechaInicio = LocalDate.parse(fecha);
                        System.out.println("Introduce el tipo de tratamiento: ");
                        String tipo = teclado.nextLine();
                        System.out.println("Introduce la fecha de fin del tratamiento: ");//introducir año/mes/día
                        fecha = teclado.nextLine();
                        LocalDate fechaFin = LocalDate.parse(fecha);
                        repoPaciente.fechaFinTratamientoPaciente(nombrePaciente, fechaInicio, tipo, fechaFin);
                        break;

                    case 9://cambiar el hospital de un tratamiento
                        System.out.println("Introduce el id del tratamiento: ");
                        id = teclado.nextInt();
                        teclado.nextLine();
                        System.out.println("Introduce el nombre del hospital actual");
                        String hospitalActual = teclado.nextLine();
                        System.out.println("Introduce el nombre del hospital nuevo");
                        String hospitalNuevo = teclado.nextLine();
                        repoHospital.cambiarTratamientoHospital(id, hospitalActual, hospitalNuevo);
                        break;

                    case 10://mostrar los datos de un paciente
                        System.out.println("Introduce el nombre del paciente: ");
                        nombrePaciente = teclado.nextLine();
                        repoPaciente.mostrarDatosPaciente(nombrePaciente);
                        break;

                    case 11://mostrar datos tratamientos y hospital
                        System.out.println("Introduce el nombre del hospital: ");
                        nombre = teclado.nextLine();
                        repoHospital.mostrarDatosHospital(nombre);
                        break;

                    case 12://mostrar el número total de tratamientos de cada hospital
                        repoHospital.mostrarNumeroTratamientosHospital();
                        break;

                    case 13:
                        System.out.println("Adiós!");
                        condicion = false;
                }
            } catch (Exception e) {
                teclado.nextLine();
                System.out.println("Entrada incorrecta");

            }
        } while (condicion);


        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }
}

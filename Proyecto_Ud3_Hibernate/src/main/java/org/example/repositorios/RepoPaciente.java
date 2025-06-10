package org.example.repositorios;

import org.example.entitys.Cita;
import org.example.entitys.Paciente;
import org.example.entitys.Recibe;
import org.example.entitys.Tratamiento;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class RepoPaciente {
    private final Session session;

    public RepoPaciente(Session session) {
        this.session = session;
    }

    //método para crear un paciente
    public void crearPaciente(Paciente paciente) {
        Transaction trx = session.beginTransaction();


        try {
            //obtenemos el último id de la BD
            Paciente pacienteObtenido = (Paciente) session.createQuery("From Paciente p order by p.id desc limit 1").getSingleResult();

            //le asignamos al nuevo paciente el id obtenido + 1
            paciente.setId(pacienteObtenido.getId() + 1);

            //guardamos el paciente
            session.persist(paciente);
            System.out.println("Paciente creado correctamente");
        } catch (Exception e) {
            System.out.println("No se pudo crear el paciente" + e);
        }

        trx.commit();
    }

    //método para eliminar un paciente por nombre
    public void borrarPaciente(String nombre) {
        Transaction trx = session.beginTransaction();

        try {
            //obtenemos el paciente que queremos borrar a través del nombre
            Paciente paciente = (Paciente) session.createQuery("From Paciente p where p.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            //eliminamos el paciente
            session.remove(paciente);
            System.out.println("paciente eliminado correctamente");
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el paciente" + e);
        }

        trx.commit();
    }

    //método para modificar los datos de un paciente
    public void modificarPaciente(int id, String nombre, LocalDate fecha, String direccion) {
        Transaction trx = session.beginTransaction();

        try {
            //obtenemos el paciente a modificar a través del id
            Paciente pacienteObtenido = (Paciente) session.createQuery("From Paciente p where p.id = " + id).getSingleResult();

            //modificamos los datos del paciente
            pacienteObtenido.setNombre(nombre);
            pacienteObtenido.setFecha_nacimiento(fecha);
            pacienteObtenido.setDireccion(direccion);

            session.merge(pacienteObtenido);
            System.out.println("Paciente modificado correctamente");
        } catch (Exception e) {
            System.out.println("No se pudo modificar el paciente");
        }

        trx.commit();
    }

    //método para asignar obtener un Paciente por su nombre
    public Paciente recuperarPacientePorNombre(String nombre) {
        Transaction trx = session.beginTransaction();
        Paciente paciente = null;

        try {
            //obtenemos el paciente por su nombre
            paciente = (Paciente) session.createQuery("From Paciente p where p.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("No se ha podido recuperar ningún paciente con ese nombre. ERROR: " + e);
        }

        trx.commit();
        return paciente;
    }

    //método para mostrar los datos de un paciente
    public void mostrarDatosPaciente(String nombre) {
        Transaction trx = session.beginTransaction();

        try {
            //recuperamos el paciente con dicho nombre
            Paciente paciente = (Paciente) session.createQuery("From Paciente p where p.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            //Mostramos los datos básicos del paciente
            System.out.println("Id paciente: " + paciente.getId());
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Fecha Naciemineto: " + paciente.getFecha_nacimiento());
            System.out.println("Dirección: " + paciente.getDireccion());

            //recuperamos todas las citas asociadas al paciente
            List<Cita> listaCitas = (List<Cita>) session.createQuery("From Cita c where c.paciente.id = :idPaciente")//preguntar a Jose por el parámetro Cita.class
                    .setParameter("idPaciente", paciente.getId())
                    .getResultList();

            //mostramos las citas
            System.out.println("Citas del paciente: ");
            for (Cita cita : listaCitas) {
                System.out.println("Id cita: " + cita.getId());
                System.out.println("Fecha: " + cita.getFecha());
                System.out.println("Estado: " + cita.getEstado());
            }

            // Recuperamos los tratamientos que recibe el paciente a través de la relación Recibe
            List<Recibe> listaRecibe = (List<Recibe>) session.createQuery("From Recibe r where r.paciente.id = :idPaciente")
                    .setParameter("idPaciente", paciente.getId())
                    .getResultList();

            //mostramos los tratamientos del paciente
            System.out.println("Tratamientos del paciente: ");
            for (Recibe recibe : listaRecibe) {
                //obtenemos el tratamiento del idPaciente en cuestión de recibe
                Tratamiento tratamiento = recibe.getTratamiento();
                System.out.println("Tipo: " + tratamiento.getTipo());
            }

        } catch (Exception e) {
            System.out.println("No se puedieron mostrar los datos del paciente. ERROR: " + e);
        }

        trx.commit();
    }

    //método para indicar la fecha de fin de tratamiento de un paciente
    public void fechaFinTratamientoPaciente(String nombrePaciente, LocalDate fechaInicio, String tipoTratamiento, LocalDate fechaFin) {
        Transaction trx = session.beginTransaction();

        try {
            //obtenemos el paciente con el nombre en cuestión
            Paciente paciente = (Paciente) session.createQuery("From Paciente p where p.nombre = :nombre")
                    .setParameter("nombre", nombrePaciente)
                    .getSingleResult();

            //obtenemos el tratamiento con el tipo en cuestión
            Tratamiento tratamiento = (Tratamiento) session.createQuery("From Tratamiento t where t.tipo = :tipo")
                    .setParameter("tipo", tipoTratamiento)
                    .getSingleResult();

            //obtenemos el objeto Recibe pasándole los campos de lso campos obtenidos
            Recibe recibe = (Recibe) session.createQuery("From Recibe r where r.id.tratamientoId = :tratamientoId and r.id.pacienteId = :pacienteId and r.id.fechaInicio = :fechaInicio")
                    .setParameter("tratamientoId", tratamiento.getId())
                    .setParameter("pacienteId", paciente.getId())
                    .setParameter("fechaInicio", fechaInicio)
                    .getSingleResult();

            //modificamos la fecha de fin del Recibe en cuestión
            recibe.setFecha_fin(fechaFin);
            System.out.println("Fecha de fin modificada correctamente");
        } catch (Exception e) {
            System.out.println("No se pudo modificar la fecha de fin");
        }

        trx.commit();
    }
}

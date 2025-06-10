package org.example.repositorios;

import org.example.entitys.Doctor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RepoDoctor {
    private Session session;

    public RepoDoctor(Session session) {
        this.session = session;
    }

    //método para crear un nuevo doctor
    public void crearDoctor(Doctor doctor) {
        Transaction trx = session.beginTransaction();

        try {
            //recuperamos el doctor con el último id de la BD
            Doctor doctorObtenido = (Doctor) session.createQuery("From Doctor d order by d.id desc limit 1").getSingleResult();

            //le asignamos al nuevo doctor el último id de la BD + 1
            doctor.setId(doctorObtenido.getId() + 1);

            session.persist(doctor);
            System.out.println("Doctor añadido correctamente");
        } catch (Exception e) {
            System.out.println("No se pudo realizar la creación del Doctor");
        }

        trx.commit();
    }

    //método para eliminar un doctor por su id
    public void eliminarDoctor(int id) {
        Transaction trx = session.beginTransaction();

        try {
            //obtenemos el doctor con el id deseado
            Doctor doctor = (Doctor) session.createQuery("From Doctor d where d.id = " + id).getSingleResult();

            //eliminamos el doctor en cuestión
            session.remove(doctor);
            System.out.println("Doctor eliminado correctamente");
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el doctor en cuestión");
        }

        trx.commit();
    }

    //método para modificar los datos de un Doctor
    public void modificarDoctor(int id, String nombre, String especialidad, String telefono) {
        Transaction trx = session.beginTransaction();

        try {
            //recuperamos el doctor del id que queremos modificar
            Doctor doctor = (Doctor) session.createQuery("From Doctor d where d.id = " + id).getSingleResult();

            //modificamos los datos de dicho doctor con los nuevos datos
            doctor.setNombre(nombre);
            doctor.setEspecialidad(especialidad);
            doctor.setTelefono(telefono);

            //guardamos el doctor con los datos modificados
            session.merge(doctor);
            System.out.println("Doctor modificado correctamente");
        } catch (Exception e) {
            System.out.println("No se pudo modificar el doctor");
        }

        trx.commit();
    }

    //método para obtener un Doctor por su nombre
    public Doctor recuperarDoctorPorNombre(String nombre) {
        Transaction trx = session.beginTransaction();
        Doctor doctor = null;

        try {
            //recuperamos el doctor por su nombre
            doctor = (Doctor) session.createQuery("From Doctor d where d.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("No se ha podido recuperar ningún doctor con ese nombre. ERROR: " + e);
        }

        trx.commit();
        return doctor;
    }
}

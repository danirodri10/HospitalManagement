package org.example.repositorios;

import org.example.entitys.Cita;
import org.example.entitys.Doctor;
import org.example.entitys.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;

public class RepoCita {
    private Session session;

    public RepoCita(Session session) {
        this.session = session;
    }

    //método para asignar un Doctor a un Paciente a través de sus nombres
    public void asignarDoctorPaciente(LocalDate fecha, String estado, Doctor doctor, Paciente paciente) {
        Transaction trx = session.beginTransaction();

        try {
            //creamos la citaNueva mediante la cual realizaremos la asignación
            Cita citaNueva = new Cita(fecha, estado);


            try {
                //buscamos si existe una cita con el doctor que queremos añadir
                Cita citaExistente = (Cita) session.createQuery("From Cita c where c.doctor.id = :idNuevoDoctor")
                        .setParameter("idNuevoDoctor", doctor.getId())
                        .getSingleResult();

                //si existe, lo eliminamos
                session.remove(citaExistente);
                session.flush();//sincroniza el estado de la sesión con la bd y realiza las operaciones pendientes
            } catch (Exception e) {
                System.out.println("No existe ninguna cita existente para ese doctor.");
            }

            citaNueva.setDoctorBidireccional(doctor);
            paciente.addCita(citaNueva);

            //guardamos el objeto citaNueva que establece la asignación
            session.persist(citaNueva);
            System.out.println("Asignación realizada correctamente y cita nueva guardada con éxito!");
        } catch (Exception e) {
            System.out.println("No se pudo realizar la asignación " + e);
        }

        trx.commit();
    }
}

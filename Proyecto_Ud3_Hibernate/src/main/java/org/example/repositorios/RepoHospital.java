package org.example.repositorios;

import org.example.entitys.Hospital;
import org.example.entitys.Tratamiento;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RepoHospital {
    private Session session;

    public RepoHospital(Session session) {
        this.session = session;
    }

    //método para mostrar los datos de un hospital y sus tratamientos
    public void mostrarDatosHospital(String nombre) {
        Transaction trx = session.beginTransaction();

        try {
            //obtenemos el hospital con el nombre en cuestión
            Hospital hospital = (Hospital) session.createQuery("From Hospital h where h.nombre = :nombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            //mostramos los datos del hospital
            System.out.println("Datos del hospital: ");
            System.out.println("Id hospital: " + hospital.getId());
            System.out.println("Nombre: " + hospital.getNombre());
            System.out.println("Ubicación: " + hospital.getUbicacion());

            //obtenemos los tratamientos de ese hospital
            List<Tratamiento> listaTratamientos = (List<Tratamiento>) session.createQuery("From Tratamiento t where t.hospital.id = :id")
                    .setParameter("id", hospital.getId())
                    .getResultList();

            //mostramos los datos de los tratamientos
            System.out.println("Datos de los tratamientos: ");
            for (Tratamiento tratamiento : listaTratamientos) {
                System.out.println("Id tratamiento: " + tratamiento.getId());
                System.out.println("Tipo tratamiento: " + tratamiento.getTipo());
                System.out.println("Costo: " + tratamiento.getCosto());
            }
        } catch (Exception e) {
            System.out.println("No se puedieron obtener los datos");
        }

        trx.commit();
    }

    //método para mostrar la cantidad de tratamientos que tiene cada hospital
    //no utilizo el nombre de hospital que figura como parámetro en el enunciado porque no al listarlos todos no es necesario
    public void mostrarNumeroTratamientosHospital() {
        Transaction trx = session.beginTransaction();

        try {
            //obtenemos el hospital con el nombre en cuestión
            List<Hospital> listaHospital = (List<Hospital>) session.createQuery("From Hospital h")
                    .getResultList();

            //iteramos la lista de hospitales para obtener los tratamientos de cada uno
            for (Hospital hospital : listaHospital) {
                //obtenemos los tratamientos de ese hospital
                List<Tratamiento> listaTratamientos = (List<Tratamiento>) session.createQuery("From Tratamiento t where t.hospital.id = :id")
                        .setParameter("id", hospital.getId())
                        .getResultList();

                //mostramos la cantidad de tratamientos de dicho hospital
                System.out.println(hospital.getNombre() + " tiene " + listaTratamientos.size() + " tratamientos\n");
            }
        } catch (Exception e) {
            System.out.println("No se puedieron obtener los datos");
        }

        trx.commit();
    }

    //método para cambiar un tratamiento de hospital
    public void cambiarTratamientoHospital(int idTratamiento, String nombreHospitalAntiguo, String nombreHospitalNuevo) {
        Transaction trx = session.beginTransaction();

        try {
            //obtenemos el tratamiento con dicho id y nombre del hospital actual
            Tratamiento tratamiento = (Tratamiento) session.createQuery("From Tratamiento t where t.id = :id and t.hospital.nombre = :nombre")
                    .setParameter("id", idTratamiento)
                    .setParameter("nombre", nombreHospitalAntiguo)
                    .getSingleResult();

            //obtenemos el hospital nuevo al que queremos cambiar el tratamiento
            Hospital hospitalNuevo = (Hospital) session.createQuery("From Hospital h where h.nombre = :nombre")
                    .setParameter("nombre", nombreHospitalNuevo)
                    .getSingleResult();

            //le asignamos el nuevo hospital al tratamiento
            tratamiento.setHospital(hospitalNuevo);
            System.out.println("Tratamiento cambiado de hospital correctamente!");
        } catch (Exception e) {
            System.out.println("No se pudo cambiar de hospital el tratamiento " + e);
        }

        trx.commit();
    }
}

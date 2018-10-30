/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.ubits.rewow.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HMONTENEGRO
 */
@XmlRootElement
public class MedicalRecord implements Serializable {

    private Client client;
    private Vaccinations Vaccination;
    private Pet pet;

    public MedicalRecord() {
    }
    
    

    public MedicalRecord(Client client, Vaccinations Vaccination, Pet pet) {
        this.client = client;
        this.Vaccination = Vaccination;
        this.pet = pet;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" + "client=" + client + ", Vaccination=" + Vaccination + ", pet=" + pet + '}';
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vaccinations getVaccination() {
        return Vaccination;
    }

    public void setVaccination(Vaccinations Vaccination) {
        this.Vaccination = Vaccination;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

}

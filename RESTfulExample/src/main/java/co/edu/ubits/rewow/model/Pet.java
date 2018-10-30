/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.ubits.rewow.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HMONTENEGRO
 */
@Entity
@Table(name = "Pet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pet.findAll", query = "SELECT p FROM Pet p"),
    @NamedQuery(name = "Pet.findByIdPet", query = "SELECT p FROM Pet p WHERE p.idPet = :idPet"),
    @NamedQuery(name = "Pet.findByName", query = "SELECT p FROM Pet p WHERE p.name = :name"),
    @NamedQuery(name = "Pet.findByType", query = "SELECT p FROM Pet p WHERE p.type = :type"),
    @NamedQuery(name = "Pet.findBySize", query = "SELECT p FROM Pet p WHERE p.size = :size"),
    @NamedQuery(name = "Pet.findByDescription", query = "SELECT p FROM Pet p WHERE p.description = :description")})
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPet")
    private Integer idPet;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Type")
    private String type;
    @Basic(optional = false)
    @Column(name = "Size")
    private String size;
    @Basic(optional = false)
    @Column(name = "Description")
    private String description;

    public Pet() {
    }

    public Pet(Integer idPet) {
        this.idPet = idPet;
    }

    public Pet(Integer idPet, String name, String type, String size, String description) {
        this.idPet = idPet;
        this.name = name;
        this.type = type;
        this.size = size;
        this.description = description;
    }

    public Integer getIdPet() {
        return idPet;
    }

    public void setIdPet(Integer idPet) {
        this.idPet = idPet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPet != null ? idPet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pet)) {
            return false;
        }
        Pet other = (Pet) object;
        if ((this.idPet == null && other.idPet != null) || (this.idPet != null && !this.idPet.equals(other.idPet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.ubits.rewow.database.Pet[ idPet=" + idPet + " ]";
    }
    
}

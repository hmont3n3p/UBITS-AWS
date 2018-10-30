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

@Entity
@Table(name = "Vaccinations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vaccinations.findAll", query = "SELECT v FROM Vaccinations v"),
    @NamedQuery(name = "Vaccinations.findByIdVaccinations", query = "SELECT v FROM Vaccinations v WHERE v.idVaccinations = :idVaccinations"),
    @NamedQuery(name = "Vaccinations.findByNumber", query = "SELECT v FROM Vaccinations v WHERE v.number = :number"),
    @NamedQuery(name = "Vaccinations.findByType", query = "SELECT v FROM Vaccinations v WHERE v.type = :type"),
    @NamedQuery(name = "Vaccinations.findByDate", query = "SELECT v FROM Vaccinations v WHERE v.date = :date")})
public class Vaccinations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVaccinations")
    private Integer idVaccinations;
    @Basic(optional = false)
    @Column(name = "Number")
    private String number;
    @Basic(optional = false)
    @Column(name = "Type")
    private String type;
    @Basic(optional = false)
    @Column(name = "Date")
    private String date;

    public Vaccinations() {
    }

    public Vaccinations(Integer idVaccinations) {
        this.idVaccinations = idVaccinations;
    }

    public Vaccinations(Integer idVaccinations, String number, String type, String date) {
        this.idVaccinations = idVaccinations;
        this.number = number;
        this.type = type;
        this.date = date;
    }

    public Integer getIdVaccinations() {
        return idVaccinations;
    }

    public void setIdVaccinations(Integer idVaccinations) {
        this.idVaccinations = idVaccinations;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVaccinations != null ? idVaccinations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Vaccinations)) {
            return false;
        }
        Vaccinations other = (Vaccinations) object;
        if ((this.idVaccinations == null && other.idVaccinations != null) || (this.idVaccinations != null && !this.idVaccinations.equals(other.idVaccinations))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.ubits.rewow.database.Vaccinations[ idVaccinations=" + idVaccinations + " ]";
    }

}

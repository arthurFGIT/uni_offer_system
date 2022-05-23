package de.hsrm.mi.web.projekt.benutzerprofil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.validierung.Bunt;

@Entity
public class BenutzerProfil{

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Size(min=3,max=60) 
    @NotNull
    private String name = "";

    @DateTimeFormat(iso = ISO.DATE)
    @PastOrPresent
    @NotNull
    private LocalDate geburtsdatum =  LocalDate.of(1,1,1);

    @NotNull
    private String adresse;

    @Email
    private String email = null;

    @NotNull
    @Bunt(message="{bunt.fehler}")
    private String lieblingsfarbe = "";

    @NotBlank
    private String interessen = "";

    private double lat, lon;

    
    @OneToMany(cascade=CascadeType.PERSIST, orphanRemoval=true, fetch = FetchType.EAGER)
    private List<Angebot> angebote = new ArrayList<>();
    
    public List<Angebot> getAngebote() {
        return angebote;
    }

    public void setAngebote(List<Angebot> angebote) {
        this.angebote = angebote;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }
    
    @Override
    public String toString() {
        return "BenutzerProfil [id=" + id + "adresse=" + adresse + ", email=" + email + ", geburtsdatum=" + geburtsdatum
                + ", interessen=" + interessen + ", lieblingsfarbe=" + lieblingsfarbe + ", name=" + name + "version=" + version + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geburtsdatum == null) ? 0 : geburtsdatum.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BenutzerProfil other = (BenutzerProfil) obj;
        if (geburtsdatum == null) {
            if (other.geburtsdatum != null)
                return false;
        } else if (!geburtsdatum.equals(other.geburtsdatum))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }
    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLieblingsfarbe() {
        return lieblingsfarbe;
    }
    public void setLieblingsfarbe(String lieblingsfarbe) {
        this.lieblingsfarbe = lieblingsfarbe;
    }
    public String getInteressen() {
        return interessen;
    }
    public void setInteressen(String interessen) {
        this.interessen = interessen;
    }

    public List<String> getInteressenListe(){
        if(interessen.isEmpty()){
            return Collections.emptyList();
        }
        List<String> interessenListe = new ArrayList<String>();
        for(String x : interessen.split(",")){
            interessenListe.add(x.trim());
        }   
        return interessenListe;
    }

}
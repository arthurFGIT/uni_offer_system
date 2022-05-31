package de.hsrm.mi.web.projekt.gebot;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;

@Entity
public class Gebot {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @ManyToOne
    private BenutzerProfil gebieter;

    @ManyToOne
    private Angebot angebot;

    private long betrag = 0;
    
    @DateTimeFormat(iso=ISO.DATE_TIME)
    private LocalDateTime gebotzeitpunkt = LocalDateTime.now();



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gebot other = (Gebot) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public BenutzerProfil getGebieter() {
        return gebieter;
    }

    public void setGebieter(BenutzerProfil gebieter) {
        this.gebieter = gebieter;
    }

    public Angebot getAngebot() {
        return angebot;
    }

    public void setAngebot(Angebot angebot) {
        this.angebot = angebot;
    }

    public long getBetrag() {
        return betrag;
    }

    public void setBetrag(long betrag) {
        this.betrag = betrag;
    }

    public LocalDateTime getGebotzeitpunkt() {
        return gebotzeitpunkt;
    }

    public void setGebotzeitpunkt(LocalDateTime gebotzeitpunkt) {
        this.gebotzeitpunkt = gebotzeitpunkt;
    }

    public static void add(Gebot gebot) {
    }

    
}

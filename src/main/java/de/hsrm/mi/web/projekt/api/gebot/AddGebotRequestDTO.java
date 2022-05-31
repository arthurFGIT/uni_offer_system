package de.hsrm.mi.web.projekt.api.gebot;


import de.hsrm.mi.web.projekt.gebot.Gebot;

public record AddGebotRequestDTO(
Long benutzerprofilid,
Long angebotid,
Long betrag)
{
    public static AddGebotRequestDTO from(Gebot g) {
        return new AddGebotRequestDTO(
            g.getGebieter().getId(),
            g.getAngebot().getId(),
            g.getBetrag()
        );
    }
}


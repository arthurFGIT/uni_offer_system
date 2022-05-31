package de.hsrm.mi.web.projekt.api.benutzer;



import java.time.LocalDateTime;

import de.hsrm.mi.web.projekt.angebot.Angebot;

public record GetAngebotResponseDTO(
    long angebotid, // ID des Angebots
    String beschreibung, // Beschreibung
    long anbieterid, // ID des Anbieters
    String anbietername, // Name des Anbieters
    long mindestpreis, // geforderter Mindestpreis
    LocalDateTime ablaufzeitpunkt, // Ende der Versteigerung
    String abholort, // Abholort der Sache
    double lat, // mit Breite
    double lon, // und Laenge
    long topgebot, // bisher max. Gebot fuer dieses Angebot (Default 0)
    long gebote // Anzahl bisheriger Gebote
)
{
    public static GetAngebotResponseDTO from(Angebot a) {
        return new GetAngebotResponseDTO(
            a.getId(),
            a.getBeschreibung(),
            a.getAnbieter().getId(),
            a.getAnbieter().getName(),
            a.getMindestpreis(),
            a.getAblaufzeitpunkt(),
            a.getAbholort(),
            a.getLat(),
            a.getLon(),
            a.gethoechstGebot(),
            a.getGebote().size()
        );
    }
}

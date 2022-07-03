package de.hsrm.mi.web.projekt.projektuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilRepository;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilServiceImpl;

@Service
public class ProjektUserServiceImpl implements ProjektUserService {

    @Autowired PasswordEncoder pwenc;

    @Autowired ProjektUserRepository proUserRepository;
    @Autowired BenutzerprofilServiceImpl bServiceImpl;
    @Autowired BenutzerprofilRepository bRepository;

    @Override
    @Transactional
    public ProjektUser neuenBenutzerAnlegen(String username, String klartextpasswort, String rolle) {
        if(proUserRepository.findById(username).isPresent()){
            throw new ProjektUserServiceException("Projekt User ist bereits vorhanden.");
        }
        else if(rolle.equals("") || rolle == null){
            rolle = "USER";
        }        
        ProjektUser pUser = new ProjektUser(username, pwenc.encode(klartextpasswort), rolle);
        BenutzerProfil benutzer = new BenutzerProfil();
        benutzer.setName(username);
        benutzer = bRepository.save(benutzer);
        pUser = proUserRepository.save(pUser);

        benutzer.setProjektUser(pUser);
        pUser.setBenutzerProfil(benutzer);

        benutzer = bRepository.save(benutzer);
        pUser = proUserRepository.save(pUser);        
                
        return pUser;        
    }

    @Override
    public ProjektUser findeBenutzer(String username) {
        if(!proUserRepository.findById(username).isPresent()){
            throw new ProjektUserServiceException("Konnte projektuser nicht finden.");
        }
        return proUserRepository.findById(username).get();

    }


    
}

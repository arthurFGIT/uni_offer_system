package de.hsrm.mi.web.projekt.benutzerprofil;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BenutzerprofilServiceImpl implements BenutzerprofilService {
        
    private BenutzerprofilRepository bRepository;
    Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);
    
    @Autowired 
    public BenutzerprofilServiceImpl(BenutzerprofilRepository bRepository) {
        this.bRepository = bRepository;
    }

    @Transactional
    @Override
    public BenutzerProfil speichereBenutzerProfil(BenutzerProfil bp) {
        logger.info("User will be saved.");
        return bRepository.save(bp);
    }

    @Override
    public Optional<BenutzerProfil> holeBenutzerProfilMitId(Long id) {
        logger.info("Get user with id.");
        return bRepository.findById(id);
    }

    @Override
    public List<BenutzerProfil> alleBenutzerProfile() {
        logger.info("User will be deleted.");
        return bRepository.findAll(Sort.by("name"));
    }

    @Override
    public void loescheBenutzerProfilMitId(Long loesch) {
        logger.warn("User will be deleted.");
        bRepository.deleteById(loesch);       
    }
    
}

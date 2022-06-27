package de.hsrm.mi.web.projekt.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class BackendInfoService {

    @Autowired SimpMessagingTemplate message;

    public void sendInfo(String topicname, BackendOperation operation, long id){

        BackendInfoMessage bim = new BackendInfoMessage(topicname, operation, id);

        message.convertAndSend("/topic/" + topicname, bim);


    }
    
}

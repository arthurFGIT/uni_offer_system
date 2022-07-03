package de.hsrm.mi.web.projekt.projektuser;

public class ProjektUserServiceException extends RuntimeException{


    public ProjektUserServiceException(String msg){
        super(String.format(msg));
    }
    
    public ProjektUserServiceException(){
    }
}

package co.com.linktic.model.exceptions;

import co.com.linktic.model.enums.TechnicalMessage;

public class ServiceException extends RuntimeException{
    public ServiceException(TechnicalMessage technicalMessage){
        super(technicalMessage.getMessage());
    }
}

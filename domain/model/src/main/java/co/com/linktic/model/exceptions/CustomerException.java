package co.com.linktic.model.exceptions;

import co.com.linktic.model.enums.TechnicalMessage;

public class CustomerException extends RuntimeException{
    public CustomerException(TechnicalMessage technicalMessage){
        super(technicalMessage.getMessage());
    }
}

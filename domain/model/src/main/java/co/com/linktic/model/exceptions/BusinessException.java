package co.com.linktic.model.exceptions;

import co.com.linktic.model.enums.TechnicalMessage;

public class BusinessException extends RuntimeException{

    public BusinessException(TechnicalMessage technicalMessage, String message){
        super(technicalMessage.getMessage() + message);
    }

    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }

}

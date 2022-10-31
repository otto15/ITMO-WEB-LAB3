package com.otto.lab3.service.validator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@ApplicationScoped
@FacesValidator("yValueValidator")
public class YValueValidator implements Validator {

    private static final String INTERVAL_ERROR_MSG = "Координата Y задается числом в промежутке (-5..5)!";

    private static final double LEFT_BOUND = -5;
    private static final double RIGHT_BOUND = 5;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        double yValue = (double) o;
        if (yValue < LEFT_BOUND || yValue > RIGHT_BOUND) {
            processError(INTERVAL_ERROR_MSG);
        }
    }

    private void processError(String errorMessage) {
        FacesMessage msg = new FacesMessage(errorMessage);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);

        throw new ValidatorException(msg);
    }
}

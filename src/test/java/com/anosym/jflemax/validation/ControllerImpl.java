package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.OnRequests;
import java.util.logging.Logger;
import javax.inject.Named;

/**
 *
 * @author marembo
 */
@OnRequests
@Named
class ControllerImpl extends AbstractControllerTest {

    private static final Logger LOG = Logger.getLogger(ControllerImpl.class.getName());

    @Override
    public void onRequest() {
        LOG.info("ControllerImpl called");
    }

}

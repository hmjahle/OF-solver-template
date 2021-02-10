package com.visma.of.solvertemplate.constants;

public class Constants {

    /**
     * Feature flag Constants
     */

    private Constants(){

    }

    public static final String OF_TEST_FLAG = "of-test-flag";

    public static final String openapiPath = Constants.class.getResource("/openapi.json").getPath();
    public static final String requestPayloadPath = Constants.class.getResource("/spec/schemas/request/Request.json").getPath();

}

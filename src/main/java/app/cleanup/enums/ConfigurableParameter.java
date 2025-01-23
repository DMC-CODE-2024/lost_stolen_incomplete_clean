package app.cleanup.enums;

import lombok.Getter;

@Getter
public enum ConfigurableParameter {
    Code201(201, "Initial"),
    Code200(200, "Success"),
    Code500(500, "Fail");
    private String value;
    private int statusCode;

    private ConfigurableParameter(int statusCode, String value) {
        this.statusCode = statusCode;
        this.value = value;
    }

}

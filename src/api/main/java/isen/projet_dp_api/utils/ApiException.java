package isen.projet_dp_api.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.io.Serial;
import java.util.List;
import java.util.UUID;


@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String className;
    private final String fishTag;
    private final List<String> parms;

    public ApiException(Exception exception, String className, List<String> parms) {
        super(exception);
        this.fishTag = UUID.randomUUID().toString().replaceAll("-", "");
        this.className = className;
        this.parms = parms;
    }

    private void logException() {
        log.error("""
                    API Exception: {}", this.getMessage(), this);
    }
}
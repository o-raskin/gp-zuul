package ru.olegraskin.sugateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Getter
public class ResourceForbiddenException extends RuntimeException {

    private String resourceName;

    public ResourceForbiddenException(String resourceName) {
        super(String.format("Access to %s is forbidden!", resourceName));
        this.resourceName = resourceName;
    }
}

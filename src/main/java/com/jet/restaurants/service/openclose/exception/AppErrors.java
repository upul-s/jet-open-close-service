package com.jet.restaurants.service.openclose.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Collections.singletonList;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * App errors and warnings POJO.
 */
@Data
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class AppErrors implements Serializable {

    private List<ErrorMessage> errors;
    private List<ErrorMessage> warnings;

    AppErrors(String key, String message) {
        errors = singletonList(new ErrorMessage(key, message));
    }

    AppErrors(ConnectException e) {
        errors = singletonList(new ErrorMessage(e));
    }

    public AppErrors addErrors(List<ErrorMessage> messages) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.addAll(messages);

        return this;
    }

    public AppErrors addWarnings(List<ErrorMessage> messages) {
        if (warnings == null) {
            warnings = new ArrayList<>();
        }
        warnings.addAll(messages);

        return this;
    }

    public AppErrors withErrors(List<ErrorMessage> errors) {
        this.errors = errors;
        return this;
    }

    public List<ErrorMessage> getWarnings() {
        return isEmpty(warnings) ? null : warnings;
    }

    public List<ErrorMessage> getErrors() {
        return isEmpty(errors) ? null : errors;
    }

    public boolean hasErrors() {
        return !isEmpty(errors);
    }

    public boolean hasWarnings() {
        return !isEmpty(warnings);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ErrorMessage implements Serializable {
        private String key;
        private String message;

        ErrorMessage(ConnectException e) {
            this.key = e.getKey();
            this.message = e.getMessage();
        }
    }

}

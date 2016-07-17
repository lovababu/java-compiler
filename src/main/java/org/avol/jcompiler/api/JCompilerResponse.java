package org.avol.jcompiler.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Durga on 7/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JCompilerResponse {

    private int statusCode;
    private String message;

    private JCompilerResponse(Builder builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int statusCode;
        private String message;

        public final JCompilerResponse build() {
            return new JCompilerResponse(this);
        }

        public Builder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }
    }
}

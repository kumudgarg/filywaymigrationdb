package com.thoughtworks.flywayDbMigration.Exception;

import lombok.Getter;
import lombok.Setter;

public class Response {

    @Getter
    @Setter
    private int statusCode;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private Long id;

    public Response() {
    }

    public Response(int statusCode, String message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }

    public Response(String message) {
        super();
        this.message = message;
    }

    public Response(Long id, int statusCode, String message) {
        this.id = id;
        this.statusCode = statusCode;
        this.message = message;
    }
}

package com.asapp.backend.challenge.model;

public class MessageVideo extends Content {
    private String url;
    private Source source;
}

enum Source {
    YOUTUBE("Youtube"), VIMEO("Vimeo");

    final private String source;

    Source(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

}

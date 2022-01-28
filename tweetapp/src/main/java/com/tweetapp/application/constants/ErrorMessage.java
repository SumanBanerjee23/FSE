package com.tweetapp.application.constants;

public enum ErrorMessage {

    /** The invalid structure. */
    DUPLICATE_EMAIL_INPUT ("Email ID already exists, Please login or provide new Email ID");


    /** The description. */
    private String description;

    /**
     * Instantiates a new error code.
     *
     * @param description the description
     */
    ErrorMessage(String description) {
        this.description = description;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}

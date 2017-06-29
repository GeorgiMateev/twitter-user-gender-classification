package edu.fmi.genderclassify.entities;

import java.util.Date;

public class TwitterProfile {
    private String description;                 // the user's profile description

    private String sidebarColor;                // color of the profile sidebar, as a hex value
    private String linkColor;                   // the link color on the profile, as a hex value
    private String profileImageLink;            // a link to the profile image

    private Boolean profileExists;              // "no" here seems to mean that the profile was meant to be part of the dataset but was not available when contributors went to judge it
    private Double profileExistsConfidence;     // confidence in the existence/non-existence of the profile
    private Date created;                       // date and time when the profile was created
    private String genderGold;                  // if the profile is golden, what is the gender?
    private Boolean profileYnGolden;            // whether the profile y/n value is golden

    public TwitterProfile() {}

    public TwitterProfile desciption(String description) {
        this.description = description;
        return this;
    }

    public TwitterProfile sidebarColor(String sidebarColor) {
        this.sidebarColor = sidebarColor;
        return this;
    }

    public TwitterProfile linkColor(String linkColor) {
        this.linkColor = linkColor;
        return this;
    }

    public TwitterProfile profileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
        return this;
    }

    public TwitterProfile profileExists(Boolean profileExists) {
        this.profileExists = profileExists;
        return this;
    }

    public TwitterProfile profileExistsConfidence(Double profileExistsConfidence) {
        this.profileExistsConfidence = profileExistsConfidence;
        return this;
    }

    public TwitterProfile created(Date created) {
        this.created = created;
        return this;
    }

    public TwitterProfile genderGold(String genderGold) {
        this.genderGold = genderGold;
        return this;
    }

    public TwitterProfile profileYnGolden(Boolean profileYnGolden) {
        this.profileYnGolden = profileYnGolden;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public String getSidebarColor() {
        return sidebarColor;
    }

    public String getLinkColor() {
        return linkColor;
    }

    public String getProfileImageLink() {
        return profileImageLink;
    }

    public Boolean getProfileExists() {
        return profileExists;
    }

    public Double getProfileExistsConfidence() {
        return profileExistsConfidence;
    }

    public Date getCreated() {
        return created;
    }

    public String getGenderGold() {
        return genderGold;
    }

    public Boolean getProfileYnGolden() {
        return profileYnGolden;
    }
}

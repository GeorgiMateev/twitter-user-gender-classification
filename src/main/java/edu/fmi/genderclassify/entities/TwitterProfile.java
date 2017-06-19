package edu.fmi.genderclassify.entities;

import java.util.Date;

/**
 * Created by Miroslav Kramolinski
 */
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
}

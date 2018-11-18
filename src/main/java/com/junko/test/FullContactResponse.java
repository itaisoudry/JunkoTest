package com.junko.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@ToString
public class FullContactResponse {

    private String fullName;

    private String gender;

    private Details details;

    public PersonalInfo convert() {
        PersonalInfo personalInfo = new PersonalInfo();

        personalInfo.setFullName(this.fullName);
        personalInfo.setGender(this.gender);
        personalInfo.setAge(this.details.getAge().getValue());

        if (this.details.getLocations() == null || this.details.getLocations().isEmpty()) {
            log.error("There is not location for the specific email");
        } else {
            // took the first location because that what FullContact shows in the results
            Location location = this.details.getLocations().get(0);
            personalInfo.setAddress(location.getCity() + " - " + location.getCountry());
        }

        return personalInfo;
    }


    @Data
    class Details {
        private Age age;

        private List<Location> locations;

        @Data
        class Age {
            private int value;
        }


    }

    // this is outside due to collision with lombok
    @Data
    static class Location {
        private String city;
        private String country;


        public Location() {

        }
    }


}



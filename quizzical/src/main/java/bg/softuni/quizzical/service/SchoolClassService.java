package bg.softuni.quizzical.service;

import bg.softuni.quizzical.model.service.SchoolClassDTO;

import java.util.List;

public interface SchoolClassService {
    SchoolClassDTO createSchoolClass(SchoolClassDTO map, String userEmail);


    List<SchoolClassDTO> findAllByEmail(String email);

    void seedGroupsInDb();
}

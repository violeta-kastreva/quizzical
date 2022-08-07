package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.model.entity.SchoolClass;
import bg.softuni.quizzical.model.entity.User;
import bg.softuni.quizzical.model.service.SchoolClassDTO;
import bg.softuni.quizzical.repository.SchoolClassRepository;
import bg.softuni.quizzical.repository.UserRepository;
import bg.softuni.quizzical.service.SchoolClassService;
import bg.softuni.quizzical.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassServiceImpl(UserService userService, ModelMapper modelMapper, UserRepository userRepository, SchoolClassRepository schoolClassRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    @Transactional
    public SchoolClassDTO createSchoolClass(SchoolClassDTO map, String userEmail) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(map.getName());
//        map.getStudents().forEach(s->{
//            User student = this.userRepository.findFirstByEmail(s).get();
//            schoolClass.getUsers().add(student);
//        });

        //User user = this.modelMapper.map(this.userService.findByEmail(userEmail), User.class);

        User user = this.userRepository.findFirstByEmail(userEmail).get();
        user.getClasses().add(schoolClass);

      //  this.userRepository.save(user);
        schoolClass.getUsers().add(user);


        map.getStudents().forEach(s->{
            User student = this.userRepository.findFirstByEmail(s).get();
            student.getClasses().add(schoolClass);
        });

        this.schoolClassRepository.save(schoolClass);
        return map;
    }

    @Override
    public List<SchoolClassDTO> findAllByEmail(String email) {
        User user = this.userRepository.findFirstByEmail(email).get();
        Set<SchoolClass> allByUser = user.getClasses();
        return allByUser.stream()
                .map(schoolClass -> modelMapper.map(schoolClass, SchoolClassDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void seedGroupsInDb() {
        if(this.schoolClassRepository.count()==0){

            SchoolClass schoolClass1 = new SchoolClass("Group1",
                    Set.of(this.userRepository.findFirstByEmail("student@student.com").get(),
                            this.userRepository.findFirstByEmail("student2@student.com").get(),
                            this.userRepository.findFirstByEmail("teacher@teacher.com").get()));
            this.schoolClassRepository.save(schoolClass1);
            schoolClass1.getUsers().forEach(s->{
                s.getClasses().add(schoolClass1);
                this.userRepository.save(s);
            });

            SchoolClass schoolClass2 = new SchoolClass("Group2",
                    Set.of(this.userRepository.findFirstByEmail("student@student.com").get(),
                            this.userRepository.findFirstByEmail("student3@student.com").get(),
                            this.userRepository.findFirstByEmail("student4@student.com").get(),
                            this.userRepository.findFirstByEmail("teacher@teacher.com").get()));
            this.schoolClassRepository.save(schoolClass2);
            schoolClass2.getUsers().forEach(s->{
                s.getClasses().add(schoolClass1);
                this.userRepository.save(s);
            });
        }
    }


}

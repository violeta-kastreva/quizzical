package bg.softuni.quizzical.init;


import bg.softuni.quizzical.service.RoleService;
import bg.softuni.quizzical.service.SchoolClassService;
import bg.softuni.quizzical.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;
    private final SchoolClassService schoolClassService;

    public DataInit(RoleService roleService, UserService userService, SchoolClassService schoolClassService) {
        this.roleService = roleService;
        this.userService = userService;
        this.schoolClassService = schoolClassService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.seedRolesInDb();
        userService.seedUsersInDb();
        //schoolClassService.seedGroupsInDb();
    }
}
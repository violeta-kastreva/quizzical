package bg.softuni.quizzical.init;


import bg.softuni.quizzical.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    private final RoleService roleService;

    public DataInit(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.seedRolesInDb();
    }
}
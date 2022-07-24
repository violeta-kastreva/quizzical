package bg.softuni.quizzical.service;

import bg.softuni.quizzical.model.entity.Role;
import bg.softuni.quizzical.model.service.RoleDTO;

import javax.management.relation.RoleNotFoundException;


public interface RoleService {
    void seedRolesInDb();

    RoleDTO findByAuthority(String authority) throws RoleNotFoundException;

    Role findAuthorityByName(String authority) throws RoleNotFoundException;
}

package bg.softuni.quizzical.service.impl;

import bg.softuni.quizzical.model.entity.Role;
import bg.softuni.quizzical.model.service.RoleDTO;
import bg.softuni.quizzical.repository.RoleRepository;
import bg.softuni.quizzical.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.save(new Role("ROLE_ADMIN"));
            this.roleRepository.save(new Role("ROLE_TEACHER"));
            this.roleRepository.save(new Role("ROLE_STUDENT"));
        }
    }

    @Override
    public RoleDTO findByAuthority(String authority) throws RoleNotFoundException {

        return this.roleRepository.findFirstByAuthority(authority)
                .map(role -> this.modelMapper.map(role, RoleDTO.class))
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }

    @Override
    public Role findAuthorityByName(String authorityName) throws RoleNotFoundException {

        return this.roleRepository.findFirstByAuthority(authorityName)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }

}

package com.example.staffapi.service;

import com.example.staffapi.config.Credentials;
import com.example.staffapi.config.AddUserKeycloakConfig;
import com.example.staffapi.dto.CreateWorkerDTO;
import com.example.staffapi.dto.WorkerDTO;
import com.example.staffapi.enitity.Worker;
import com.example.staffapi.mappers.WorkerMapper;
import com.example.staffapi.utils.GeneralUtils;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final WorkerService workerService;

    public ManagerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    public WorkerDTO addWorker(CreateWorkerDTO userDTO){
        String generatedPassword = GeneralUtils.generatePassword();
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(generatedPassword);
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        var res = instance.create(user);

        if(res.getStatus() != HttpStatus.CREATED.value()) {
            throw new RuntimeException("ERROR_WHILE_CREATING_USER");
        }

        List<UserRepresentation> userList = instance.search(user.getUsername()).stream()
                .filter(userRep -> userRep.getUsername().equals(user.getUsername())).collect(Collectors.toList());
        var userRepresentation = userList.get(0);
        this.assignRoleToUser(userRepresentation.getId());

        Worker worker = WorkerMapper.fromCreateWorkerDTO(userDTO, generatedPassword);
        worker = workerService.save(worker);

        return WorkerMapper.toDTO(worker);
    }

    private void assignRoleToUser(String userId) {
        UsersResource usersResource = getInstance();
        UserResource userResource = usersResource.get(userId);


        RealmResource realmResource = getKeycloak();
        RoleRepresentation roleRepresentation = realmResource.roles().list().stream()
                .filter(element -> element.getName().equals("worker")).collect(Collectors.toList()).get(0);
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }

    public UsersResource getInstance(){
        return AddUserKeycloakConfig.getInstance().realm("staff-api-realm").users();
    }

    public RealmResource getKeycloak(){
        return AddUserKeycloakConfig.getInstance().realm("staff-api-realm");
    }
}

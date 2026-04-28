package co.edu.usbcali.ecommerceusb.Service.impl;


import co.edu.usbcali.ecommerceusb.Service.UserService;
import co.edu.usbcali.ecommerceusb.dto.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.mapper.UserMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return List.of();
        }
        List<UserResponse> userResponses = UserMapper.modelToUserResponseList(users);
        return userResponses;
    }
    @Override
    public UserResponse getUserById(Integer id) throws Exception {
        if(id == null || id <= 0){
            throw new Exception("Debe ingresar el id para buscar");
        }
        User user = userRepository.findById(id)
                .orElseThrow(()->
                        new Exception(
                                String.format("Usuario no encontrado con el id: %d", id)));
        UserResponse userResponse = UserMapper.modelToUserResponse(user);
        return userResponse;
    }

    @Override
    public UserResponse getUserByEmail(String email) throws Exception {
        if(email == null || email.isBlank()){
            throw new Exception("Debe ingresar el email");
        }
        User userByEmail = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new Exception(
                                String.format("Usuario no encontrado con el email: %s", email)));

        return UserMapper.modelToUserResponse(userByEmail);
    }

    /**
     * @param createUserRequest
     * @return
     * @throws Exception
     */
    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) throws Exception {
        if(Objects.isNull(createUserRequest)){
            throw new Exception("El objeto createUserRequest no puede ser nulo.");
        }
        if(Objects.isNull(createUserRequest.getFullName()) ||
                createUserRequest.getFullName().isBlank()){
            throw new Exception("El campo Fullname no puede ser nulo.");
        }
        if(Objects.isNull(createUserRequest.getPhone()) ||
                createUserRequest.getPhone().isBlank()){
            throw new Exception("El campo Phone no puede ser nulo.");
        }
        if(Objects.isNull(createUserRequest.getEmail()) ||
                createUserRequest.getEmail().isBlank()){
            throw new Exception("El campo Email no puede ser nulo.");
        }
        if(createUserRequest.getDocumentTypeId() == null || createUserRequest.getDocumentTypeId() <= 0){
            throw new Exception("El campo documentTypeId debe contener un numero mayor a 0.");
        }
        if(createUserRequest.getDocumentNumber() == null || createUserRequest.getDocumentNumber().isBlank()){
            throw new Exception("El campo documentTypeNumber no puede estar nulo ni vacio.");
        }
        if(Objects.isNull(createUserRequest.getBirthDate()) || createUserRequest.getBirthDate().isBlank()){
            throw new Exception("El campo birthDate no puede estar nulo.");
        }
        if(Objects.isNull(createUserRequest.getCountry()) || createUserRequest.getCountry().isBlank()){
            throw new Exception("El campo country no puede estar nulo.");
        }
        if(Objects.isNull(createUserRequest.getAddress() ) || createUserRequest.getAddress().isBlank()){
            throw new Exception("El campo address no puede estar nulo.");
        }
        DocumentType documentType=documentTypeRepository.findById(createUserRequest.getDocumentTypeId())
                .orElseThrow(() -> new Exception("El documentType no encontrado"));

        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new Exception("El email ya existe");
        }
        if(userRepository.existsByDocumentNumberAndDocumentTypeId(createUserRequest.getDocumentNumber(),createUserRequest.getDocumentTypeId())){
            throw new Exception("El documentNumber ya existe");
        }
        User user = UserMapper.createUserRequestToUser(createUserRequest, documentType);


        user = userRepository.save(user);
        UserResponse userResponse = UserMapper.modelToUserResponse(user);
        return userResponse;
    }

}

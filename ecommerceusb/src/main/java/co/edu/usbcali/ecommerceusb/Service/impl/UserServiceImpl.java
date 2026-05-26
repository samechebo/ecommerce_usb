package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.UserService;
import co.edu.usbcali.ecommerceusb.dto.CreateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateUserRequest;
import co.edu.usbcali.ecommerceusb.dto.UserResponse;
import co.edu.usbcali.ecommerceusb.exception.BadRequestException;
import co.edu.usbcali.ecommerceusb.exception.InternalServerErrorException;
import co.edu.usbcali.ecommerceusb.exception.NotFoundException;
import co.edu.usbcali.ecommerceusb.mapper.UserMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return List.of();
        return UserMapper.modelToUserResponseList(users);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para buscar");
        }
        return userRepository.findById(id)
                .map(UserMapper::modelToUserResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Usuario no encontrado con el id: %d", id)));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Debe ingresar el email");
        }
        return userRepository.findByEmail(email)
                .map(UserMapper::modelToUserResponse)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Usuario no encontrado con el email: %s", email)));
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto CreateUserRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getFullName()) || request.getFullName().isBlank()) {
            throw new BadRequestException("El campo fullName no puede ser nulo.");
        }
        if (Objects.isNull(request.getPhone()) || request.getPhone().isBlank()) {
            throw new BadRequestException("El campo phone no puede ser nulo.");
        }
        if (Objects.isNull(request.getEmail()) || request.getEmail().isBlank()) {
            throw new BadRequestException("El campo email no puede ser nulo.");
        }
        if (request.getDocumentTypeId() == null || request.getDocumentTypeId() <= 0) {
            throw new BadRequestException("El campo documentTypeId debe ser mayor a 0.");
        }
        if (request.getDocumentNumber() == null || request.getDocumentNumber().isBlank()) {
            throw new BadRequestException("El campo documentNumber no puede ser nulo.");
        }
        if (Objects.isNull(request.getBirthDate()) || request.getBirthDate().isBlank()) {
            throw new BadRequestException("El campo birthDate no puede ser nulo.");
        }
        if (Objects.isNull(request.getCountry()) || request.getCountry().isBlank()) {
            throw new BadRequestException("El campo country no puede ser nulo.");
        }
        if (Objects.isNull(request.getAddress()) || request.getAddress().isBlank()) {
            throw new BadRequestException("El campo address no puede ser nulo.");
        }
        DocumentType documentType = documentTypeRepository.findById(request.getDocumentTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de documento no encontrado"));
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new InternalServerErrorException("El email ya existe.");
        }
        if (userRepository.existsByDocumentNumberAndDocumentTypeId(
                request.getDocumentNumber(), request.getDocumentTypeId())) {
            throw new InternalServerErrorException("El documentNumber ya existe.");
        }
        try {
            User user = UserMapper.createUserRequestToUser(request, documentType);
            user = userRepository.save(user);
            return UserMapper.modelToUserResponse(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new BadRequestException("El objeto UpdateUserRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getFullName()) || request.getFullName().isBlank()) {
            throw new BadRequestException("El campo fullName no puede ser nulo.");
        }
        if (Objects.isNull(request.getPhone()) || request.getPhone().isBlank()) {
            throw new BadRequestException("El campo phone no puede ser nulo.");
        }
        if (Objects.isNull(request.getEmail()) || request.getEmail().isBlank()) {
            throw new BadRequestException("El campo email no puede ser nulo.");
        }
        if (Objects.isNull(request.getCountry()) || request.getCountry().isBlank()) {
            throw new BadRequestException("El campo country no puede ser nulo.");
        }
        if (Objects.isNull(request.getAddress()) || request.getAddress().isBlank()) {
            throw new BadRequestException("El campo address no puede ser nulo.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Usuario no encontrado con el id: %d", id)));
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new InternalServerErrorException("El email ya existe.");
        }
        try {
            user.setFullName(request.getFullName());
            user.setPhone(request.getPhone());
            user.setEmail(request.getEmail());
            user.setCountry(request.getCountry());
            user.setAddress(request.getAddress());
            user.setUpdatedAt(OffsetDateTime.now());
            user = userRepository.save(user);
            return UserMapper.modelToUserResponse(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(Integer id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Debe ingresar el id para eliminar");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Usuario no encontrado con el id: %d", id)));
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
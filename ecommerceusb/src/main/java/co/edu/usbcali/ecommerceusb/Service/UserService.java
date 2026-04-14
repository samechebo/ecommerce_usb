package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.controller.UserResponse;

import java.util.List;

public interface UserService {
        List<UserResponse> getUsers();
        UserResponse getUserById(Integer id) throws Exception;
        UserResponse getUserByEmail(String email);
}

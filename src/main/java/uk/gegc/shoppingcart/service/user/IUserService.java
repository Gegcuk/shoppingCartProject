package uk.gegc.shoppingcart.service.user;

import uk.gegc.shoppingcart.model.User;
import uk.gegc.shoppingcart.request.CreateUserRequest;
import uk.gegc.shoppingcart.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
}

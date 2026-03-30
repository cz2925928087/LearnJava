package user.factory;

import user.User;


public interface IUserFactory {

    User createUser(String name,int userID);
}

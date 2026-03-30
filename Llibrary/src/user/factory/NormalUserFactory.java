package user.factory;

import user.NormalUser;
import user.User;


public class NormalUserFactory implements IUserFactory{
    @Override
    public User createUser(String name, int userID) {
        return new NormalUser(name,userID);
    }
}

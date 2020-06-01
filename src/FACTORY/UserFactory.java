package FACTORY;


import SERVICE.UserService;

public class UserFactory {
    public static UserService getDAOInstance() throws Exception{
        return new UserService();
    }
}

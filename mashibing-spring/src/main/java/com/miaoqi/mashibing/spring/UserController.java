package com.miaoqi.mashibing.spring;

public class UserController {

    @AutoWired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    // public void setUserService(UserService userService) {
    //     this.userService = userService;
    // }

}

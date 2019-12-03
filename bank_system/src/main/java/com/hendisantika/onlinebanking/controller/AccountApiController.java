package com.hendisantika.onlinebanking.controller;

import com.google.common.base.Preconditions;
import com.hendisantika.onlinebanking.entity.PrimaryAccount;
import com.hendisantika.onlinebanking.entity.SavingsAccount;
import com.hendisantika.onlinebanking.entity.User;
import com.hendisantika.onlinebanking.repository.RoleDao;
import com.hendisantika.onlinebanking.repository.UserDao;
import com.hendisantika.onlinebanking.security.UserRole;
import com.hendisantika.onlinebanking.service.AccountService;
import com.hendisantika.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController

public class AccountApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountService accountService;

    @PostMapping("api/addUser")
    public String create(@RequestBody Map<String, String> body) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(body.get("password"));
        user.setFirstName(body.get("firstName"));
        user.setLastName(body.get("lastName"));
        user.setEmail(body.get("Email"));
        user.setPhone(body.get("phone"));
        if (userService.checkUserExists(user.getUsername(), user.getEmail())) {

            if (userService.checkEmailExists(user.getEmail())) {
                return "email exist";
            }

            if (userService.checkUsernameExists(user.getUsername())) {
                return "user exist";
            }
        } else {
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

            userService.createUser(user, userRoles);
            return user.toString();
            //return blogMockedData.createBlog(id, title, content);
        }
         return "";
    }
    @PutMapping("api/addPrimaryAcc/{id}")
    public String addPrimaryAcc(@PathVariable( "id" ) Long id) {
        //User user = new User();
        Preconditions.checkNotNull(id);
        User user = userService.findByuserId(id);
        //user = userDao.findByUserId(Long.parseLong(id));

        user.setPrimaryAccount(accountService.createPrimaryAccount());


        userDao.save(user);
        return user.toString();
    }

    @GetMapping("api/get/{id}")
    public String getAccount(@PathVariable String id) {
       // User user = new User();
        User user = userService.findByuserId(Long.parseLong(id));
        //User user = userService.findByUsername(id);
        //user.setPrimaryAccount(accountService.createPrimaryAccount());
        //user.setSavingsAccount(accountService.createSavingsAccount());

       // userDao.save(user);
        return user.toString();
    }

    @PutMapping("api/addCheckingAcc/{id}")
    public String addCheckingAcc(@PathVariable String id) {
        // User user = new User();
        User user = userService.findByuserId(Long.parseLong(id));
        //User user = userService.findByUsername(id);

        user.setSavingsAccount(accountService.createSavingsAccount());

        userDao.save(user);
        return user.toString();
    }

    @PutMapping("api/deposit/{accountType}/{amount}/{receiver}")
    public String accDeposit(@PathVariable String accountType,@PathVariable double amount, @PathVariable String receiver) {
        // User user = new User();
        accountService.deposit(accountType,amount,receiver);
       // User user = userService.findByuserId(Long.parseLong(id));
        //User user = userService.findByUsername(id);
       User user =  userService.findByUsername(receiver);
        //user.setSavingsAccount(accountService.createSavingsAccount());

        //userDao.save(user);
        return "PrimaryBalance : " + user.getPrimaryAccount().getAccountBalance().toString() + '\'' +
                "SavingBalancd : " + user.getSavingsAccount().getAccountBalance().toString();
    }
}




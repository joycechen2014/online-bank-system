package com.hendisantika.onlinebanking.repository;

import com.hendisantika.onlinebanking.entity.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 08/08/18 Time: 06.08 To change this
 * template use File | Settings | File Templates.
 */
public interface UserDao extends CrudRepository<User, Long> {

  User findByUsername(String username);

  User findByEmail(String email);

  List<User> findAll();
}
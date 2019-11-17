package com.hendisantika.onlinebanking.repository;

import com.hendisantika.onlinebanking.entity.Appointment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 08/08/18 Time: 06.03 To change this
 * template use File | Settings | File Templates.
 */
public interface AppointmentDao extends CrudRepository<Appointment, Long> {

  List<Appointment> findAll();
}
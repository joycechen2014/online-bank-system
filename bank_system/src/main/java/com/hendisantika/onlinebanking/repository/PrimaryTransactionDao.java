package com.hendisantika.onlinebanking.repository;

import com.hendisantika.onlinebanking.entity.PrimaryTransaction;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA. Project : online-banking User: hendisantika Email:
 * hendisantika@gmail.com Telegram : @hendisantika34 Date: 08/08/18 Time: 06.05 To change this
 * template use File | Settings | File Templates.
 */
public interface PrimaryTransactionDao extends CrudRepository<PrimaryTransaction, Long> {

  List<PrimaryTransaction> findAll();
}
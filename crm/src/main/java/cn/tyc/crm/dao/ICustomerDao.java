package cn.tyc.crm.dao;

import cn.tyc.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerDao extends JpaRepository<Customer, Integer> {

    public List<Customer> findByFixedAreaIdIsNull();

    public List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query(value = "update Customer set fixedAreaId = null where fixedAreaId = ?1")
    @Modifying
    public void setFixedAreaIdIsNull(String fixedAreaId);

    @Query(value = "update Customer set fixedAreaId = ?1 where id = ?2")
    @Modifying
    public void assignCustomers2FixedArea(String fixedAreaId, Integer customerId);


    List<Customer> findByTelephone(String telephone);

    @Query(value = "update Customer set type = 1 where telephone = ?1")
    @Modifying
    void activeMail(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);


    @Query("select fixedAreaId from Customer where address = ?1")
    String findFixedAreaIdByAddress(String address);
}

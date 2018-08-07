package cn.tyc.crm.service;

import cn.tyc.crm.domain.Customer;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface ICustomerService {

    List<Customer> findAll();

    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    void assignCustomers2FixedArea(String fixedAreaId, List<Integer> customerIds);

    boolean regist(Customer customer);

    List<Customer> findByTelephone(String telephone);

    void activeMail(String telephone);

    Customer login(String telephone,String password);

    String findFixedAreaIdByAddress(String address);
}

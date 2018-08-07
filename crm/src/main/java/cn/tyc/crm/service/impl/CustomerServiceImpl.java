package cn.tyc.crm.service.impl;

import cn.tyc.crm.dao.ICustomerDao;
import cn.tyc.crm.domain.Customer;
import cn.tyc.crm.service.ICustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Resource
    private ICustomerDao customerDao;

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public List<Customer> findByFixedAreaIdIsNull() {
        return customerDao.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findByFixedAreaId(String fixedAreaId) {
        return customerDao.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, List<Integer> customerIds) {
        customerDao.setFixedAreaIdIsNull(fixedAreaId);
        if (customerIds != null && customerIds.size() > 0) {
            for (Integer customerId : customerIds) {
                customerDao.assignCustomers2FixedArea(fixedAreaId, customerId);
            }
        }
    }

    @Override
    public boolean regist(Customer customer) {
        List<Customer> customers = customerDao.findByTelephone(customer.getTelephone());
        if (customers == null || customers.size()==0){
            customerDao.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> findByTelephone(String telephone) {
        return customerDao.findByTelephone(telephone);
    }

    @Override
    public void activeMail(String telephone) {
        customerDao.activeMail(telephone);
    }

    @Override
    public Customer login(String telephone, String password) {
        return customerDao.findByTelephoneAndPassword(telephone,password);
    }

    @Override
    public String findFixedAreaIdByAddress(String address) {
        return customerDao.findFixedAreaIdByAddress(address);
    }

}

package Test;
import java.sql.*;
import java.io.InputStream;
import com.superwallet.dao.*;
import com.superwallet.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DAOTest {
    private ApplicationContext context;

    public void setUp() throws Exception {
        this.context = new ClassPathXmlApplicationContext("classpath:resources/spring/applicationContext-dao.xml");
    }
        //BGS
    public void BGSInsert(){
        BGSWalletMapper mapper = this.context.getBean(BGSWalletMapper.class);

        BGSWallet wallet = new BGSWallet();
        wallet.setAvailableAmount(100);
        wallet.setLockedAmount(50);
        wallet.setUid("AXO1230SD01HDSBAKJ");
        mapper.insert(wallet);

    }

    public void BGSSelect(){
        BGSWalletMapper mapper = this.context.getBean(BGSWalletMapper.class);
        BGSWallet wallet = mapper.selectByPrimaryKey("AXO1230SD01HDSBAKJ");
        System.out.println(wallet);
    }

    public void BGSUpdate(){
        BGSWalletMapper mapper = this.context.getBean(BGSWalletMapper.class);
        BGSWallet wallet = new BGSWallet();
        wallet.setAvailableAmount(200);
        wallet.setLockedAmount(0);
        wallet.setUid("AXO1230SD01HDSBAKJ");
        int res = mapper.updateByPrimaryKey(wallet);
        System.out.println("result:"+res);
    }
//锁仓
    public void LockWareHouseInsert(){
        lockWarehouseMapper mapper = this.context.getBean(lockWarehouseMapper.class);
        lockWarehouse lock = new lockWarehouse();
        lock.setAmount(100);
        lock.setCreatedTime(new java.util.Date());
        lock.setPeriod(10);
        lock.setStatus(new Byte("1"));
        lock.setUid("lock");
        lock.setLockWarehouseId(10L);

        mapper.insert(lock);
    }

    public void LockWareHouseSelect(){
        lockWarehouseMapper mapper = this.context.getBean(lockWarehouseMapper.class);
        lockWarehouseKey key = new lockWarehouseKey();
        key.setLockWarehouseId(10L);
        key.setUid("lock");
        lockWarehouse lock = mapper.selectByPrimaryKey(key);
        System.out.println(lock);
    }

    public void LockWareHouseUpdate(){
        lockWarehouseMapper mapper = this.context.getBean(lockWarehouseMapper.class);
        lockWarehouse lock = new lockWarehouse();
        lock.setAmount(200);
        lock.setCreatedTime(new java.util.Date());
        lock.setPeriod(20);
        lock.setStatus(new Byte("0"));
        lock.setUid("lock");
        lock.setLockWarehouseId(10L);
        int j = mapper.updateByPrimaryKey(lock);
        System.out.println("result:"+j);
    }
//私钥数据库
    public void PriKeyhouseInsert(){
        PriKeyWarehouseMapper mapper = this.context.getBean(PriKeyWarehouseMapper.class);
        PriKeyWarehouse house = new PriKeyWarehouse();
        house.setKeyType(new Byte("1"));
        house.setPriKey("AIUX8Q8EVD7");
        house.setUid("1");
        mapper.insert(house);
    }

    public void PriKeyhouseSelect(){
        PriKeyWarehouseMapper mapper = this.context.getBean(PriKeyWarehouseMapper.class);

        PriKeyWarehouse res = mapper.selectByPrimaryKey( "AIUX8Q8EVD7");
        System.out.println(res);
    }

    public void PriKeyhouseDelect(){
        PriKeyWarehouseMapper mapper = this.context.getBean(PriKeyWarehouseMapper.class);
        int res = mapper.deleteByPrimaryKey("AIUX8Q8EVD7");
        System.out.println("result:"+res);
    }
}

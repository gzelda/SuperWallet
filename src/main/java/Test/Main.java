package Test;

import java.util.*;
import java.io.InputStream;
import com.superwallet.*;

public class Main {

    public static void main(String[] args) {
        DAOTest test = new DAOTest();

        try {
            test.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        test.BGSInsert();
        test.BGSSelect();
        test.BGSUpdate();
        test.BGSSelect();

        test.LockWareHouseInsert();
        test.LockWareHouseSelect();
        test.LockWareHouseUpdate();
        test.LockWareHouseSelect();

        test.PriKeyhouseInsert();
        test.PriKeyhouseSelect();
        test.PriKeyhouseDelect();
    }
}

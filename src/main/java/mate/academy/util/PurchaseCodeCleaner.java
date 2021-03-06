package mate.academy.util;

import mate.academy.database.PurchaseCodeDao;
import mate.academy.database.impl.PurchaseCodeDaoHibImpl;
import mate.academy.model.Code;
import org.apache.log4j.Logger;
import java.util.Timer;
import java.util.TimerTask;

public class PurchaseCodeCleaner {
    private static final Logger logger = Logger.getLogger(PurchaseCodeCleaner.class);
    private static final PurchaseCodeDao purchaseCodeDao = new PurchaseCodeDaoHibImpl();
    private static final long DELAY_30_MIN = 1800000;
    private static final long DELAY_5_MIN = 300000;

    public static void clean(Code code) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                purchaseCodeDao.remove(code);
                logger.info("Code " + code.getValue() + " died!");
            }
        }, DELAY_5_MIN);
    }
}

package mate.academy.initializer;

import mate.academy.database.CartDao;
import mate.academy.database.GoodDao;
import mate.academy.database.OrderDao;
import mate.academy.database.PurchaseCodeDao;
import mate.academy.database.RoleDao;
import mate.academy.database.UserDao;
import mate.academy.database.impl.CartDaoHibImpl;
import mate.academy.database.impl.GoodDaoHibImpl;
import mate.academy.database.impl.OrderDaoHibImpl;
import mate.academy.database.impl.PurchaseCodeDaoHibImpl;
import mate.academy.database.impl.RoleDaoHibImpl;
import mate.academy.database.impl.UserDaoHibImpl;
import mate.academy.model.Code;
import mate.academy.model.Good;
import mate.academy.model.Order;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.util.PurchaseCodeCleaner;
import org.apache.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value = "/init", loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(InitServlet.class);
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final RoleDao roleDao = new RoleDaoHibImpl();
    private static final CartDao cartDao = new CartDaoHibImpl();
    private static final OrderDao orderDao = new OrderDaoHibImpl();
    private static final PurchaseCodeDao purchaseCodeDao = new PurchaseCodeDaoHibImpl();

    @Override
    public void init() {
        logger.info("INITIALIZATION DATABASE");
        Role admin = new Role(1L, "ADMIN");
        Role user = new Role(2L, "USER");
        roleDao.add(admin);
        roleDao.add(user);
        User fluriko = new User("fluriko", "123123", admin, "fluricode@gmail.com");
        User candy = new User("candy", "123123", user, "artfluriko@gmail.com");
        User sweetie = new User("sweetie", "123123", user, "sweetie@gmail.com");
        userDao.add(fluriko);
        userDao.add(candy);
        userDao.add(sweetie);
        Good frenchLop = new Good("french lop", "the best pet", "bunnies", 20.5);
        Good englishAngora = new Good("english angora", "the fluffiest pet", "bunnies", 20.8);
        Good cinnamon = new Good("cinnamon", "amily or cinnamon", "bunnies", 20.2);
        Good hay = new Good("fresh hay", "good quality hay ", "food", 2.2);
        goodDao.add(frenchLop);
        goodDao.add(englishAngora);
        goodDao.add(cinnamon);
        goodDao.add(hay);
        sweetie.addGoodToCart(hay);
        Order order = new Order(sweetie.getCart());
        sweetie.addOrder(order);
        cartDao.edit(sweetie.getCart());
        orderDao.edit(order);
        Code testCode = new Code("1212", sweetie.getCart());
        purchaseCodeDao.add(testCode);
        PurchaseCodeCleaner.clean(testCode);
    }
}

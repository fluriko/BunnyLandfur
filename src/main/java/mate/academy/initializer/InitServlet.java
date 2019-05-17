package mate.academy.initializer;

import mate.academy.database.good.GoodDao;
import mate.academy.database.good.GoodDaoHib;
import mate.academy.database.good.PurchaseCodeDao;
import mate.academy.database.good.PurchaseCodeDaoHib;
import mate.academy.database.role.RoleDao;
import mate.academy.database.role.RoleDaoHib;
import mate.academy.database.user.UserDao;
import mate.academy.database.user.UserDaoHib;
import mate.academy.model.Code;
import mate.academy.model.Good;
import mate.academy.model.Role;
import mate.academy.model.User;
import mate.academy.util.PurchaseCodeCleaner;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value = "/init", loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(InitServlet.class);
    private static final UserDao USER_DAO = new UserDaoHib();
    private static final GoodDao GOOD_DAO = new GoodDaoHib();
    private static final RoleDao ROLE_DAO = new RoleDaoHib();
    private static final PurchaseCodeDao PURCHASE_CODE_DAO = new PurchaseCodeDaoHib();

    @Override
    public void init() {
        logger.debug("INITIALIZATION DATABASE");
        Role admin = new Role(1L, "ADMIN");
        Role user = new Role(2L, "USER");
        ROLE_DAO.addRole(admin);
        ROLE_DAO.addRole(user);
        User fluriko = new User("fluriko", "123123", admin, "fluricode@gmail.com");
        User candy = new User("candy", "123123", user, "artfluriko@gmail.com");
        User sweetie = new User("sweetie", "123123", user, "sweetie@gmail.com");
        USER_DAO.addUser(fluriko);
        USER_DAO.addUser(candy);
        USER_DAO.addUser(sweetie);
        Good frenchLop = new Good("french lop", "the best pet", "bunnies", 20.5);
        Good englishAngora = new Good("english angora", "the fluffiest pet", "bunnies", 20.8);
        Good cinnamon = new Good("cinnamon", "amily or cinnamon", "bunnies", 20.2);
        Good hay = new Good("fresh hay", "good quality hay ", "food", 2.2);
        GOOD_DAO.addGood(frenchLop);
        GOOD_DAO.addGood(englishAngora);
        GOOD_DAO.addGood(cinnamon);
        GOOD_DAO.addGood(hay);
        Code testCode = new Code(55L, "1212", candy, frenchLop);
        PURCHASE_CODE_DAO.addCode(testCode);
        PurchaseCodeCleaner.clean(testCode);
    }
}

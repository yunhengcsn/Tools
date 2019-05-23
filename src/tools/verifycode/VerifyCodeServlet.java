package tools.verifycode;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.verifycode
 * @Author: csn
 * @Description: generate verifycode
 */
public class VerifyCodeServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Vcode code = new Vcode();
        BufferedImage image = code.getImage();

        Vcode.output(image,resp.getOutputStream());
        req.getSession().setAttribute("verifyCode",code.getText());
    }
}

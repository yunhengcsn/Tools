package tools.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.filter
 * @Author: csn
 * @Description: handle request encoding problem
 */
public class EncodingFilter implements Filter {
    private String charset = "UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //web.xml中若配置charset这一init-param则使用该配置
        String charset = filterConfig.getInitParameter("charset");
        if(charset != null && !charset.isEmpty()) {
            this.charset = charset;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        //get method
        if(req.getMethod().equalsIgnoreCase("get")) {
            if(!(req instanceof GetRequest)) {
                req = new GetRequest(req, charset);
                }
        } else {//post method
            req.setCharacterEncoding(charset);
        }

        filterChain.doFilter(req,servletResponse);

    }

    @Override
    public void destroy() {

    }
}

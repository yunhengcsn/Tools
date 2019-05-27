package tools.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @BelongsProject: Tools
 * @BelongsPackage: tools.filter
 * @Author: csn
 * @Description:
 */
public class GetRequest extends HttpServletRequestWrapper {
    private HttpServletRequest req;
    private String charset;
    public GetRequest(HttpServletRequest req, String charset) {
        super(req);
        this.req = req;
        this.charset = charset;
    }

    @Override
    public String getParameter(String name){
        String val = req.getParameter(name);
        if(val == null) return null;
        String encodedParam = null;
        try {
            //对参数进行编码处理
            encodedParam = new String(val.getBytes("ISO-8859-1"),charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedParam;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String,String[]> map = req.getParameterMap();
        if(map == null) return map;
        for(String key : map.keySet()) {
            String[] vals = map.get(key);
            for(int i = 0; i < vals.length; i++) {
                try {
                    vals[i] = new String(vals[i].getBytes("ISO-8859-1"),charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] vals = req.getParameterValues(name);
        for(int i = 0; i < vals.length; i++) {
            try {
                vals[i] = new String(vals[i].getBytes("ISO-8859-1"),charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return vals;
    }
}

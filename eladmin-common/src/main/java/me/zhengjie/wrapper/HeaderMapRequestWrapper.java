package me.zhengjie.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;


/**
 * <p>
 * request修改header包装
 * </p>
 *
 * @author miaoyj
 * @since 2022-06-21
 */
public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    private Map headerMap = new HashMap<>();

    /**
     * construct a wrapper for this request
     *
     * @param request
     */
    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * add a header with given name and value
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    /**
     * <p>
     * 批量添加
     * </p>
     *
     * @param headers /
     */
    public void addHeaders(Map headers) {
        headerMap.putAll(headers);
    }


    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if (headerMap.containsKey(name)) {
            headerValue = String.valueOf(headerMap.get(name));
        }
        return headerValue;
    }

    /**
     * get the Header names
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        for (Object name : headerMap.keySet()) {
            names.add(String.valueOf(name));
        }
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            Object value = headerMap.get(name);
            if (String.class.isInstance(value)) {
                values.add(String.valueOf(value));
            } else if (value != null) {
                values = (List<String>) value;
            }
        }
        return Collections.enumeration(values);
    }
}
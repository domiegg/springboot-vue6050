package com.daowen.configuration;

import com.daowen.util.BodyReaderHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "requestWrapperFilter", urlPatterns = "/*")
public class RequestWrapperFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestWrapperFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if(null == requestWrapper) {
            log.error("包装request失败");
            chain.doFilter(request, response);
            return;
        }
        log.info("包装request成功");
        chain.doFilter(requestWrapper, response);

    }

    @Override
    public void destroy() {
    }
}

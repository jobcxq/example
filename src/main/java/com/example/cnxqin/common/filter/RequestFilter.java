package com.example.cnxqin.common.filter;

import com.google.common.io.ByteSource;
import com.example.cnxqin.common.constant.RequestConstant;
import com.example.cnxqin.common.wrapper.DuplicateReadRequestWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author cnxqin
 * @desc 利用DuplicateReadRequestWrapper装饰器将inputStream备份数据，装入request自定义REQUEST_DATA属性中，以便于后面拦截器进行权限校验
 * @date 2021/01/23 12:49
 */
@Order(1)
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(
                    (HttpServletRequest)request);
            String requestData = getRequestData(requestWrapper);
            request.setAttribute(RequestConstant.REQUEST_DATA, requestData);
            filterChain.doFilter(requestWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String getRequestData(HttpServletRequest request) throws IOException {
        String result;
        if (RequestMethod.GET.name().equals(request.getMethod())) {
            result = request.getQueryString();
        } else {
            result = ByteSource.wrap(((ContentCachingRequestWrapper)request).getContentAsByteArray())
                    .asCharSource(Charset.forName("UTF-8")).read();
        }
        return result;
    }
}

package com.example.cnxqin.common.wrapper;

import com.google.common.io.ByteSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * request复读装饰器
 * RequestLogFilterFilter中需要读取ServletInputStream流的内容，而ServletInputStream只能读一次，会造成之后再次读取失败。
 * 此处对HttpServletRequest进行封装，通过将inputStream缓存下来，提供{@link #getContentAsByteArray()}方法可以重复读取请求数据。
 * 代码参考{@link org.springframework.web.util.ContentCachingRequestWrapper}
 *
 */
public class DuplicateReadRequestWrapper extends HttpServletRequestWrapper {

    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private ServletInputStream inputStream;
    private BufferedReader reader;
    private Map<String, String[]> parameterMaps;

    public DuplicateReadRequestWrapper(HttpServletRequest request) {
        super(request);
        this.inputStream = new DuplicateReadInputStream(request);
        this.reader = new BufferedReader(new InputStreamReader(this.inputStream));
        this.initParameterMapsFromInputStream();
    }

    @Override
    public ServletInputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public BufferedReader getReader() {
        return this.reader;
    }

    @Override
    public String getParameter(String name) {
        if (isFormPost()) {
            String[] parameter = this.parameterMaps.get(name);
            return parameter == null ? null : parameter[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (isFormPost()) {
            return this.parameterMaps;
        }
        return super.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        if (isFormPost()) {
            return Collections.enumeration(this.parameterMaps.keySet());
        }
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        if (isFormPost()) {
            return this.parameterMaps.get(name);
        }
        return super.getParameterValues(name);
    }

    public byte[] getContentAsByteArray() {
        return ((DuplicateReadInputStream) this.inputStream).getContentAsByteArray();
    }

    private boolean isFormPost() {
        String contentType = getContentType();
        return (contentType != null && contentType.contains(FORM_CONTENT_TYPE) &&
                HttpMethod.POST.matches(getMethod()));
    }

    private void initParameterMapsFromInputStream() {
        this.parameterMaps = new LinkedHashMap<>();
        if (!isFormPost()) {
            return;
        }

        String queryString;
        try {
            queryString = ByteSource.wrap((this.getContentAsByteArray()))
                    .asCharSource(Charset.forName("UTF-8")).read();
            queryString = URLDecoder.decode(queryString, "UTF-8");
        } catch (IOException e) {
            queryString = null;
        }

        if (StringUtils.isBlank(queryString)) {
            return;
        }

        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = idx > 0 ? pair.substring(0, idx) : pair;
            String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;
            List<String> values = new ArrayList<>();
            if (this.parameterMaps.containsKey(key)) {
                Collections.addAll(values, this.parameterMaps.get(key));
            }
            values.add(value);
            this.parameterMaps.put(key, values.toArray(new String[0]));
        }
    }

    /**
     * 用来缓存ServletInputStream的内部类
     */
    private class DuplicateReadInputStream extends ServletInputStream {

        private ServletInputStream is;
        private byte[] cachedContent;
        private InputStream inputStream;

        public DuplicateReadInputStream(HttpServletRequest request) {
            try {
                this.is = request.getInputStream();
                this.cachedContent = convertInputStreamToCachedContent(this.is);
                this.inputStream = new ByteArrayInputStream(this.cachedContent);
            } catch (IOException e) {
                this.is = null;
            } finally {
                closeStream(this.is);
            }
        }

        @Override
        public boolean isFinished() {
            return is.isFinished();
        }

        @Override
        public boolean isReady() {
            return is.isReady();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            is.setReadListener(readListener);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        public byte[] getContentAsByteArray() {
            return cachedContent;
        }

        private byte[] convertInputStreamToCachedContent(InputStream is) throws IOException {
            int len = 1024;
            byte[] temp = new byte[len];
            int c = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((c = is.read(temp, 0, len)) != -1) {
                baos.write(temp, 0, c);
            }
            return baos.toByteArray();
        }

        private void closeStream(ServletInputStream is) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

}

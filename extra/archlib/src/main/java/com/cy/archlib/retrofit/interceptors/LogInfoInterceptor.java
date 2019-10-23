package com.cy.archlib.retrofit.interceptors;


import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static okhttp3.internal.platform.Platform.INFO;

public class LogInfoInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void logInfo(String message);

        /**
         * A {@link LogInfoInterceptor.Logger} defaults output appropriate for the current platform.
         */
        LogInfoInterceptor.Logger DEFAULT = new LogInfoInterceptor.Logger() {
            @Override
            public void logInfo(String message) {
                Platform.get().log(INFO, message, null);
            }
        };
    }

    public LogInfoInterceptor() {
        this(LogInfoInterceptor.Logger.DEFAULT);
    }

    public LogInfoInterceptor(LogInfoInterceptor.Logger logger) {
        this.logger = logger;
    }

    private final LogInfoInterceptor.Logger logger;

    private volatile LogInfoInterceptor.Level level = LogInfoInterceptor.Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public LogInfoInterceptor setLevel(LogInfoInterceptor.Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public LogInfoInterceptor.Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        LogInfoInterceptor.Level level = this.level;

        Request request = chain.request();
        if (level == LogInfoInterceptor.Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == LogInfoInterceptor.Level.BODY;
        boolean logHeaders = logBody || level == LogInfoInterceptor.Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        StringBuffer sb = new StringBuffer();
        sb.append("┌--------------------开始请求 ")
                .append(request.method() + "  ")
                .append(request.url())
                .append((connection != null ? " " + connection.protocol() : ""))
                .append("\n");
       /* if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }*/

        if (logHeaders) {
           /* if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    logger.log("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    logger.log("Content-Length: " + requestBody.contentLength());
                }
            }*/

            /*Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logger.log(name + ": " + headers.value(i));
                }
            }*/

            if (!logBody || !hasRequestBody) {
                //logger.log("--> END " + request.method());
            } else if (bodyHasUnknownEncoding(request.headers())) {
                //logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {
                    sb.append(buffer.readString(charset) + "\n");
                    //logger.log("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    //logger.log("--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            sb.append("HTTP 请求出错: " + e + "\n");
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        sb.append(response.code())
                .append(", ")
                .append(response.message().isEmpty() ? "" : response.message() + ", ")
                .append(tookMs + "ms" + (!logHeaders ? ", " + bodySize + " body" : ""))
                .append("\n");

        if (logHeaders) {
            Headers headers = response.headers();
            /*for (int i = 0, count = headers.size(); i < count; i++) {
                logger.log(headers.name(i) + ": " + headers.value(i));
            }*/

            if (!logBody || !HttpHeaders.hasBody(response)) {
                sb.append("└--------------------结束请求").append("\n");
            } else if (bodyHasUnknownEncoding(response.headers())) {
                sb.append("└--------------------结束请求 (encoded body omitted)").append("\n");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Long gzippedLength = null;
                if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                    gzippedLength = buffer.size();
                    GzipSource gzippedResponseBody = null;
                    try {
                        gzippedResponseBody = new GzipSource(buffer.clone());
                        buffer = new Buffer();
                        buffer.writeAll(gzippedResponseBody);
                    } finally {
                        if (gzippedResponseBody != null) {
                            gzippedResponseBody.close();
                        }
                    }
                }

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                    sb.append("└--------------------结束请求 HTTP (binary " + buffer.size() + "-byte body omitted)").append("\n");
                    return response;
                }

                if (contentLength != 0) {
                    sb.append(buffer.clone().readString(charset)).append("\n");
                }

                if (gzippedLength != null) {
                    sb.append("└--------------------结束请求 HTTP (" + buffer.size() + "-byte, "
                            + gzippedLength + "-gzipped-byte body)").append("\n");
                } else {
                    sb.append("└--------------------结束请求 HTTP (" + buffer.size() + "-byte body)").append("\n");
                }
            }
            logger.logInfo(sb.toString());
        }
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }
}
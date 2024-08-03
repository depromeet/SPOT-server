package org.depromeet.spot.infrastructure.jpa.common;

import java.util.Locale;

import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

@Configuration
public class P6spySqlFormatter implements MessageFormattingStrategy {

    private static String ROOT_PACKAGE = "org.depromeet.spot";
    private static String P6SPY_PACKAGE = "org.depromeet.spot.jpa.common.P6spySqlFormatter";

    @Override
    public String formatMessage(
            int connectionId,
            String now,
            long elapsed,
            String category,
            String prepared,
            String sql,
            String url) {
        sql = formatSql(category, sql);
        return String.format(
                "[%s] | took %d ms | connectionId %d | %s | %s",
                category, elapsed, connectionId, filterStack(), formatSql(category, sql));
    }

    private String formatSql(String category, String sql) {
        if (isNotEmpty(sql) && isStatement(category)) {
            String trimmedSQL = sql.trim().toLowerCase(Locale.ROOT);
            if (isDDL(trimmedSQL)) {
                return FormatStyle.DDL.getFormatter().format(sql);
            }
            return FormatStyle.BASIC.getFormatter().format(sql);
        }
        return sql;
    }

    // 일반적인 쿼리인지 판단
    // 트랜잭션 커밋, 롤백 등 쿼리가 아닌 작업은 포맷팅하지 않는다.
    private boolean isStatement(String category) {
        return Category.STATEMENT.getName().equals(category);
    }

    private boolean isNotEmpty(String sql) {
        return sql != null && !sql.trim().isEmpty();
    }

    private boolean isDDL(String sql) {
        return (sql.startsWith("create")) || sql.startsWith("alter") || sql.startsWith("comment");
    }

    private String filterStack() {
        StackTraceElement[] stackTraces = new Throwable().getStackTrace();
        StringBuilder sb = new StringBuilder();
        int order = 1;

        for (StackTraceElement element : stackTraces) {
            String trace = element.toString();
            if (trace.startsWith(ROOT_PACKAGE) && !trace.contains(P6SPY_PACKAGE)) {
                sb.append("\n\t\t").append(order++).append(".").append(trace);
            }
        }
        return sb.toString();
    }
}

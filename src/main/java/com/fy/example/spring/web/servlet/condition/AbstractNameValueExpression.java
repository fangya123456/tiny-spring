package com.fy.example.spring.web.servlet.condition;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数抽象模板
 *
 * @author
 * @create 2017-12-18 10:34
 **/
public abstract class AbstractNameValueExpression<T> implements NameValueExpression{
    /**参数名称*/
    protected final String name;
    /**参数值*/
    protected final T value;
    /**参数匹配规则：= or !=*/
    protected final boolean isNegated;

    AbstractNameValueExpression(String expression) {
        int separator = expression.indexOf('=');
        if (separator == -1) {
            this.isNegated = expression.startsWith("!");
            this.name = isNegated ? expression.substring(1) : expression;
            this.value = null;
        }
        else {
            this.isNegated = (separator > 0) && (expression.charAt(separator - 1) == '!');
            this.name = isNegated ? expression.substring(0, separator - 1) : expression.substring(0, separator);
            this.value = parseValue(expression.substring(separator + 1));
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public boolean isNegated() {
        return this.isNegated;
    }

    protected abstract T parseValue(String valueExpression);

    public final boolean match(HttpServletRequest request) {
        boolean isMatch;
        if (this.value != null) {
            isMatch = matchValue(request);
        }
        else {
            isMatch = matchName(request);
        }
        return isNegated ? !isMatch : isMatch;
    }

    protected abstract boolean matchName(HttpServletRequest request);

    protected abstract boolean matchValue(HttpServletRequest request);
}

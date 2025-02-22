package de.rjst.rjstbackendservice.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.MDC;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestLogAspect {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(requestLog)")
    public Object around(final ProceedingJoinPoint joinPoint, final RequestLog requestLog) throws Throwable {
        final String value = getValue(joinPoint, requestLog);
        final String key = requestLog.key();
        MDC.put(key, value);
        try {
            return joinPoint.proceed();
        } finally {
            MDC.remove(key);
        }
    }

    @Nullable
    private String getValue(final ProceedingJoinPoint joinPoint, final RequestLog requestLog) {
        final CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        final Object[] args = joinPoint.getArgs();
        final EvaluationContext context = new StandardEvaluationContext();
        final String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        final Expression expression = parser.parseExpression(requestLog.value());
        return expression.getValue(context, String.class);
    }

}

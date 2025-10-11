package de.rjst.bes.logging;

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
public class AsyncProcessingLogAspect {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Around("execution(public * * (..)) && @annotation(asyncProcessingLog)")
    public Object around(final ProceedingJoinPoint joinPoint, final AsyncProcessingLog asyncProcessingLog) throws Throwable {
        final var key = asyncProcessingLog.key();
        final var value = getValue(joinPoint, asyncProcessingLog);
        try {
            MDC.put(key, value);
            return joinPoint.proceed();
        } finally {
            MDC.remove(key);
        }
    }

    @Nullable
    private String getValue(final ProceedingJoinPoint joinPoint, final AsyncProcessingLog asyncProcessingLog) {
        final var signature = (CodeSignature) joinPoint.getSignature();
        final Object[] args = joinPoint.getArgs();
        final EvaluationContext context = new StandardEvaluationContext();
        final String[] parameterNames = signature.getParameterNames();
        final int length = parameterNames.length;
        for (int i = 0; i < length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        final Expression expression = parser.parseExpression(asyncProcessingLog.value());
        return expression.getValue(context, String.class);
    }

}

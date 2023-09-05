package aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Slf4j
@Component
@Aspect
public class OperateAspect {

    @Pointcut("@annotation(aop.RecordOperate)")
    public void pointcut() {}

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100)
    );
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        threadPoolExecutor.execute(() -> {
            try{
                MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
                RecordOperate annotation = methodSignature.getMethod().getAnnotation(RecordOperate.class);

                Class<? extends Convert> convert = annotation.convert();
                Convert logConvert = convert.newInstance();
                OperateLogDO operateLogDO = logConvert.convert(proceedingJoinPoint.getArgs()[0]);

                operateLogDO.setDesc(annotation.desc());
                operateLogDO.setResult(result.toString());

                log.info("insert operateLog {}", operateLogDO);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }
}

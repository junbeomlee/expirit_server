package com.tanx.expirit.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import rx.Observable;
import rx.functions.Action0;

@Aspect
@Component
public class AopTx {

	private Log mylogger = LogFactory.getLog(getClass());

	@Autowired
	ObservableTxFactory observableTx;

	@Autowired
	private PlatformTransactionManager transactionManager;

	
	
	@Around("@annotation(com.tanx.expirit.annotation.RxTransactional)")
	public Object writeFormAspect(ProceedingJoinPoint joinPoint) throws Throwable {
		//TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		// Object retVal = null;

		Object retVal = joinPoint.proceed();

		if (retVal instanceof Observable) {
			System.out.println("tx start");
			TransactionStatus status=transactionManager.getTransaction(new DefaultTransactionDefinition());
			return ((Observable<Object>)retVal)
					.doOnError(e->{
						transactionManager.rollback(status);
						System.out.println("transaction error rollback");
					})
					.doOnNext(s->{
						transactionManager.commit(status);
						System.out.println("commit");
					});
		}
		return retVal;
	}
}

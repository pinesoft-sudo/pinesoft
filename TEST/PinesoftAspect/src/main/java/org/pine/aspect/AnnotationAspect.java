package org.pine.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AnnotationAspect {
	@Autowired
	private LogHandler logHandler;

	// Service层切点
	@Pointcut("@annotation(org.pine.annotation.ServiceLog)")
	public void ServiceAspect() {
	}

	// Controller层切点
	@Pointcut("@annotation(org.pine.annotation.ControllerLog)")
	public void ControllerAspect() {
	}

	// 异常层切点
	@Pointcut("@annotation(org.pine.annotation.ThrowExceptionLog)")
	public void ExceptionAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 */
	@Before("ControllerAspect()")
	public void ControllerBefore(JoinPoint joinPoint) {
		logHandler.OprateLog(joinPoint, "controller");
	}

	/**
	 * 前置通知 用于拦截Service层记录用户的操作
	 * 
	 * @param joinPoint
	 */
	@Before("ServiceAspect()")
	public void ServiceBefore(JoinPoint joinPoint) {
		logHandler.OprateLog(joinPoint, "service");
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "ExceptionAspect()", throwing = "e")
	public void ExceptionAfterThrowing(JoinPoint joinPoint, Throwable e) throws ClassNotFoundException {
		logHandler.ExceptionLog(joinPoint, e);
	}

}

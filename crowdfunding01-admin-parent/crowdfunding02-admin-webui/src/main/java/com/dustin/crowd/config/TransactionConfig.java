package com.dustin.crowd.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/** 全域性事務控制，防止漏加 事務註解，也就是說開發中可以不用加事務註解了 */
@Configuration
@Aspect
public class TransactionConfig {
	private static final int TX_METHOD_TIMEOUT = 60;

	private static final String AOP_POINTCUT_EXPRESSION = "execution(* *..*ServiceImpl.*(..))";

	@Autowired
	private TransactionManager transactionManager;

	@Bean
	public TransactionInterceptor txAdvice() {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

		RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
		readOnly.setReadOnly(true);
		readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		// 禁止使用這種 。這種會導致 只讀事務不生效的。而且 可以 在程式碼裡面 提交事務的
		// readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

		RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
		required.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		// 單位是 秒
		required.setTimeout(TX_METHOD_TIMEOUT);
		Map<String, TransactionAttribute> txMap = new HashMap<>(50);
		txMap.put("add*", required);
		txMap.put("save*", required);
		txMap.put("insert*", required);
		txMap.put("update*", required);
		txMap.put("delete*", required);
		txMap.put("create*", required);
		txMap.put("init*", required);
		txMap.put("submit*", required);
		txMap.put("remove*", required);
		txMap.put("edit*", required);
		txMap.put("modify*", required);
		txMap.put("batch*", required);
		txMap.put("mass*", required);
		txMap.put("handle*", required);
		txMap.put("exec*", required);
		txMap.put("import*", required);
		txMap.put("set*", required);

		txMap.put("get*", readOnly);
		txMap.put("select*", readOnly);
		txMap.put("list*", readOnly);
		txMap.put("query*", readOnly);
		txMap.put("find*", readOnly);
		txMap.put("count*", readOnly);
		txMap.put("page*", readOnly);
		txMap.put("all*", readOnly);
		txMap.put("*", readOnly);

		source.setNameMap(txMap);
		return new TransactionInterceptor(transactionManager, source);
	}

	@Bean
	public Advisor txAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, txAdvice());
	}

	// @Bean 這種方式不夠好，不能配置回滾異常。 其實和上面的方式 的原理是一樣的。
	// public TransactionInterceptor txAdvice() {
	//
	// DefaultTransactionAttribute txAttr_REQUIRED = new
	// DefaultTransactionAttribute();
	// txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	// txAttr_REQUIRED.setTimeout(TX_METHOD_TIMEOUT);
	//
	// DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new
	// DefaultTransactionAttribute();
	// txAttr_REQUIRED_READONLY.setPropagationBehavior(
	// TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	// txAttr_REQUIRED_READONLY.setReadOnly(true);
	//
	// NameMatchTransactionAttributeSource source = new
	// NameMatchTransactionAttributeSource();
	// source.addTransactionalMethod("add*", txAttr_REQUIRED);
	// source.addTransactionalMethod("save*", txAttr_REQUIRED);
	// source.addTransactionalMethod("delete*", txAttr_REQUIRED);
	// source.addTransactionalMethod("update*", txAttr_REQUIRED);
	// source.addTransactionalMethod("exec*", txAttr_REQUIRED);
	// source.addTransactionalMethod("set*", txAttr_REQUIRED);
	//
	// // source.addTransactionalMethod("get*", txAttr_REQUIRED_READONLY);
	// // source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);
	// // source.addTransactionalMethod("find*", txAttr_REQUIRED_READONLY);
	// // source.addTransactionalMethod("list*", txAttr_REQUIRED_READONLY);
	// // source.addTransactionalMethod("count*", txAttr_REQUIRED_READONLY);
	// source.addTransactionalMethod("*", txAttr_REQUIRED_READONLY);
	//
	//
	//
	// return new TransactionInterceptor(transactionManager, source);
	// }

}

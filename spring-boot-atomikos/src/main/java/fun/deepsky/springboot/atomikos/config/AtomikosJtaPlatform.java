package fun.deepsky.springboot.atomikos.config;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

public class AtomikosJtaPlatform extends AbstractJtaPlatform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 382822939141239243L;
	
	static TransactionManager transactionManager;
	
	static UserTransaction userTransaction;

	@Override
	protected TransactionManager locateTransactionManager() {
		return transactionManager;
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		return userTransaction;
	}

}

/**
 * 
 */
package org.semanticsoft.vaaclipse.api;

/**
 * @author rushan
 *
 */
public interface VaadinExecutorService
{
	void invokeLater(Runnable runnable);
	void invokeLater(Object key, Runnable runnable);
	boolean containsKey(Object key);
	void invokeLaterAlways(Runnable runnable);
	void removeAlwaysRunnable(Runnable runnable);
}

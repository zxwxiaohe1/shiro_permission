package com.sinux.ssh.global;

import java.io.IOException;
import java.io.InputStream;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * @author Yockii
 * 
 */
public class EhCacheManager implements CacheManager, Initializable, Destroyable {

	private static final Logger log = LoggerFactory
			.getLogger(EhCacheManager.class);

	protected org.springframework.cache.ehcache.EhCacheCacheManager manager;

	private boolean cacheManagerImplicitlyCreated = false;

	private String cacheManagerConfigFile = "classpath:org/apache/shiro/cache/ehcache/ehcache.xml";

	public EhCacheManager() {
	}

	public org.springframework.cache.ehcache.EhCacheCacheManager getCacheManager() {
		return manager;
	}

	public void setCacheManager(
			org.springframework.cache.ehcache.EhCacheCacheManager manager) {
		this.manager = manager;
	}

	public String getCacheManagerConfigFile() {
		return this.cacheManagerConfigFile;
	}

	public void setCacheManagerConfigFile(String classpathLocation) {
		this.cacheManagerConfigFile = classpathLocation;
	}

	protected InputStream getCacheManagerConfigFileInputStream() {
		String configFile = getCacheManagerConfigFile();
		try {
			return ResourceUtils.getInputStreamForPath(configFile);
		} catch (IOException e) {
			throw new ConfigurationException(
					"Unable to obtain input stream for cacheManagerConfigFile ["
							+ configFile + "]", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.util.Destroyable#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.util.Initializable#init()
	 */
	@Override
	public void init() throws ShiroException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.cache.CacheManager#getCache(java.lang.String)
	 */
	@Override
	public final <K, V> Cache<K, V> getCache(String name) throws CacheException {
		if (log.isTraceEnabled()) {
			log.trace("Acquiring EhCache instance named [" + name + "]");
		}

		try {
			org.springframework.cache.Cache cache = manager.getCache(name);
			if (cache == null) {
				if (log.isInfoEnabled()) {
					log.info(
							"Cache with name '{}' does not yet exist. Creating now.",
							name);
				}
				this.manager.getCacheManager().addCache(name);
				cache = manager.getCache(name);
				if (log.isInfoEnabled()) {
					log.info("Added EhCache named [" + name + "]");
				}
			} else {
				if (log.isInfoEnabled()) {
					log.info("Using existing EHCache named [" + cache.getName()
							+ "]");
				}
			}
			return new EhCache<K, V>(cache);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	private EhCacheCacheManager ensureCacheManager() {
		try {
			if (this.manager == null) {
				if (log.isDebugEnabled()) {
					log.debug("cacheManager property not set. Constructing CacheManager instance... ");
				}

				this.manager = new org.springframework.cache.ehcache.EhCacheCacheManager();
				manager.setCacheManager(new net.sf.ehcache.CacheManager(
						getCacheManagerConfigFileInputStream()));
				if (log.isTraceEnabled()) {
					log.trace("instantiated Ehcache CacheManager instance WITH SPRING EhCacheCacheManager");
				}
				cacheManagerImplicitlyCreated = true;
				if (log.isDebugEnabled()) {
					log.debug("implicit cacheManager created WITH SPRING EhCacheCacheManager successfully.");
				}
			}
			return this.manager;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}
}

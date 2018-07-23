package com.sinux.ssh.global;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Ehcache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache.ValueWrapper;

/**
 * @author Yockii
 * 
 */
public class EhCache<K, V> implements Cache<K, V> {

	private static final Logger log = LoggerFactory.getLogger(EhCache.class);

	private org.springframework.cache.Cache cache;

	public EhCache(org.springframework.cache.Cache cache) {
		if (cache == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.cache = cache;
	}

	@Override
	public V get(K key) throws CacheException {
		try {
			if (log.isTraceEnabled()) {
				log.trace("Getting object from cache [" + cache.getName()
						+ "] for key [" + key + "]");
			}
			if (key == null) {
				return null;
			} else {
				ValueWrapper vw = cache.get(key);
				if (vw == null) {
					if (log.isTraceEnabled()) {
						log.trace("ValueWrapper for [" + key + "] is null.");
					}
					return null;
				} else {
					return (V) vw.get();
				}
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if (log.isTraceEnabled()) {
			log.trace("Putting object in cache [" + cache.getName()
					+ "] for key [" + key + "]");
		}
		try {
			V previous = get(key);
			cache.put(key, value);
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public V remove(K key) throws CacheException {
		if (log.isTraceEnabled()) {
			log.trace("Removing object from cache [" + cache.getName()
					+ "] for key [" + key + "]");
		}
		try {
			V previous = get(key);
			cache.evict(key);
			return previous;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public void clear() throws CacheException {
		if (log.isTraceEnabled()) {
			log.trace("Clearing all objects from cache [" + cache.getName()
					+ "]");
		}
		try {
			cache.clear();
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public int size() {
		try {
			return ((Ehcache) cache.getNativeCache()).getSize();
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public Set<K> keys() {
		try {
			Ehcache ehcache = (Ehcache) cache.getNativeCache();
			@SuppressWarnings("unchecked")
			List<K> keys = ehcache.getKeys();
			if (!CollectionUtils.isEmpty(keys)) {
				return Collections.unmodifiableSet(new LinkedHashSet<K>(keys));
			} else {
				return Collections.emptySet();
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	@Override
	public Collection<V> values() {
		try {
			Ehcache ehcache = (Ehcache) cache.getNativeCache();
			@SuppressWarnings("unchecked")
			List<K> keys = ehcache.getKeys();
			if (!CollectionUtils.isEmpty(keys)) {
				List<V> values = new ArrayList<V>(keys.size());
				for (K key : keys) {
					V value = get(key);
					if (value != null) {
						values.add(value);
					}
				}
				return Collections.unmodifiableList(values);
			} else {
				return Collections.emptyList();
			}
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public long getMemoryUsage() {
		try {
			return ((Ehcache) cache.getNativeCache()).calculateInMemorySize();
		} catch (Throwable t) {
			return -1;
		}
	}

	public long getMemoryStoreSize() {
		try {
			return ((Ehcache) cache.getNativeCache()).getMemoryStoreSize();
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public long getDiskStoreSize() {
		try {
			return ((Ehcache) cache.getNativeCache()).getDiskStoreSize();
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public String toString() {
		return "EhCache [" + cache.getName() + "]";
	}
}

package com.appleframework.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;

import com.appleframework.cache.core.CacheException;
import com.appleframework.cache.core.CacheManager;

@SuppressWarnings({ "unchecked" })
public class RedissonMapCacheManager implements CacheManager {

	private static Logger logger = Logger.getLogger(RedissonMapCacheManager.class);
	
	private String name = "REDIS_CACHE_MANAGER";
	
	private RedissonClient redisson;

	public void setRedisson(RedissonClient redisson) {
		this.redisson = redisson;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public RMapCache<String, Object> getCacheMap() {
		return redisson.getMapCache(name);
	}

	public void clear() throws CacheException {
		try {
			getCacheMap().clear();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}

	public Object get(String key) throws CacheException {
		try {
			return getCacheMap().get(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}

	@Override
	public <T> T get(String key, Class<T> clazz) throws CacheException {
		try {
			return (T)getCacheMap().get(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}

	public boolean remove(String key) throws CacheException {
		try {
			getCacheMap().remove(key);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}

	public void set(String key, Object value) throws CacheException {
		if (null != value) {
			try {
				getCacheMap().put(key, value);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CacheException(e.getMessage());
			}
		}
	}

	public void set(String key, Object value, int expireTime) throws CacheException {
		if (null != value) {
			try {
				getCacheMap().put(key, value, expireTime, TimeUnit.SECONDS);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	@Override
	public List<Object> get(List<String> keyList) throws CacheException {
		try {
			List<Object> list = new ArrayList<Object>();
			RMapCache<String, Object> map = this.getCacheMap();
			for (String key : keyList) {
				list.add(map.get(key));
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}

	@Override
	public List<Object> get(String... keys) throws CacheException {
		try {
			List<Object> list = new ArrayList<Object>();
			RMapCache<String, Object> map = this.getCacheMap();
			for (String key : keys) {
				list.add(map.get(key));
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}

	@Override
	public <T> List<T> get(Class<T> clazz, List<String> keyList) throws CacheException {
		try {
			List<T> list = new ArrayList<T>();
			RMapCache<String, Object> map = this.getCacheMap();
			for (String key : keyList) {
				list.add((T)map.get(key));
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}

	@Override
	public <T> List<T> get(Class<T> clazz, String... keys) throws CacheException {
		try {
			List<T> list = new ArrayList<T>();
			RMapCache<String, Object> map = this.getCacheMap();
			for (String key : keys) {
				list.add((T)map.get(key));
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CacheException(e.getMessage());
		}
	}
		
}
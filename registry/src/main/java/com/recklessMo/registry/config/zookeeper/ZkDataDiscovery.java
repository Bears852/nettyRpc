package com.recklessMo.registry.config.zookeeper;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.recklessMo.common.Closeable;
import com.recklessMo.registry.config.DataDiscovery;
import com.recklessMo.registry.config.model.Node;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 *
 * 用于发现指定znode下的节点
 *
 * 必须extends Node对象
 *
 */
public class ZkDataDiscovery<T extends Node> implements DataDiscovery, Closeable {

    private static final Logger logger = LoggerFactory.getLogger(ZkDataDiscovery.class);

    private Class<T> elementClass;
    private CuratorFramework client;
    private PathChildrenCache cache;
    private AtomicReference<List<T>> result = new AtomicReference<>();

    public ZkDataDiscovery(CuratorFramework client, String path, Class<T> elementClass){
        Preconditions.checkNotNull(client, "client required not null");
        Preconditions.checkNotNull(path, "path required not null");
        this.client = client;
        this.elementClass = elementClass;
        this.cache = new PathChildrenCache(client, path, true);
        this.result.set(new LinkedList<>());
        try {
            this.cache.start();
            this.cache.getListenable().addListener((c, event)->{
                logger.info("{} for zkDiscovery {}",event.getType().name(), ZKPaths.getNodeFromPath(event.getData().getPath()));
                updateNodeList();
            });
            logger.info("init zkDiscovery success with path {}", path);
        }catch (Exception e){
            logger.error("init zkDiscovery failed", e);
            CloseableUtils.closeQuietly(this.cache);
        }
    }


    @Override
    public List<T> getDataList() {
        return result.get();
    }

    private void updateNodeList() {
        try {
            List<T> nodeList = new LinkedList<>();
            for (ChildData childData : cache.getCurrentData()) {
                //反序列化
                String str = new String(childData.getData());
                logger.info(str);
                nodeList.add(JSON.parseObject(str, elementClass));
            }
            result.getAndSet(nodeList);
            logger.info("update list success !");
        }catch (Exception e){
            logger.error("update list error", e);
        }
    }

    public void close() {
        try {
            this.cache.close();
            logger.info("exit zkDataDiscovery");
        }catch (Exception e){
            logger.error("close cache error", e);
        }
    }


    public Class<T> getElementClass() {
        return elementClass;
    }

    public void setElementClass(Class<T> elementClass) {
        this.elementClass = elementClass;
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    public PathChildrenCache getCache() {
        return cache;
    }

    public void setCache(PathChildrenCache cache) {
        this.cache = cache;
    }

    public AtomicReference<List<T>> getResult() {
        return result;
    }

    public void setResult(AtomicReference<List<T>> result) {
        this.result = result;
    }


}

package com.recklessMo.registry.config.zookeeper;

import com.google.common.base.Preconditions;
import com.recklessMo.registry.config.DataDiscovery;
import com.recklessMo.registry.config.constant.Constants;
import com.recklessMo.registry.config.model.Node;
import com.recklessMo.registry.config.model.ServerNode;
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
public class ZkDiscovery<T extends Node> implements DataDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(ZkDiscovery.class);

    private Class<T> elementClass;
    private CuratorFramework client = null;
    private PathChildrenCache cache = null;
    private AtomicReference<List<T>> result = new AtomicReference<>();

    public ZkDiscovery(CuratorFramework client, Class<T> elementClass) throws Exception{
        Preconditions.checkNotNull(client, "client required not null");
        this.client = client;
        this.elementClass = elementClass;
        this.cache = new PathChildrenCache(client, Constants.SERVER, true);
        try {
            this.cache.start();
            this.cache.getListenable().addListener((curatorFramework, event)->{
                logger.info("{} for zkDiscovery {}",event.getType().name(), ZKPaths.getNodeFromPath(event.getData().getPath()));
                updateNodeList();
            });
        }catch (Exception e){
            logger.error("init zkDiscovery failed", e);
            throw e;
        }finally {
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
                nodeList.add(null);
            }
            result = new AtomicReference<>(nodeList);
        }catch (Exception e){
            logger.error("update list error", e);
        }
    }



}

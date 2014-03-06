package com.huagai.eSpace.common;

import com.huagai.eSpace.util.ConstantsUtil;
import com.huawei.ecs.mtk.log.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huagai on 14-3-6.
 */

/**
 * 线程池管理类
 * <p/>
 * 线程池管理类,创建单线程池和固定线程数的线程池.使用完线程池后,需调用
 * <code>shutDownSingleExecutor()</code>关闭线程池.避免线程池中还有线
 * 程在排队运行.
 * <p/>
 */
final public class ThreadManager {
    /**
     * 固定的线程数
     */
    private static final int FIXEDNUMBER = 3;

    private static ThreadManager instance;

    private Object singleLock = new Object();

    /**
     * 单线程执行器,只用于广播
     */
    private final ExecutorService broadcastPool;

    /**
     * 单线程池,加载完成则关闭
     */
    private final ExecutorService simpleSinglePool;

    /**
     * 固定个数的线程执行器
     */
    private final ExecutorService fixedThreadPool;

    /**
     * voip注册及相关操作线程
     */
    private final ExecutorService voipPool;


    private ThreadManager() {
        //Logger.debug(LocalLog.APPTAG, "thread manager init!");

        broadcastPool = Executors.newSingleThreadExecutor();
        fixedThreadPool = Executors.newFixedThreadPool(FIXEDNUMBER);
        simpleSinglePool = Executors.newSingleThreadExecutor();
        voipPool = Executors.newSingleThreadExecutor();
    }

    synchronized public static ThreadManager getInstance() {
        if (null == instance) {
            instance = new ThreadManager();
            //Logger.debug(LocalLog.APPTAG, "get a instance of ThreadManager");
        }
        return instance;
    }

    private void add(ExecutorService service, Runnable command) {
        if (service != null && command != null) {
            try {
                service.execute(command);
            } catch (Exception e) {
                Logger.error(ConstantsUtil.APPTAG, e.toString());
            }

        }
    }


    public void addToBroadcastThread(Runnable command) {
        ExecutorService service = broadcastPool;
        synchronized (singleLock) {
            add(service, command);
        }
    }

    public void addToSingleThread(Runnable command) {
        add(simpleSinglePool, command);
    }

    /**
     * voip 注册
     *
     * @param command
     */
    public void addVoipThread(Runnable command) {
        /**
         Logger.debug(LocalLog.APPTAG,
         "voipThread status[isShutDown:" + voipPool.isShutdown()
         + ",isTerminated:" + voipPool.isTerminated()
         + "]");**/
        add(voipPool, command);
    }

    public void addToFixedThreadPool(Runnable command) {
        add(fixedThreadPool, command);
    }

    private void shutDownThread(ExecutorService service) {
        if (service == null) {
            return;
        }
        service.shutdownNow();
        //List<Runnable>  threads = service.shutdownNow();

        //Logger.debug(LocalLog.APPTAG, threads != null ?
        //         "leaved threads.size() = " + threads.size() : "no leaved threads");
    }

    public void clearThreadResource() {
        //Logger.debug(LocalLog.APPTAG, "clear!");

        shutDownThread(broadcastPool);
        shutDownThread(simpleSinglePool);
        shutDownThread(fixedThreadPool);
        shutDownThread(voipPool);

        toNull();

        //Logger.debug(LocalLog.APPTAG, "clear end");
    }

    private static void toNull() {
        instance = null;
    }
}

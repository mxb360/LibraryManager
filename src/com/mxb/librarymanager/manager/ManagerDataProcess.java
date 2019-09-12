package com.mxb.librarymanager.manager;

import com.mxb.librarymanager.dataprocess.database.MysqlDataProcess;
import com.mxb.librarymanager.dataprocess.server.ServerDataProcess;

/**
 * 后台数据处理的类
 * 此类没有任何新的实现，具体功能取决于其继承的父类
 * 当前此类继承至ServerDataProcess，表示通过连接服务器进行数据处理
 * 如果想通过其他方式处理数据，请实现DataProcess接口，并将此类继承至该实现即可
 * 目前已经支持：从服务器处理数据：继承类ServerDataProcess
 *              直接通过数据库处理数据：继承类DatabaseDataProcess
 */
public class ManagerDataProcess extends ServerDataProcess{
    // Do Nothing
}
/**
 * Created by yuanyi on 2018/1/18.
 */

/**
 * 应用模块
 * @type {Object}
 */
export default [
    {
        name: '获取机器环境',
        method: 'getEnv',
        path: '/resource/machines/env',
        type: 'get'
    },{
        name: '获取服务器机器',
        method: 'getAll',
        path: '/resource/machines',
        type: 'get'
    },{
        name: '获取所有分组',
        method: 'getAllGroups',
        path: '/resource/machines/groups',
        type: 'get'
    },{
        name: '更新服务器信息',
        method: 'updateMachineInfo',
        path: (pathParam) => {
            return "/resource/machines/"+pathParam.machineId;
        },
        type: 'put'
    }
]

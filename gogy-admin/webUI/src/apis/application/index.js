/**
 * Created by yuanyi on 2018/1/18.
 */

/**
 * 应用模块
 * @type {Object}
 */
export default [
    {
        name: '获取所有应用信息',
        method: 'getAll',
        path: '/applications',
        type: 'get'
    },{
        name: '获取单个应用的信息',
        method: 'getById',
        path: (pathParam) => {
            return "/applications/"+pathParam.id;
        },
        type: 'get'
    },{
        name: '获取应用的构建情况',
        method: 'getAllBuilds',
        path: (pathParam) => {
            return "/builds/"+pathParam.applicationKey;
        },
        type: 'get'
    },{
        name: '执行应用构建',
        method: 'build',
        path: (pathParam) => {
            return "/builds/"+pathParam.applicationKey;
        },
        type: 'post'
    },{
        name: '获取应用所有分支',
        method: 'getAllBranches',
        path: (pathParam) => {
            return "/builds/"+pathParam.applicationKey+"/branches";
        },
        type: 'get'

    },{
        name: '获取应用所有标签',
        method: 'getAllTags',
        path: (pathParam) => {
            return "/builds/"+pathParam.applicationKey+"/tags";
        },
        type: 'get'

    }
]

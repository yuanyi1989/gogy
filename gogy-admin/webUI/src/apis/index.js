
/**
 * 导出所有模块需要用到接口
 * 一级属性：模块名
 * 一级属性中的方法：当前模块需要用的接口
 * @type {Object}
 */

import user from './user/'
import application from './application'

export default [
    {
        module: 'user',
        name: '用户管理',
        list: user
    },
    {
        module: 'application',
        name: '应用管理',
        list: application
    }]

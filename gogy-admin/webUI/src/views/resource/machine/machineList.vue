<template>
    <div>
        <p></p>
        <Card :bordered="true">
            <div slot="title">
                <Select @on-change="searchGroupChange" v-model="searchData.group" style="width:100px">
                    <Option v-for="item in groups" :value="item" :key="item">{{ item }}</Option>
                </Select>
                <Input v-model="searchData.name" icon="search" @change="handleSearchMachineName" placeholder="请输入别称搜索..." style="width: 200px" />
            </div>
            <div slot="extra">
                <Button style="margin-left:20px" @click="newApplication" type="primary" icon="plus-round">New Server</Button>
            </div>
            <can-edit-table :editIncell="false" @on-change="handleRowChange" v-model="tableData" :hover-show="true" :edit-incell="true" :columns-list="machineTableColumns"></can-edit-table>
            <div style="text-align: center; margin-top:30px;">
                <Page placement="top" :total="searchData.pageData.total" :page-size="searchData.pageData.pageSize" show-sizer
                      @on-change="selectNewPage"
                      @on-page-size-change="changePageSize"></Page>
            </div>
        </Card>
    </div>
</template>
<script>
    import canEditTable from '../../tables/components/canEditTable.vue';
    export default {
        components: {
            canEditTable
        },
        data () {
            return {
                currentEnvKey:'',
                groups:[],
                searchData: {
                    name: '',
                    group:'',
                    pageData: {
                        pageSize:20,
                        pageNo:0,
                        total:0
                }

                },
                tableData: [],
                machineTableColumns: [
                    {
                        title: 'id',
                        key: 'id'
                    },
                    {
                        title: '序列号',
                        key: 'serialNumber'
                    },
                    {
                        title: '分组',
                        key: 'group',
                        editable: true
                    },
                    {
                        title: '名称',
                        key: 'alias',
                        editable: true
                    },
                    {
                        title: 'IP',
                        key: 'address'
                    },
                    {
                        title: '状态',
                        key: 'status',
                        render: (h, params) => {
                            const row = params.row;
                            const color = row.status === 1 ? 'green' : row.status === 0 ? 'gray' : 'red';
                            const text = row.status === 1 ? '正常' : row.status === 0 ? '未知' : '异常';

                            return h('Tag', {
                                props: {
                                    type: 'dot',
                                    color: color
                                }
                            }, text);
                        }
                    },
                    {
                        title: '操作',
                        key: 'handle',
                        handle: ['edit', 'delete', 'view']
                    }
                ]
            }
        },
        created () {
            var params = this.$router.currentRoute.params;
            this.currentEnvKey = params.envKey;
            this.loadData(params.envKey);
            this.loadGroups(params.envKey);
        },
        methods: {
            loadGroups (envKey) {
                var _this = this;
                this.$$api_machine_getAllGroups({
                    pathParams: {
                        env: envKey
                    },
                    data: {},
                    fn: data => {
                        console.log(data);
                        _this.groups = data;
                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            },
            loadData (envKey) {
                var _this = this;
                this.$$api_machine_getAll({
                    data: {
                        env: envKey,
                        group: _this.searchData.group,
                        pageNo: _this.searchData.pageData.pageNo,
                        pageSize: _this.searchData.pageData.pageSize,
                        name: _this.searchData.name
                    },
                    fn: data => {
                        console.log(data);
                        _this.tableData = data.data;
                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            },
            updateData(value) {
                var _this = this;
                this.$$api_machine_updateMachineInfo({
                    data: value,
                    pathParams: {
                        machineId: value.id //服务器ID
                    },
                    fn: data => {
                        console.log(data);

                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: true
                });
            },
            newApplication() {

            },
            handleSearchMachineName() {

            },
            searchGroupChange() {

            },
            selectNewPage(pageIndex) {
                this.searchData.pageData.currentPage = pageIndex-1;
                this.loadData(this.currentEnvKey);
            },
            changePageSize(currentPageSize) {
                this.searchData.pageData.pageSize = currentPageSize;
                this.selectNewPage(1);
            },
            // 操作表单的方法
            handleRowChange(tableValue, rowIndex) {
                this.updateData(tableValue[rowIndex]);
            },
        }
    }
</script>
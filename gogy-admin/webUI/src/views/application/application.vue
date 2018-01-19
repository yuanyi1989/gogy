<style lang="less">
    @import '../../styles/common.less';
    @import './table.less';
</style>

<template>
    <div>
        <Row class="margin-top-10">
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="android-remove"></Icon>
                    可编辑单元行
                </p>
                <div class="edittable-table-height-con">
                    <can-edit-table :rowInfoTitle="rowInfoTitle" :rowInfoWidth="rowInfoWidth" v-model="applicationData" @on-change="handleRowChange" :columns-list="applicationColumns">
                        <div slot="rowInfo"><application-info></application-info></div>
                    </can-edit-table>
                </div>
            </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import canEditTable from '../tables/components/canEditTable.vue';
    import tableData from '../tables/components/table_data.js';
    import applicationInfo from './applicationInfo.vue';
    export default {
        name: 'application-table',
        components: {
            canEditTable,
            applicationInfo
        },
        data () {
            return {
                applicationColumns: [
                    {
                        title: '序号',
                        type: 'index',
                        width: 80,
                        align: 'center'
                    },
                    {
                        title: 'Id',
                        align: 'center',
                        key: 'id',
                        width: 90
                    },
                    {
                        title: 'Key',
                        align: 'center',
                        key: 'key'
                    },
                    {
                        title: '应用名称',
                        align: 'center',
                        key: 'name',
                        width: 150
                    },
                    {
                        title: '分组',
                        align: 'center',
                        key: 'group'
                    },
                    {
                        title: '状态',
                        align: 'center',
                        key: 'state',
                        render: (h, params) => {
                            const row = params.row;
                            var color, text;
                            if (row.state == 0) {
                                color = 'grey';
                                text = '未上线';
                            } else if (row.state == 1) {
                                color = 'green';
                                text = '正常';
                            } else if (row.state == -1) {
                                color = 'red';
                                text = '异常';
                            } else if (row.state == 2) {
                                color = 'yellow';
                                text = '告警';
                            }

                            return h('Tag', {
                                props: {
                                    type: 'dot',
                                    color: color
                                }
                            }, text);
                        }
                    },
                    {
                        title: '负责人',
                        align: 'center',
                        key: 'chargeMan'
                    },
                    {
                        title: '操作',
                        align: 'center',
                        width: 280,
                        key: 'handle',
                        handle: ['edit', 'delete', 'view']
                    }
                ],
                applicationData: [],
                rowInfoTitle: '应用详情',
                rowInfoWidth: 1200
            };
        },
        methods: {
            getData () {
                var _this = this;
                this.$$api_application_getAll({
                    data: {},
                    fn: data => {
                        _this.applicationData = data;
                    },
                    errFn: err => {
                        console.log(err);
                    }
                });
                /*this.editInlineColumns = tableData.editInlineColumns;
                this.editInlineData = tableData.editInlineData;*/
            },
            handleNetConnect (state) {
                this.breakConnect = state;
            },
            handleLowSpeed (state) {
                this.lowNetSpeed = state;
            },
            getCurrentData () {
                this.showCurrentTableData = true;
            },
            handleDel (val, index) {
                this.$Message.success('删除了第' + (index + 1) + '行数据');
            },
            handleCellChange (val, index, key) {
                this.$Message.success('修改了第 ' + (index + 1) + ' 行列名为 ' + key + ' 的数据');
            },
            handleChange (val, index) {
                this.$Message.success('修改了第' + (index + 1) + '行数据');
            },
            handleRowChange(val, index) {
                this.$Message.success('修改了第' + (index + 1) + '行数据');
            }
        },
        created () {
            this.getData();
        }
    };
</script>

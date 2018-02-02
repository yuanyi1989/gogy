<template>
    <div>
        <p></p>
        <Card :bordered="true">
            <div slot="title">
                <Select v-model="currentBranch" style="width:200px">
                    <OptionGroup label="分支">
                        <Option v-for="item in branchList" :value="item" :key="item">{{ item }}</Option>
                    </OptionGroup>
                    <OptionGroup label="标签">
                        <Option v-for="item in tagList" :value="item" :key="item">{{ item }}</Option>
                    </OptionGroup>
                </Select>
                <Button style="margin-left:20px" @click="newBuild" type="primary" icon="plus-round">Build</Button>
            </div>
            <div slot="extra">
            </div>
            <Table border :columns="columns" :data="tableData"></Table>
            <div style="text-align: center; margin-top:30px;">
                <Page placement="top" :total="searchData.pageData.total" :page-size="searchData.pageData.pageSize" show-sizer
                      @on-change="selectNewPage"
                      @on-page-size-change="changePageSize"></Page>
            </div>
        </Card>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                searchData: {
                    name: '',
                    group:'',
                    pageData: {
                        pageSize:20,
                        pageNo:0,
                        total:0
                    }

                },
                packagePath:'',
                currentBranch:'',
                branchList: [],
                tagList: [],
                tableData: [],
                columns: [
                    {
                        title: '构建编号',
                        key: 'id'
                    },
                    {
                        title: '构建版本',
                        key: 'buildVersion'
                    },
                    {
                        title: '状态',
                        key: 'success',
                        render: (h, params) => {
                            const row = params.row;
                            let isSuccess = row.success;
                            return h('Tag', {
                                props: {
                                    type: 'dot',
                                    color: isSuccess? 'green' : 'gray'
                                }
                            }, isSuccess? '成功' : '失败');
                        }
                    },{
                        title: '操作',
                        key: 'operator',
                        render: (h, params) => {
                            let children = [];
                            children.push(viewLogButton(this, h));
                            children.push(downloadButton(this, h));
                            return h('div', children);

                        }
                    }
                ]
            }
        },
        created () {
            var params = this.$router.currentRoute.params;
            this.loadData(params.application_key);
            this.loadBranch(params.application_key);
            this.loadTag(params.application_key);
        },
        methods: {
            newBuild () {
                var params = this.$router.currentRoute.params
                var _this = this;
                this.$$api_application_build({
                    pathParams: {
                        applicationKey: params.application_key
                    },
                    data: {
                        branch: _this.currentBranch
                    },
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    fn: data => {
                        console.log(data);
                        //_this.tableData = data.data;
                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            },
            loadData (application_key) {
                var _this = this;
                this.$$api_application_getAllBuilds({
                    pathParams: {applicationKey: application_key},
                    data: {
                        pageNo: _this.searchData.pageData.pageNo,
                        pageSize: _this.searchData.pageData.pageSize,
                    },
                    fn: data => {
                        console.log(data);
                        _this.tableData = data;
                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            },
            loadBranch (application_key) {
                let _this = this;
                this.$$api_application_getAllBranches({
                    pathParams: {applicationKey: application_key},
                    data: { },
                    fn: data => {
                        console.log(data);
                        _this.branchList = data;
                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: false
                });

            },
            loadTag (application_key) {
                let _this = this;
                this.$$api_application_getAllTags({
                    pathParams: {applicationKey: application_key},
                    data: { },
                    fn: data => {
                        console.log(data);
                        _this.tagList = data;
                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: false
                });

            },
            selectNewPage(pageIndex) {
                this.searchData.pageData.currentPage = pageIndex-1;
                this.loadData(this.currentEnvKey);
            },
            changePageSize(currentPageSize) {
                this.searchData.pageData.pageSize = currentPageSize;
                this.selectNewPage(1);
            }
        }
    }
    const downloadButton = (vm, h) => {
        return h('Button', {
            style: {
                margin: '0 5px'
            },
            props: {
                type: 'primary',
                placement: 'top'
            }
        }, '下载');
    };
    const viewLogButton = (vm, h) => {
        return h('Button', {
            style: {
                margin: '0 5px'
            },
            props: {
                type: 'primary',
                placement: 'top'
            }
        }, '构建日志');
    };
</script>
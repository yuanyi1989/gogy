<style lang="less">
    @import '../../styles/common.less';
    @import './application.less';
</style>
<template>
    <div>
        <Card>
        <!-- title才能撑开头部空间 -->
            <div style="height:30px;" slot="title"></div>
            <div slot="extra">
                <Select @on-change="searchTypeChange" v-model="searchData.type" style="width:100px">
                    <Option value="api" key="api">接口</Option>
                    <Option value="web" key="web">web系统</Option>
                </Select>
                <AutoComplete
                        v-model="searchData.key"
                        icon="ios-search"
                        placeholder="input any keyword you want to search..."
                        style="margin-left:7px; width:260px"
                        :filter-method="filterKeyData"
                        :data="keyData"
                        @on-search="searchKeyChange">
                </AutoComplete>
                <Button style="margin-left:20px" @on-click="newApplication" type="primary" icon="plus-round">New Application</Button>
            </div>
            <div>
                <Row v-on:click.native="handleRowClick" class="application-list-row" :application_id="item.id" :key="item.id" align="middle" v-for="item in items">
                    <Col class="application-list-col" :application_id="item.id" span="8">{{ item.name }}</Col>
                    <Col class="application-list-col light" :application_id="item.id" span="8">{{ item.key }}</Col>
                    <Col class="application-list-col" :application_id="item.id" span="4">{{ item.chargeMan || '暂缺' }}</Col>
                    <Col class="application-list-col" :application_id="item.id" span="4" style="background-color:cadetblue">
                        <div>
                            <Tag type="dot" :color="item.state == 0 ? 'gray' : item.state == 1 ? 'green' : 'red'">{{ item.state == 0 ?'未上线': item.state == 1 ? '正常' : '异常' }}</Tag>
                        </div>
                    </Col>
                </Row>
            </div>
            <div style="text-align: center; margin-top:30px;">
                <Page placement="top" :total="pageData.total" :page-size="pageData.pageSize" show-sizer
                      @on-change="selectNewPage"
                      @on-page-size-change="changePageSize"></Page>
            </div>
        </Card>
    </div>
</template>

<script>
    export default {
        name: 'application-list',
        data () {
            return {
                items: [],
                pageData: {
                    pageSize:10,
                    currentPage:0,
                    total:0
                },
                searchData: {
                    type:'api',
                    key:''
                },
                keyData: ['Steve Jobs', 'Stephen Gary Wozniak', 'Jonathan Paul Ive']
            };
        },
        methods: {
            getData () {
                var _this = this;
                this.$$api_application_getAll({
                    data: {type: _this.searchData.type, key:_this.searchData.key, pageSize:_this.pageData.pageSize, currentPage:_this.pageData.currentPage},
                    fn: data => {
                        if (data) {
                            _this.pageData.total = data.total;
                        }
                        _this.items = data.data;
                    },
                    errFn: err => {
                        console.log(err);
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            },
            newApplication() {

            },
            searchKeyChange(searchKey) {
                this.searchData.key = searchKey;
                this.selectNewPage(1);
            },
            searchTypeChange(option) {
                this.searchData.type = option;
                this.selectNewPage(1);
            },
            filterKeyData(value, option) {
                return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
            },
            selectNewPage(pageIndex) {
                this.pageData.currentPage = pageIndex-1;
                this.getData();
            },
            changePageSize(currentPageSize) {
                this.pageData.pageSize = currentPageSize;
                this.selectNewPage(1);
            },
            handleRowClick(event) {
                event.preventDefault();
                event.stopPropagation();
                var application_id = event.currentTarget.getAttribute("application_id");
                this.$router.push({
                    name: 'applicationRouter',
                    params: {
                        application_id: application_id
                    }
                });
            }
        },
        created () {
            this.getData();
        }
    }
</script>
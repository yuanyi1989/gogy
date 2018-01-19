<style lang="less">
    @import '../../styles/common.less';
    @import './application.less';
</style>
<template>
    <div>
        <Scroll :on-reach-bottom="handleReachBottom" height="830" loading-text="加载中……">
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
        </Scroll>
    </div>
</template>

<script>
    export default {
        name: 'application-list',
        props: {
            type: String
        },
        data () {
            return {
                items: [],
                pageSize: 10,
                currentPage:0
            };
        },
        methods: {
            getData () {
                var _this = this;
                this.$$api_application_getAll({
                    data: {type: _this.type, pageSize:_this.pageSize, currentPage:_this.currentPage},
                    fn: data => {
                        if (data && data.length > 0) {
                            _this.currentPage++;
                            for (var i=0; i<data.length; i++) {
                                _this.items.push(data[i]);
                            }
                        } else {

                            _this.$Message.info({
                                showClose: false,
                                content: '到底啦！拉不动了……',
                                duration: 2
                            });
                        }
                    },
                    errFn: err => {
                        console.log(err);
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            },
            handleReachBottom () {
                var _this = this;
                return new Promise(resolve => {
                    setTimeout(() => {
                        _this.getData();
                        resolve();
                    }, 200);
                });
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
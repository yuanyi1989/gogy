<style>
    @import './application.less';
</style>
<template>
    <div>
        <Row :gutter="10">
            <Col :md="24" :lg="5">
            <Card class="application_info_base">
                <p slot="title">
                    <Icon type="ios-book"></Icon>
                    基本信息
                </p>
                <a href="#" slot="extra">
                    <Icon type="ios-loop-strong"></Icon>
                    更新
                </a>
                <ul>
                    <li>
                        <span style="color:#464c5b; font-size:16px;">应用名称</span>
                        <span style="float:right; color:#ffac2d;">{{ application.name }}&nbsp;</span>
                    </li>
                    <li>
                        <span style="color:#464c5b; font-size:16px;">Key</span>
                        <span style="float:right; color:#ffac2d;">{{ application.key }}&nbsp;</span>
                    </li>
                    <li>
                        <span style="color:#464c5b; font-size:16px;">构建方式</span>
                        <span style="float:right; color:#ffac2d;"><Tag color="green">{{ application.buildType }}</Tag></span>
                    </li>
                    <li>
                        <span style="color:#464c5b; font-size:16px;">负责人</span>
                        <span style="float:right; color:#ffac2d;"><a href="item.url" target="_blank">{{ application.chargeMan }}&nbsp;</a></span>
                    </li>
                </ul>
            </Card>
            </Col>
            <Col :md="24" :lg="19">
            <Card class="application_info_base" style="width:100%">
                <p slot="title">
                    <Icon type="ios-gear"></Icon>
                    最后构建
                </p>
                <a href="#" slot="extra">
                    <Icon type="ios-loop-strong"></Icon>
                    更新
                </a>
                <Card>
                    <p slot="title">
                        <Icon type="clock"></Icon> <span style="color:indianred;">2018-01-12 12:59:32</span> 由 <span style="color:yellowgreen;">定时任务</span> 触发
                    </p>
                    <Steps :current="4">
                        <Step title="开始"></Step>
                        <Step title="构建"></Step>
                        <Step title="部署测试环境"></Step>
                        <Step title="测试环境验证"></Step>
                        <Step title="部署预发布环境"></Step>
                        <Step title="预发布环境验证"></Step>
                        <Step title="部署正式环境"></Step>
                        <Step title="正式环境验证"></Step>
                    </Steps>
                </Card>
            </Card>
            </Col>
        </Row>

    </div>
</template>

<script>
    export default {
        name: 'application-info',
        data () {
            return {
                application: {
                    type: '',
                    name: '',
                    key: '',
                    repository:'',
                    buildType:'',
                    chargeMan:'',
                    description:''
                }
            };
        },
        methods: {
            getData (id) {
                var _this = this;
                this.$$api_application_getById({
                    data: {},
                    pathParams: {id: id},
                    fn: data => {
                        _this.application = data;
                    },
                    errFn: err => {
                        console.log(err);
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            }
        },
        created () {
            var params = this.$router.currentRoute.params;
            this.getData(params.application_key);
        }
    }
</script>
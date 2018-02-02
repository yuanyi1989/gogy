<style lang="less">
    @import './machine.less';
</style>
<template>
    <div id="machine-content">
        <Row class="machine-row" v-for="(item, index) in env" :key="item.key" v-if="(index+1) % 3 == 0">
            <Col class="machine-col" v-for="(item1, index1) in env" :key="item1.key" v-if="index1 <= index && index1 > index-3" span="8" >
                <Card :bordered="true">
                    <p class="envName" @click="handleClick(item1.key)" slot="title">{{ item1.name }}</p>
                    <ul>
                        <li>
                            <span style="color:#464c5b; font-size:16px;">key</span>
                            <span style="float:right; color:#ffac2d;">{{ item1.key }}&nbsp;</span>
                        </li>
                        <li>
                            <span style="color:green; font-size:16px;">正常</span>
                            <span style="float:right; color:green;"><Tag type="dot" color="green">{{ item1.working }}</Tag></span>
                        </li>
                        <li>
                            <span style="color:red; font-size:16px;">异常</span>
                            <span style="float:right; color:red;"><Tag type="dot" color="red">{{ item1.down }}</Tag></span>
                        </li>
                        <li>
                            <span style="color:#464c5b; font-size:16px;">共计</span>
                            <span style="float:right; color:blue;"><Tag type="dot" color="blue">{{ item1.total }}</Tag></span>
                        </li>
                    </ul>
                </Card>
            </Col>
        </Row>
        <Row class="machine-row" v-if="env.length % 3 != 0">
            <Col class="machine-col" v-for="(item1, index1) in env" :key="item1.key" v-if="(index1+1) > (parseInt(env.length / 3) * 3)" span="8">
            <Card :bordered="true">
                <p class="envName" @click="handleClick(item1.key)" slot="title">{{ item1.name }}</p>
                <ul>
                    <li>
                        <span style="color:#464c5b; font-size:16px;">key</span>
                        <span style="float:right; color:#ffac2d;">{{ item1.key }}&nbsp;</span>
                    </li>
                    <li>
                        <span style="color:green; font-size:16px;">正常</span>
                        <span style="float:right; color:green;"><Tag @click='handleClick' type="dot" color="green">{{ item1.working }}</Tag></span>
                    </li>
                    <li>
                        <span style="color:red; font-size:16px;">异常</span>
                        <span style="float:right; color:red;"><Tag type="dot" color="red">{{ item1.down }}</Tag></span>
                    </li>
                    <li>
                        <span style="color:#464c5b; font-size:16px;">共计</span>
                        <span style="float:right; color:blue;"><Tag type="dot" color="blue">{{ item1.total }}</Tag></span>
                    </li>
                </ul>
            </Card>
            </Col>
        </Row>
    </div>
</template>
<script>
    export default {
        data () {
            return {
                env: []
            }
        },
        created () {
            this.loadData();
        },
        methods: {
            handleClick (key) {
                this.$router.push({
                    name: "machines",
                    params: {
                        envKey: key
                    }
                });
            },
            loadData () {
                var _this = this;
                this.$$api_machine_getEnv({
                    data: {},
                    fn: data => {
                        console.log(data);
                        _this.env = data;
                    },
                    errFn: err => {
                        console.log(err);
                        this.$Message.warning("网络连接异常.....");
                    },
                    tokenFlag: true,
                    spinFlag: false
                });
            },
        }
    }
</script>
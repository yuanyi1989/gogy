<template>
    <div>
        <Row>
            <Col span="10">
                <!-- 主要信息 -->
            <Form :model="application" :label-width="80">
                <FormItem label="应用类型">
                    <Select v-model="application.type">
                        <Option value="api">接口API</Option>
                        <Option value="webApp">web系统</Option>
                    </Select>
                </FormItem>
                <FormItem label="名称">
                    <Input v-model="application.name" placeholder="输入应用中文名..."></Input>
                </FormItem>
                <FormItem label="Key">
                    <Input v-model="application.key" placeholder="输入应用组内唯一标识..."></Input>
                </FormItem>
                <FormItem label="Git Url">
                    <Input v-model="application.repository" placeholder="代码仓库地址..."></Input>
                </FormItem>
                <FormItem label="构建方式">
                    <RadioGroup v-model="application.buildType">
                        <Radio label="maven">Maven</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="负责人">
                    <Input v-model="application.chargeMan" placeholder="负责人..."></Input>
                </FormItem>
                <FormItem label="描述">
                    <Input v-model="application.description" type="textarea" :autosize="{minRows: 3,maxRows: 5}" placeholder="写点什么吧 (: "></Input>
                </FormItem>
            </Form>
            </Col>
            <Col>
            <!-- 状态区 -->


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
                    input: '',
                    select: '',
                    radio: 'male',
                    checkbox: [],
                    switch: true,
                    date: '',
                    time: '',
                    slider: [20, 50],
                    textarea: '',
                    type: '',
                    name: '',
                    key: ''
                },
                offenUsedClass: [],
                offenUsedClassSelected: [],
                classificationList: []
            };
        }
    }
</script>
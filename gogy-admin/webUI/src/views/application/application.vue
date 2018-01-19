<style lang="less">
    @import './application.less';
</style>

<template>
    <div class="application-tabs">
        <Tabs class="margin-top-20, margin-left-10" value="api">
            <TabPane label="接口" name="api"><application-list type="api"></application-list></TabPane>
            <TabPane label="web系统" name="web"><application-list type="web"></application-list></TabPane>
        </Tabs>
    </div>
</template>

<script>
    import applicationList from './applicationList.vue';
    import canEditTable from '../tables/components/canEditTable.vue';
    import tableData from '../tables/components/table_data.js';
    import applicationInfo from './applicationInfo.vue';
    export default {
        name: 'application-table',
        components: {
            applicationList
        },
        data () {
            return {

            };
        }
    };
</script>

<style lang="less">
    @import './application.less';
</style>

<template>
    <div class="application-tabs">
        <application-list type="api"></application-list>
    </div>
</template>

<script>
    import applicationList from './applicationList.vue';
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

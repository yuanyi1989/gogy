<template>
    <div>
    <Menu mode="horizontal" theme="light" active-name="application_base" @on-select="changeMenu">
        <MenuItem name="application_base">
            <Icon type="ios-speedometer"></Icon>
            概览
        </MenuItem>
        <MenuItem name="application_pipeline">
            <Icon type="ios-settings"></Icon>
            流水线
        </MenuItem>
        <MenuItem name="application_build">
            <Icon type="ios-gear-outline"></Icon>
            构建
        </MenuItem>
        <MenuItem name="application_deploy">
            <Icon type="aperture"></Icon>
            部署
        </MenuItem>
        <MenuItem name="application_setting">
            <Icon type="settings"></Icon>
            设置
        </MenuItem>
    </Menu>
    <br>

    <router-view></router-view>
    </div>
</template>
<script>
    export default {
        data () {
            return {}
        },
        methods: {
            changeMenu (name) {
                this.$router.push({
                    name: name
                });
            }
        }
    }
</script>
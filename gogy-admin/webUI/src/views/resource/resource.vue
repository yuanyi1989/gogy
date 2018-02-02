<template>
    <div>
        <Menu mode="horizontal" theme="light" active-name="machine" @on-select="changeMenu">
            <MenuItem name="machine">
                <Icon type="ios-speedometer"></Icon>
                服务器资源
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
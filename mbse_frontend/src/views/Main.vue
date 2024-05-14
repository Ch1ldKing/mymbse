<template>
    <div>
        <el-container>
            <el-container>
                <el-header>
                    <common-header />
                </el-header>
                
                <el-main>
                    <!-- 路由出口 -->
                    <!-- 路由匹配到的组件将渲染在这里 -->
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script>

import CommonHeader from '../components/CommonHeader.vue';

export default {
    data() {
        return {}
    },
    components: {
        CommonHeader,
    }
}
</script>
<style scoped>
.el-header {
    padding: 0;
}
</style>
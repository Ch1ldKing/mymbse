<template>
  <el-form
    label-width="70px"
    :inline="true"
    class="login-container"
  >
    <h3 class="login_title">Project List</h3>
    <ul class="project-list">
    <li v-for="project in projects" :key="project.id" class="project-item">
      <el-link class="project-link"  type="primary"  @click="goToProject(project)">{{ project.name }}</el-link>
    </li>
  </ul>
  
  </el-form>
</template>
<script>
import { getProjects, getFileTree} from "../api";
import {mapMutations} from 'vuex'

export default {
  data() {
    return {
      projects: []
    };
  },
  mounted() {
        // 页面加载完成后，向后端发送请求获取项目信息
        this.fetchProjects();
  },
  methods: {
    ...mapMutations(['setLogged', 'setFileTree' , 'setCurrentProject']),
    fetchProjects() {
          getProjects().then(({ data }) => {
            console.log(data);
       
              this.projects = data;
            
          });
        },
    // 跳转到项目主页面
    goToProject(project) {
      this.setLogged(true);
      this.setCurrentProject(project);
      getFileTree(project.id).then(({ data }) => {
        console.log(data);

          this.setFileTree(data);

      });
      this.$router.push('/home');
    }
  },
};
</script>
<style lang="less" scoped>
.login-container {
  width: 500px;
  border: 1px solid #eaeaea;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 0 15px #cac6c6;
  box-sizing: border-box;
  .login_title {
    text-align: center;
    margin-bottom: 40px;
    color: #505458;
  }
  .el-input {
    width: 198px;
  }
}
.project-link {
    display: block;
    text-align: center;
    color: #333; /* 文字颜色 */
    text-decoration: none; /* 去除下划线 */
  }
  .project-list {
    list-style-type: none;
    padding: 0;
    margin: 0;
  }
  .project-item {
    background-color: #CCE4FF; /* 背景颜色 */
    border-radius: 5px; /* 圆角边框 */
    margin-bottom: 10px; /* 底部间距 */
    padding: 10px; /* 内边距 */
    border: 1px solid #ddd; /* 边框 */
  }
  

</style>
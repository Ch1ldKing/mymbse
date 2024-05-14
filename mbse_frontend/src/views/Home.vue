<template>
  <el-row>
    <el-col :span="6">
      <div class="tree-container">
        <el-tree
          :data="fileTreeData"
          :props="defaultProps"
          @node-click="handleNodeClick"
          ref="treeRef"
          icon-class="el-icon-arrow-right"
          :node-key="'id'"
        >
        <span slot-scope="{ data }">
        <template v-if="data.children">
          <div v-if="data.children.length > 0">
            <i class="el-icon-folder-opened" :style="'font-size: 14px; padding: 0 5px 0 5px'" ></i>
            <span>{{ data.name }}</span>
          </div>
 
          <div v-else>
            <i class="leaf-node-line"></i>
            <i class="el-icon-folder" :style="'padding: 0 5px 0 5px'"></i> 
            <span>{{ data.name }}</span>
          </div>
        </template>
 
        <template v-else>
          <div style="margin-left: 0px;">
            <i class="leaf-node-line"></i>
            <i class="el-icon-document" :style="'padding: 0 5px 0 5px'"></i>
            <span>{{ data.name }}</span>
          </div>
        </template>
      </span>
  
        </el-tree>
      </div>
    </el-col>
    <el-col :span="18">
      <div class="grid-content bg-purple-light">
        <el-card style="height: 610px">
          <quill-editor
            v-model="content"
            ref="myQuillEditor"
            style="height: 500px"
            :options="editorOption"
          >
            <!-- 自定义toolar -->
            <div id="toolbar" slot="toolbar">
              <!-- Add a bold button -->
              <button class="ql-bold" title="加粗">Bold</button>
              <button class="ql-italic" title="斜体">Italic</button>
              <button class="ql-underline" title="下划线">underline</button>
              <button class="ql-strike" title="删除线">strike</button>
              <button class="ql-blockquote" title="引用"></button>
              <button class="ql-code-block" title="代码"></button>
              <button class="ql-header" value="1" title="标题1"></button>
              <button class="ql-header" value="2" title="标题2"></button>
              <!--Add list -->
              <button class="ql-list" value="ordered" title="有序列表"></button>
              <button class="ql-list" value="bullet" title="无序列表"></button>
              <!-- Add font size dropdown -->
              <select class="ql-header" title="段落格式">
                <option selected>段落</option>
                <option value="1">标题1</option>
                <option value="2">标题2</option>
                <option value="3">标题3</option>
                <option value="4">标题4</option>
                <option value="5">标题5</option>
                <option value="6">标题6</option>
              </select>
              <select class="ql-size" title="字体大小">
                <option value="10px">10px</option>
                <option value="12px">12px</option>
                <option value="14px">14px</option>
                <option value="16px" selected>16px</option>
                <option value="18px">18px</option>
                <option value="20px">20px</option>
              </select>
              <select class="ql-font" title="字体">
                <option value="SimSun">宋体</option>
                <option value="SimHei">黑体</option>
                <option value="Microsoft-YaHei">微软雅黑</option>
                <option value="KaiTi">楷体</option>
                <option value="FangSong">仿宋</option>
                <option value="Arial">Arial</option>
              </select>
              <!-- Add subscript and superscript buttons -->
              <select class="ql-color" value="color" title="字体颜色"></select>
              <select
                class="ql-background"
                value="background"
                title="背景颜色"
              ></select>
              <select class="ql-align" value="align" title="对齐"></select>
              <button class="ql-clean" title="还原"></button>
              <el-button
                class="el-icon-document-checked"
                style="margin-left: 50px; font-weight: bold"
                @click="updateFile"
                >保存</el-button
              >
              <!-- You can also add your own -->
            </div>
          </quill-editor>
        </el-card>
      </div></el-col
    >
  </el-row>
</template>
<script>
import { getFileById, updateFileById } from "../api";
import { Quill, quillEditor } from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
//引入font.css
import "../assets/css/font.css";

// 自定义字体大小
let Size = Quill.import("attributors/style/size");
Size.whitelist = ["10px", "12px", "14px", "16px", "18px", "20px"];
Quill.register(Size, true);

// 自定义字体类型
var fonts = [
  "SimSun",
  "SimHei",
  "Microsoft-YaHei",
  "KaiTi",
  "FangSong",
  "Arial",
  "Times-New-Roman",
  "sans-serif",
  "宋体",
  "黑体",
];
var Font = Quill.import("formats/font");
Font.whitelist = fonts;
Quill.register(Font, true);

import { mapMutations } from "vuex";
import { set } from "vue";

export default {
  components: {
    quillEditor,
  },
  data() {
    return {
      isSelectAll: false,
      defaultProps: {
        name: "name",
        children: "children",
      },
      content: null,
      editorOption: {
        placeholder: "请输入",
        theme: "snow", // or 'bubble'
        modules: {
          toolbar: {
            container: "#toolbar",
          },
        },
      },
    };
  },
  methods: {
    ...mapMutations(["setFileInfo"]),
    handleNodeClick(node) {
      if (node.file) {
        // 节点对象有一个 `file` 属性来标识是否为叶节点
        this.content = node.content;
        this.setFileInfo(node);
      }
    },
    fetchNodeFiles(nodeId) {
      getFileById(nodeId).then(({ data }) => {
        console.log(data);
        if (data.code === 20000) {
          this.content = data.data.content;
          this.setFileInfo(data.data);
        } else {
          this.$message.error(data.data.message);
        }
      });
    },
    // 根据id查找结点 父函数
    travelsalTree(node, nodeId) {
      for (let i = 0; i < node.length; i++) {
        let result = this.findNodeById(node[i], nodeId);
        if (result != null) {
          return result;
        }
      }
    },

    // 根据id查找节点 子函数
    findNodeById(node, id) {
      // 检查当前节点的id是否匹配
      if (node.id === id) {
        return node;
      }
      // 如果当前节点有子节点，遍历子节点
      if (node.children) {
        for (let child of node.children) {
          let result = this.findNodeById(child, id);
          if (result) {
            return result;
          }
        }
      }
      // 如果没有找到匹配的节点，返回null
      return null;
    },

    updateFile() {

      this.travelsalTree( this.$store.state.tab.fileTree, this.$store.state.tab.fileInfo.id).content = this.content;
      updateFileById(this.$store.state.tab.fileInfo.id,this.content).then(({ data }) => {  
      
        this.$message.success("保存成功");
      });

    },

    /**
     * 是否全选所有节点
     */
     handleCheckedAllTreeNodeChange() {
      if (this.isSelectAll) {
        // 深度遍历将子节点全选中
        for (let i = 0; i < this.treeList.length; i++) {
          this.$refs.treeRef.setChecked(this.treeList[i], true, true);
        }
      } else {
        // 全部不选中
        this.$refs.treeRef.setCheckedNodes([]);
      }
 
      const leafOnly = true;
      this.handleGetCheckedNodesAndKeys(leafOnly);
    },
 
    /**
     * 获取当前已被选中的节点集合
     */
    handleGetCheckedNodesAndKeys(leafOnly) {
      console.log('getCheckedNodes =>', this.$refs.treeRef.getCheckedNodes(leafOnly));
      console.log('getCheckedKeys =>', this.$refs.treeRef.getCheckedKeys(leafOnly));
    }
  },
  computed: {
    fileTreeData() {
      return this.$store.state.tab.fileTree;
    },
  },
};
</script>
<style lang="less" scoped>
.user {
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  img {
    margin-right: 40px;
    width: 150px;
    height: 150px;
    border-radius: 50%;
  }
  .userinfo {
    .name {
      font-size: 32px;
      margin-bottom: 10px;
    }
    .access {
      color: #999999;
    }
  }
}

.login-info {
  p {
    line-height: 28px;
    font-size: 14px;
    color: #999999;
    span {
      color: #666666;
      margin-left: 60px;
    }
  }
}
.num {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  .icon {
    width: 80px;
    height: 80px;
    font-size: 30px;
    text-align: center;
    line-height: 80px;
    color: #fff;
  }
  .detail {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-left: 15px;
    .price {
      font-size: 30px;
      margin-bottom: 10px;
      line-height: 30px;
      height: 30px;
    }
    .desc {
      font-size: 14px;
      color: #999;
      text-align: center;
    }
  }
  .el-card {
    width: 32%;
    margin-bottom: 20px;
  }
}
.graph {
  display: flex;
  margin-top: 20px;
  justify-content: space-between;

  .el-card {
    width: 48%;
  }
}
</style>

<style lang="less" scoped>
// 设置树形组件节点的定位和左内边距
.tree-container /deep/ .el-tree-node {
  position: relative;
  padding-left: 13px;
}

// 设置树形组件节点的 before 伪类的样式
.tree-container /deep/ .el-tree-node:before {
  width: 1px;
  height: 100%;
  content: "";
  position: absolute;
  top: -38px;
  bottom: 0;
  left: 0;
  right: auto;
  border-width: 1px;
  border-left: 1px solid #b8b9bb;
}

// 设置树形组件节点的 after 伪类的样式
.tree-container /deep/ .el-tree-node:after {
  width: 13px;
  height: 13px;
  content: "";
  position: absolute;
  left: 0;
  right: auto;
  top: 12px;
  bottom: auto;
  border-width: 1px;
  border-top: 1px solid #b8b9bb;
}

// 设置树形组件首节点的左边框不显示
.tree-container /deep/ .el-tree > .el-tree-node:before {
  border-left: none;
}

// 设置树形组件首节点的顶部边框不显示
.tree-container /deep/ .el-tree > .el-tree-node:after {
  border-top: none;
}

// 设置树形组件末节点的 before 伪类的高度
.tree-container /deep/ .el-tree .el-tree-node:last-child:before {
  height: 50px;
}

// 设置树形组件节点字体大小、以及取消左内边距
.tree-container /deep/ .el-tree .el-tree-node__content {
  color: #000;
  font-size: 14px;
  padding-left: 0 !important;
}

// 设置树形组件孩子节点左内边距
.tree-container /deep/ .el-tree .el-tree-node__children {
  padding-left: 11.5px;
}

// 设置树形组件复选框左右外边距
.tree-container /deep/ .el-tree .el-tree-node__content > name.el-checkbox {
  margin: 0 5px 0 5px !important;
}

// 设置树形组件展开图标定位、图层、内边距
.tree-container /deep/ .el-tree .el-tree-node__expand-icon {
  position: relative;
  z-index: 99;
}

// 设置树形组件叶子节点的默认图标不显示
.tree-container /deep/ .el-tree .el-tree-node__expand-icon.is-leaf {
  display: none;
}

// 设置树形组件叶子节点的横线
.tree-container /deep/ .el-tree .leaf-node-line {
  width: 23px;
  height: 13px;
  content: "";
  position: absolute;
  left: 13px;
  right: auto;
  top: 12px;
  bottom: auto;
  border-width: 1px;
  border-top: 1px solid #b8b9bb;
}

// 设置树形组件有叶子节点的左外边距
.tree-container /deep/ .el-tree .el-tree-node__content:has(.is-leaf) {
  // color: aqua;
  margin-left: 24px !important;
}
</style>

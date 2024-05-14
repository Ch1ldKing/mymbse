<template>
  <div class="bodys">
    <!-- 标题栏 -->
    <div class="titles">
      <!-- 图标 -->
      <div><i class="el-icon-s-management"></i></div>
      <!-- 标题文字 -->
      <div>{{ title }}</div>
    </div>

    <!-- 主体部分 -->
    <div class="mains">
      <!-- 标签页 -->
      <el-tabs tab-position="left" @tab-click="tabClick">
        <!-- 文件分类信息 -->
        <el-tab-pane>
          <span slot="label"
            ><i class="el-icon-folder-opened"></i> 文件分类信息</span
          >
          <!-- 分类信息主体部分 -->
          <div>
            <!-- 顶部功能操作区 -->
            <div class="typeDos">
              <div>
                <el-input
                  v-model="typeQueryInfo.content"
                  placeholder="全局搜索"
                  prefix-icon="el-icon-search"
                  size="medium"
                  clearable
                ></el-input>
              </div>
              <div>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="medium"
                  @click="queryTypeInfo"
                  >查询</el-button
                >
              </div>
              <div>
                <el-button
                  type="warning"
                  icon="el-icon-refresh"
                  size="medium"
                  @click="resetQueryType"
                  >重置</el-button
                >
              </div>
            </div>
            <!-- 数据展示部分 -->
            <div class="typeTables">
              <!-- 数据表格 -->
              <el-table
                :data="typeList"
                v-loading="typeLoading"
                element-loading-text="加载中..."
              >
                <el-table-column
                  label="主键"
                  align="center"
                  prop="id"
                ></el-table-column>
                <el-table-column
                  label="分类名称"
                  align="center"
                  prop="name"
                ></el-table-column>
                <el-table-column label="创建时间" align="center">
                  <template slot-scope="scope">
                    {{ formatDate(scope.row.uploadTime) }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                  <template slot-scope="scope">
                    <div class="tableBtns">
                      <div>
                        <el-button
                          size="small"
                          icon="el-icon-edit"
                          @click="openUpdateType(scope.row)"
                          >修改</el-button
                        >
                      </div>
                      <div>
                        <el-popconfirm
                          :title="'确定要删除：' + scope.row.name + ' 吗？'"
                          @confirm="delType(scope.row)"
                        >
                          <el-button
                            slot="reference"
                            type="danger"
                            size="small"
                            icon="el-icon-delete"
                            >删除</el-button
                          >
                        </el-popconfirm>
                      </div>
                    </div>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 数据分页 -->
              <div class="typePages">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="typePage.pageNumber"
                  :page-sizes="[10, 20, 30, 40]"
                  :total="typePage.pageTotal"
                  layout="jumper, prev, pager, next, sizes, total"
                >
                </el-pagination>
              </div>
            </div>
          </div>
        </el-tab-pane>

        <!-- 文件信息 -->
        <el-tab-pane>
          <span slot="label"><i class="el-icon-folder"></i>需求链接</span>
          <!-- 文件信息上传对话框 -->
          <div class="center-container">
            <!-- 上传表单 -->
            <el-form
              v-loading="uploadFileLoading.loading"
              :element-loading-text="
                '上传中：' + uploadFileLoading.progress + '%'
              "
            >
              <el-form-item label="选择两个文件：">
                <el-upload
                  class="upload-demo"
                  action=""
                  :show-file-list="false"
                  :before-upload="chooseUploadFile"
                  drag
                  multiple
                >
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">
                    将文件拖到此处，或 <em>点击上传</em>
                  </div>
                </el-upload>
              </el-form-item>
              <el-form-item label="选中的文件：">
                <div>
                  {{
                    uploadFileInfo.length == 3
                      ? uploadFileInfo[1].file.name +
                        " " +
                        uploadFileInfo[2].file.name
                      : "未选择足够文件"
                  }}
                </div>
              </el-form-item>
              <el-form-item>
                <div class="doingFormBtns">
                  <div>
                    <el-button
                      size="medium"
                      type="primary"
                      icon="el-icon-position"
                      @click="uploadFile"
                      >提交</el-button
                    >
                  </div>
                  <div>
                    <el-button
                      size="medium"
                      icon="el-icon-close"
                      @click="closeUploadFile"
                      >关闭</el-button
                    >
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 文件分类信息修改对话框 -->
    <div>
      <el-dialog
        title="修改文件信息"
        center
        :visible.sync="showTypeUpdateFrom"
        :close-on-press-escape="false"
        :close-on-click-modal="false"
        :show-close="false"
      >
        <!-- 操作表单 -->
        <el-form>
          <el-form-item label="文件名称：">
            <el-input
              v-model="updateTypeInfo.name"
              placeholder="请输入修改后文件名称"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div class="doingFormBtns">
              <div>
                <el-button
                  size="medium"
                  type="primary"
                  icon="el-icon-position"
                  @click="updateType"
                  :loading="updateTypeLoading"
                  >提交</el-button
                >
              </div>
              <div>
                <el-button
                  size="medium"
                  icon="el-icon-close"
                  @click="closeUpdateType"
                  >关闭</el-button
                >
              </div>
            </div>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
// 依赖引入
// axios
import axios from "axios";
// qs
import qs from "qs";
// js-base64
import { Base64 } from "js-base64";
import { getTypeFiles, sendToServer, getSpecialFiles } from "../api";
import { MessageBox } from "element-ui";
import { Loading } from "element-ui";

export default {
  name: "FilesIndex",
  data() {
    return {
      // 页面标题
      title: "文件管理",
      // 接口服务地址
      baseUrl: "https://kangxianghui.top",
      // ======== 分类信息部分 ========
      // 分类信息查询条件
      typeQueryInfo: {
        content: "",
      },
      // 分类信息查询加载
      typeLoading: false,
      // 分类信息数据集合
      typeList: [],
      // 分类信息数据分页
      typePage: {},
      // 是否打开分类信息添加对话框
      showTypeAddFrom: false,
      // 分类信息添加信息对象
      addTypeInfo: {
        name: "",
      },
      // 分类信息添加加载
      addTypeLoading: false,
      // 是否打开分类信息修改对话框
      showTypeUpdateFrom: false,
      // 分类信息修改信息对象
      updateTypeInfo: {},
      // 分类信息修改加载
      updateTypeLoading: false,
      // 分类信息删除加载
      delTypeLoading: false,
      // ======== 文件信息部分 ========
      fileSize: 10 * 1000 * 1024,
      fileQueryInfo: {
        fileInfo: {
          ftiid: "",
          info: "",
        },
        page: {
          pageNumber: 1,
          pageSize: 25,
        },
      },
      // 文件信息加载
      fileLoading: false,
      // 文件信息滚动查询加载
      fileScrollLoading: false,
      // 文件分类信息加载
      fileTypeLoading: false,
      // 文件分类信息数据集合
      fileTypeList: [],
      // 文件信息数据集合
      fileList: [],
      // 文件信息数据分页
      filePage: {},
      // 文件信息数据是否全部加载完毕
      isNfm: false,
      // 是否打开上传文件对话框
      showFileUploadFrom: false,
      // 文件上传信息对象
      uploadFileInfo: [
        {
          file: null,
          fileInfo: {
            ftiid: "",
            info: "",
          },
        },
      ],
      // 文件上传反馈信息对象
      uploadFileLoading: {
        loading: false,
        progress: 0,
      },
      // 是否打开修改文件对话框
      showFileUpdateFrom: false,
      // 文件修改信息对象
      updateFileInfo: {
        file: null,
        fileInfo: {},
      },
      // 文件修改反馈信息对象
      updateFileLoading: {
        loading: false,
        progress: 0,
      },
      // 删除文件加载
      delFileLoading: false,
      // 是否打开右击功能卡片
      showDoingCard: false,
      // 文件信息存储对象
      doingInfo: {},
      // 右键菜单样式变量
      updateDialogTop: 0,
      updateDialogLeft: 0,
      // 是否打开文件详情对话框
      showFileDetail: false,
      // 文件详情信息对象
      fileDetail: {},
    };
  },
  methods: {
    // 修改文件表单选择文件组件触发的函数
    chooseUpdateFile(file) {
      this.updateFileInfo.file = file;
      return false;
    },
    // 修改文件
    updateFile() {
      if (this.updateFileInfo.fileInfo.ftiid == "") {
        this.showMessage(false, "请选择分类名称");
        return;
      }
      if (this.updateFileInfo.fileInfo.info == "") {
        this.showMessage(false, "文件描述必须填写");
        return;
      }
      if (this.updateFileInfo.file == null) {
        this.showMessage(false, "必须选择一个文件");
        return;
      }
      if (this.updateFileInfo.file.size > this.fileSize) {
        this.showMessage(false, "文件大小超过限制");
        return;
      }
      this.sendFile(
        "/karl-openapi/FileInfo/Update",
        this.updateFileInfo,
        "POST",
        this.updateFileLoading
      );
    },
    // 关闭文件修改对话框
    closeUpdateFile() {
      this.showFileUpdateFrom = false;
      this.fileTypeList = [];
      this.updateFileInfo = {
        file: null,
        fileInfo: {},
      };
      this.queryFileInfo();
    },
    // 打开文件修改对话框
    openUpdateFile() {
      this.showFileUpdateFrom = true;
      this.updateFileInfo.fileInfo = {
        fiid: this.doingInfo.fiid,
        ftiid: this.doingInfo.ftiid,
        info: this.doingInfo.info,
      };
      this.ajax(
        "/karl-openapi/FileTypeInfo/QueryAll",
        {
          fileTypeInfo: {
            name: "",
          },
          page: {
            pageNumber: 1,
            pageSize: 100,
          },
        },
        (data) => {
          this.fileTypeList = data.resultData.list;
        }
      );
      this.closeDoingCard();
    },
    // 删除文件
    delFile() {
      this.ajax(
        "/karl-openapi/FileInfo/Del",
        {
          "fileInfo.fiid": this.doingInfo.fiid,
        },
        (data) => {
          this.showMessage(data.success, data.message);
          this.closeDoingCard();
          this.queryFileInfo();
        }
      );
    },
    // 关闭文件详情对话框
    closeFileDetail() {
      this.showFileDetail = false;
      this.fileDetail = {};
      this.fileTypeList = [];
    },
    // 打开文件详情对话框
    openFileDetail() {
      this.fileDetail = this.doingInfo;
      this.ajax(
        "/karl-openapi/FileTypeInfo/QueryAll",
        {
          fileTypeInfo: {
            name: "",
          },
          page: {
            pageNumber: 1,
            pageSize: 100,
          },
        },
        (data) => {
          this.fileTypeList = data.resultData.list;
        }
      );
      this.showFileDetail = true;
      this.closeDoingCard();
    },
    // 复制文件链接
    copyOnlineUrl() {
      let input = document.createElement("input");
      input.readOnly = "readonly";
      input.value =
        this.baseUrl +
        "/karl-openapi/FileInfo/OnlineView/" +
        this.doingInfo.name;
      document.body.appendChild(input);
      input.select();
      input.setSelectionRange(0, input.value.length);
      document.execCommand("Copy");
      document.body.removeChild(input);
      this.$message.success("复制成功");
      this.closeDoingCard();
    },
    // 下载文件
    downLoadFile() {
      window.open(
        this.baseUrl +
          "/karl-openapi/FileInfo/Download?fileInfo.fiid=" +
          this.doingInfo.fiid
      );
      this.closeDoingCard();
    },
    // 关闭文件右键菜单
    closeDoingCard() {
      document.removeEventListener("click", this.outsideClickListener);
      this.doingInfo = {};
      this.showDoingCard = false;
      this.updateDialogLeft = 0;
      this.updateDialogTop = 0;
    },
    // 打开文件右键菜单
    openDoingCard(e, info) {
      this.doingInfo = info;
      this.showDoingCard = true;
      this.updateDialogLeft = e.clientX + 30;
      this.updateDialogTop = e.clientY;
      document.addEventListener("click", this.outsideClickListener);
    },
    // 元素外点击监听函数
    outsideClickListener(event) {
      let targetElement = document.getElementById("doingCard");
      let isClickedOutside = !targetElement.contains(event.target);
      if (isClickedOutside) {
        this.closeDoingCard();
      }
    },
    // 预览文件
    fileView(info) {
      window.open(
        "https://kangxianghui.top/karl-fileview/onlinePreview?url=" +
          encodeURIComponent(
            Base64.encodeURI(
              this.baseUrl + "/karl-openapi/FileInfo/OnlineView/" + info.name
            )
          )
      );
    },
    // 滚动查询文件
    fhScroll() {
      let filesBody = this.$refs.filesBody;
      let app = this;
      if (filesBody) {
        filesBody.addEventListener("scroll", function () {
          if (
            filesBody.scrollTop + filesBody.clientHeight >=
            filesBody.scrollHeight
          ) {
            if (app.isNfm) {
              return;
            }
            app.fileScrollLoading = true;
            app.fileQueryInfo.page.pageNumber += 1;
            app.ajax(
              "/karl-openapi/FileInfo/QueryAll",
              app.fileQueryInfo,
              (data) => {
                setTimeout(() => {
                  app.fileScrollLoading = false;
                  let n = 0;
                  data.resultData.list.forEach((obj) => {
                    if (
                      !app.fileList.some(function (flObj) {
                        return flObj.fiid === obj.fiid;
                      })
                    ) {
                      app.fileList.push(obj);
                      n++;
                    }
                  });
                  if (n == 0) {
                    app.isNfm = true;
                    app.$message({
                      type: "info",
                      message: "啊哦，没有更多数据了~",
                    });
                  } else {
                    app.isNfm = false;
                  }
                  app.filePage = data.resultData.page;
                }, 800);
              }
            );
          }
        });
      }
    },
    // 文件类型匹配
    resFileType(type, value) {
      return value.search(type) != -1;
    },
    // 上传文件
    uploadFile() {
      const files = this.uploadFileInfo;
      let loadingInstance = null;
      if (files.length != 3) {
        this.showMessage(false, "请选择两个文件");
        return;
      }
      const readers = [];
      for (let i = 1; i < files.length; i++) {
        readers[i] = new FileReader();
        readers[i].readAsText(files[i].file);
      }
      Promise.all(
        readers.map(
          (reader) =>
            new Promise((resolve) => {
              reader.onload = () => resolve(reader.result);
            })
        )
      ).then((contents) => {
        console.log("文件内容已读取，发送到服务器：", contents);
        loadingInstance = Loading.service({
          fullscreen: true,
          text: "加载中...",
          background: "rgba(0, 0, 0, 0.7)",
        });

        sendToServer(contents[1], contents[2])
          .then(({ data }) => {
            if (data.data == true) {
              MessageBox.alert("两个文件存在需求关联", "成功", {
                confirmButtonText: "确定",
                type: "success",
                center: true,
              });
            } else {
              MessageBox.alert("两个文件不存在需求关联", "失败", {
                confirmButtonText: "确定",
                type: "error",
                center: true,
              });
            }
          })
          .catch((error) => {
            console.error("读取文件发生错误:", error);
          })
          .finally(() => {
            if (loadingInstance) {
              // 请求完成后关闭加载动画
              loadingInstance.close();
            }
          });
      });
    },
    // 文件上传选择文件组件触发的函数
    chooseUploadFile(file) {
      console.log(this.uploadFileInfo.length);
      this.uploadFileInfo.push({
        file: file,
        fileInfo: {
          ftiid: "",
          info: "",
        },
      });
      return false;
    },
    // 关闭文件上传对话框
    closeUploadFile() {
      this.showFileUploadFrom = false;
      this.fileTypeList = [];
      this.uploadFileInfo = {
        file: null,
        fileInfo: {
          ftiid: "",
          info: "",
        },
      };
      this.queryFileInfo();
    },
    // 分类信息选择器输入值变化时触发
    queryRmFun(str) {
      this.fileTypeLoading = true;
      this.ajax(
        "/karl-openapi/FileTypeInfo/QueryAll",
        {
          fileTypeInfo: {
            name: str,
          },
          page: {
            pageNumber: 1,
            pageSize: 100,
          },
        },
        (data) => {
          this.fileTypeLoading = false;
          this.fileTypeList = data.resultData.list;
        }
      );
    },
    // 重置查询文件信息
    resetQueryFileInfo() {
      this.fileTypeList = [];
      this.fileQueryInfo = {
        fileInfo: {
          ftiid: "",
          info: "",
        },
        page: {
          pageNumber: 1,
          pageSize: 25,
        },
      };
      this.isNfm = false;
      this.queryFileInfo();
    },

    // 删除分类信息
    delType(obj) {
      if (obj.ftiid == "") {
        this.showMessage(false, "分类主键不能为空");
        return;
      }
      this.delTypeLoading = true;
      this.ajax(
        "/karl-openapi/FileTypeInfo/Del",
        {
          fileTypeInfo: {
            ftiid: obj.ftiid,
          },
        },
        (data) => {
          this.delTypeLoading = false;
          this.showMessage(data.success, data.message);
          this.queryTypeInfo();
        }
      );
    },
    // 修改分类信息
    updateType() {
      if (this.updateTypeInfo.ftiid == "") {
        this.showMessage(false, "分类主键不能为空");
        return;
      }
      if (this.updateTypeInfo.name == "") {
        this.showMessage(false, "分类名称不能为空");
        return;
      }
      this.updateTypeLoading = true;
      this.ajax(
        "/karl-openapi/FileTypeInfo/Update",
        {
          fileTypeInfo: {
            ftiid: this.updateTypeInfo.ftiid,
            name: this.updateTypeInfo.name,
          },
        },
        (data) => {
          this.updateTypeLoading = false;
          this.showMessage(data.success, data.message);
        }
      );
    },
    // 关闭分类信息修改对话框
    closeUpdateType() {
      this.showTypeUpdateFrom = false;
      this.updateTypeInfo = {};
      this.queryTypeInfo();
    },
    // 打开分类信息修改对话框
    openUpdateType(obj) {
      this.updateTypeInfo = obj;
      this.showTypeUpdateFrom = true;
    },
    // 添加分类信息
    addType() {
      if (this.addTypeInfo.name == "") {
        this.showMessage(false, "分类名称必须填写");
        return;
      }
      this.addTypeLoading = true;
      this.ajax(
        "/karl-openapi/FileTypeInfo/Add",
        {
          "fileTypeInfo.name": this.addTypeInfo.name,
        },
        (data) => {
          this.addTypeLoading = false;
          this.showMessage(data.success, data.message);
          this.queryTypeInfo();
        }
      );
    },
    // 分类信息分页大小变化时触发
    handleSizeChange(val) {
      this.typeQueryInfo.page.pageSize = val;
      this.queryTypeInfo();
    },
    // 分类信息页码变化时触发
    handleCurrentChange(val) {
      this.typeQueryInfo.page.pageNumber = val;
      this.queryTypeInfo();
    },
    // 重置分类信息查询
    resetQueryType() {
      this.typeQueryInfo = {
        fileTypeInfo: {
          name: "",
        },
        page: {
          pageNumber: 1,
          pageSize: 10,
        },
      };
      this.queryTypeInfo();
    },
    // 查询文件分类信息
    queryTypeInfo() {
      let app = this;
      this.typeLoading = true;

      if (this.typeQueryInfo.content == "") {
        getTypeFiles(this.typeQueryInfo.content).then(({ data }) => {
          console.log(data);
          app.typeLoading = false;
          app.typeList = data;
        });
      } else {
        getSpecialFiles(this.typeQueryInfo.content).then(({ data }) => {
          console.log(data);
          app.typeLoading = false;
          app.typeList = data;
        });
      }
    },
    // 标签页切换时触发的函数
    tabClick(tab) {
      switch (parseInt(tab.index)) {
        case 0:
          this.queryTypeInfo();
          break;
        case 1:
          break;
        default:
          break;
      }
    },
    /**
     * 全局消息提示
     *
     * @param success 服务器是否正确应答
     * @param message 服务器应答内容
     */
    showMessage(success, message) {
      if (success) {
        this.$message({
          type: "success",
          message: message,
        });
        return;
      }
      this.$message({
        type: "error",
        message: message,
      });
    },
    /**
     * 文件上传方法
     *
     * @param url 接口地址
     * @param params 请求参数
     * @param method 请求方式
     * @param loadingObj 反馈信息对象
     */
    sendFile(url, params, method, loadingObj) {
      loadingObj.loading = true;
      let formdata = new FormData();
      formdata.append("file", params.file);
      for (let key in params.fileInfo) {
        formdata.append("fileInfo." + key, params.fileInfo[key]);
      }
      let app = this;
      axios({
        url: app.baseUrl + url,
        data: formdata,
        method: method,
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress: (progressEvent) => {
          loadingObj.progress = parseInt(
            ((progressEvent.loaded / progressEvent.total) * 100) | 0
          );
        },
      })
        .then(function ({ data }) {
          loadingObj.progress = 0;
          loadingObj.loading = false;
          app.showMessage(data.success, data.message);
        })
        .catch(function (err) {
          loadingObj.loading = false;
          app.showMessage(false, "上传发生错误：" + err);
          console.log(err);
        });
    },
    /**
     * ajax请求方法
     *
     * @param url 接口地址
     * @param params 请求参数
     * @param cb 回调函数
     * @param method 请求方式
     */
    ajax(url, params, cb, method) {
      url = this.baseUrl + url;
      params = qs.stringify(params, { allowDots: true });
      method = method ? method : "POST";
      if (method.toUpperCase() == "GET") {
        url = url + "?" + params;
        params = null;
      }
      axios({
        url: url,
        data: params,
        method: method,
      })
        .then((resp) => {
          cb(resp.data);
        })
        .catch((err) => {
          console.log("ajax请求发生错误:{}", err);
          cb({ code: 500, message: err, success: false });
        });
    },
    /**
     * 格式化日期时间
     *
     * @param value 时间戳
     */
    formatDate(value) {
      try {
        let date = new Date(value);
        let year = date.getFullYear() + "";
        let month = date.getMonth() + 1;
        let day = date.getDate();
        let hour = date.getHours();
        let minute = date.getMinutes();
        let second = date.getSeconds();
        month = month < 10 ? "0" + month : "" + month;
        day = day < 10 ? "0" + day : "" + day;
        hour = hour < 10 ? "0" + hour : "" + hour;
        minute = minute < 10 ? "0" + minute : "" + minute;
        second = second < 10 ? "0" + second : "" + second;
        // 日期和时间的格式 - 可自定义
        // 例如：yyyy年MM月dd日 hh时mm分ss秒、yyyy年MM月dd日 hh:mm:ss等
        let format = "yyyy-MM-dd hh:mm:ss";
        format = format.replace(/yyyy/g, year);
        format = format.replace(/MM/g, month);
        format = format.replace(/dd/g, day);
        format = format.replace(/hh/g, hour);
        format = format.replace(/mm/g, minute);
        format = format.replace(/ss/g, second);
        return format;
      } catch (ex) {
        console.error(ex);
        return "";
      }
    },
    /**
     * 格式化文件大小
     *
     * @param size 文件大小（单位：kb）
     */
    formatFileSize(size) {
      if (size == null || size == "") {
        return "0B";
      }
      let unitArr = new Array(
        "B",
        "KB",
        "MB",
        "GB",
        "TB",
        "PB",
        "EB",
        "ZB",
        "YB"
      );
      let index = 0;
      let srcsize = parseFloat(size);
      index = Math.floor(Math.log(srcsize) / Math.log(1024));
      let sizes = srcsize / Math.pow(1024, index);
      sizes = sizes.toFixed(2);
      return sizes + unitArr[index];
    },
  },
  mounted() {
    this.queryTypeInfo();
    this.fhScroll();
  },
};
</script>

<style lang="less" scoped>
.doingCardStyles {
  position: fixed;
  z-index: 99999;
  width: auto;
  height: auto;
  background: #ffffff;
  border: 1px solid rgb(231, 231, 231);
  border-radius: 6px;
  > div {
    display: flex;
    cursor: pointer;
    font-size: 17px;
    letter-spacing: 2px;
    width: 8rem;
    height: 2.5rem;
    justify-content: center;
    align-items: center;
  }
  > div:hover {
    background: #ddeafb;
  }
  > div:not(:last-child) {
    border-bottom: 1px solid rgb(231, 231, 231);
  }
}

.filesBody {
  display: flex;
  justify-content: space-evenly;
  flex-wrap: wrap;
  margin: 1rem 1rem 1rem 0;
  max-height: calc((@windowHeight - @titlesHeight) / 1.1);
  border-top: 1px solid #e4e7ed;
  overflow-y: auto;
  .scrollbarStyle();
  > div {
    width: 18%;
    min-height: 100%;
    margin: 1rem 0;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    word-break: break-word;
    cursor: pointer;
    padding: 1rem 0;
    overflow: hidden;
    border-radius: 0.3rem;
    > div {
      transition: all 0.5s ease;
    }
    &:hover {
      background-color: #f8f8f8;
    }
  }
}

.fileInfoWrites {
  display: flex;
  > div:first-child {
    margin-right: 0.8rem;
  }
}

.doingFormBtns {
  display: flex;
  justify-content: flex-end;
  > div {
    .btnsstyle();
  }
}

.tableBtns {
  display: flex;
  justify-content: center;
  > div {
    .btnsstyle();
  }
}

.addTypeForms {
  display: flex;
  justify-content: flex-end;
  margin-right: @mainsMarginRight;
  margin-top: 1rem;
  > div {
    .btnsstyle();
    display: flex;
    align-items: center;
    font-size: 1rem;
  }
}

.typePages {
  margin: 1rem 0;
  text-align: right;
  margin-right: @mainsMarginRight;
  padding-bottom: 2rem;
}

.typeTables {
  margin: 2rem;
  height: calc((@windowHeight - @titlesHeight) / 1.15);
  overflow-y: auto;
  .scrollbarStyle();
}

.typeDos {
  display: flex;
  justify-content: flex-end;
  margin-right: @mainsMarginRight;
  > div {
    .btnsstyle();
  }
}

.mains {
  display: flex;
  padding: 1rem 0;
  height: calc(@windowHeight - @titlesHeight);
  .el-tabs {
    width: 100%;
  }
}

.titles {
  display: flex;
  justify-content: center;
  height: @titlesHeight;
  font-size: 1.8rem;
  font-family: "黑体";
  color: rgb(255, 255, 255);
  background-color: #7bbbe8;
  letter-spacing: 2px;
  i {
    font-size: 2.2rem;
    font-weight: 700;
  }
  > div {
    margin: 0 0.3rem;
    display: flex;
    align-items: center;
  }
}

.bodys {
  overflow: hidden;
  height: @windowHeight;
}

.btnsstyle() {
  margin: 0 0.4rem;
}

.scrollbarStyle() {
  &::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(255, 255, 255, 0);
    background-color: rgb(255, 255, 255);
  }

  &::-webkit-scrollbar {
    width: 6px;
    background-color: rgb(255, 255, 255);
  }

  &::-webkit-scrollbar-thumb {
    height: 20px;
    background-image: -webkit-gradient(
      linear,
      left bottom,
      left top,
      color-stop(0.2, #e4e7ed),
      color-stop(0.4, #e4e7ed),
      color-stop(0.8, #e4e7ed)
    );
  }
}

@mainsMarginRight: 3rem;
@titlesHeight: 90px;
@windowHeight: calc(100vh);
@windowWidth: calc(100vw);
</style>

<style>
.center-container {
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  margin-left: 300px;
  margin-top: 100px;
}
/* 自定义MessageBox样式 */
.el-message-box__header {
  color: #333; /* 标题颜色 */
  font-weight: bold; /* 标题字体加粗 */
}

.el-message-box__content {
  font-size: 16px; /* 内容字体大小 */
  color: #666; /* 内容字体颜色 */
}

.el-button--default {
  border-color: #3399ff; /* 按钮边框颜色 */
  color: #3399ff; /* 按钮文字颜色 */
}

.el-button--primary {
  background-color: #3399ff; /* 主按钮背景颜色 */
  border-color: #3399ff; /* 主按钮边框颜色 */
}
</style>

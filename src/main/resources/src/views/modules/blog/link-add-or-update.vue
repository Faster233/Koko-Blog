<template>
  <el-dialog
    :title="!dataForm.linkId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="友链类别 0-友链 1-推荐 2-个人网站" prop="linkType">
      <el-input v-model="dataForm.linkType" placeholder="友链类别 0-友链 1-推荐 2-个人网站"></el-input>
    </el-form-item>
    <el-form-item label="网站名称" prop="linkName">
      <el-input v-model="dataForm.linkName" placeholder="网站名称"></el-input>
    </el-form-item>
    <el-form-item label="网站链接" prop="linkUrl">
      <el-input v-model="dataForm.linkUrl" placeholder="网站链接"></el-input>
    </el-form-item>
    <el-form-item label="网站描述" prop="linkDescription">
      <el-input v-model="dataForm.linkDescription" placeholder="网站描述"></el-input>
    </el-form-item>
    <el-form-item label="用于列表排序" prop="linkRank">
      <el-input v-model="dataForm.linkRank" placeholder="用于列表排序"></el-input>
    </el-form-item>
    <el-form-item label="是否删除 0-未删除 1-已删除" prop="isDeleted">
      <el-input v-model="dataForm.isDeleted" placeholder="是否删除 0-未删除 1-已删除"></el-input>
    </el-form-item>
    <el-form-item label="添加时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="添加时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          linkId: 0,
          linkType: '',
          linkName: '',
          linkUrl: '',
          linkDescription: '',
          linkRank: '',
          isDeleted: '',
          createTime: ''
        },
        dataRule: {
          linkType: [
            { required: true, message: '友链类别 0-友链 1-推荐 2-个人网站不能为空', trigger: 'blur' }
          ],
          linkName: [
            { required: true, message: '网站名称不能为空', trigger: 'blur' }
          ],
          linkUrl: [
            { required: true, message: '网站链接不能为空', trigger: 'blur' }
          ],
          linkDescription: [
            { required: true, message: '网站描述不能为空', trigger: 'blur' }
          ],
          linkRank: [
            { required: true, message: '用于列表排序不能为空', trigger: 'blur' }
          ],
          isDeleted: [
            { required: true, message: '是否删除 0-未删除 1-已删除不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '添加时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.linkId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.linkId) {
            this.$http({
              url: this.$http.adornUrl(`/blog/link/info/${this.dataForm.linkId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.linkType = data.link.linkType
                this.dataForm.linkName = data.link.linkName
                this.dataForm.linkUrl = data.link.linkUrl
                this.dataForm.linkDescription = data.link.linkDescription
                this.dataForm.linkRank = data.link.linkRank
                this.dataForm.isDeleted = data.link.isDeleted
                this.dataForm.createTime = data.link.createTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/blog/link/${!this.dataForm.linkId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'linkId': this.dataForm.linkId || undefined,
                'linkType': this.dataForm.linkType,
                'linkName': this.dataForm.linkName,
                'linkUrl': this.dataForm.linkUrl,
                'linkDescription': this.dataForm.linkDescription,
                'linkRank': this.dataForm.linkRank,
                'isDeleted': this.dataForm.isDeleted,
                'createTime': this.dataForm.createTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>

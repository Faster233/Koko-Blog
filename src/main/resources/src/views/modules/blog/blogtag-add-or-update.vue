<template>
  <el-dialog
    :title="!dataForm.tagId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标签名称" prop="tagName">
      <el-input v-model="dataForm.tagName" placeholder="标签名称"></el-input>
    </el-form-item>
    <el-form-item label="是否删除 0=否 1=是" prop="isDeleted">
      <el-input v-model="dataForm.isDeleted" placeholder="是否删除 0=否 1=是"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
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
          tagId: 0,
          tagName: '',
          isDeleted: '',
          createTime: ''
        },
        dataRule: {
          tagName: [
            { required: true, message: '标签名称不能为空', trigger: 'blur' }
          ],
          isDeleted: [
            { required: true, message: '是否删除 0=否 1=是不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.tagId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.tagId) {
            this.$http({
              url: this.$http.adornUrl(`/blog/blogtag/info/${this.dataForm.tagId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.tagName = data.blogTag.tagName
                this.dataForm.isDeleted = data.blogTag.isDeleted
                this.dataForm.createTime = data.blogTag.createTime
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
              url: this.$http.adornUrl(`/blog/blogtag/${!this.dataForm.tagId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'tagId': this.dataForm.tagId || undefined,
                'tagName': this.dataForm.tagName,
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

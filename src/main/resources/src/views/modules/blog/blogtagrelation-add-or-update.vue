<template>
  <el-dialog
    :title="!dataForm.relationId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="博客id" prop="blogId">
      <el-input v-model="dataForm.blogId" placeholder="博客id"></el-input>
    </el-form-item>
    <el-form-item label="标签id" prop="tagId">
      <el-input v-model="dataForm.tagId" placeholder="标签id"></el-input>
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
          relationId: 0,
          blogId: '',
          tagId: '',
          createTime: ''
        },
        dataRule: {
          blogId: [
            { required: true, message: '博客id不能为空', trigger: 'blur' }
          ],
          tagId: [
            { required: true, message: '标签id不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '添加时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.relationId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.relationId) {
            this.$http({
              url: this.$http.adornUrl(`/blog/blogtagrelation/info/${this.dataForm.relationId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.blogId = data.blogTagRelation.blogId
                this.dataForm.tagId = data.blogTagRelation.tagId
                this.dataForm.createTime = data.blogTagRelation.createTime
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
              url: this.$http.adornUrl(`/blog/blogtagrelation/${!this.dataForm.relationId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'relationId': this.dataForm.relationId || undefined,
                'blogId': this.dataForm.blogId,
                'tagId': this.dataForm.tagId,
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

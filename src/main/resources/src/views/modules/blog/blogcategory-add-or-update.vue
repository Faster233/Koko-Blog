<template>
  <el-dialog
    :title="!dataForm.categoryId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="分类的名称" prop="categoryName">
      <el-input v-model="dataForm.categoryName" placeholder="分类的名称"></el-input>
    </el-form-item>
    <el-form-item label="分类的图标" prop="categoryIcon">
      <el-input v-model="dataForm.categoryIcon" placeholder="分类的图标"></el-input>
    </el-form-item>
    <el-form-item label="分类的排序值 被使用的越多数值越大" prop="categoryRank">
      <el-input v-model="dataForm.categoryRank" placeholder="分类的排序值 被使用的越多数值越大"></el-input>
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
          categoryId: 0,
          categoryName: '',
          categoryIcon: '',
          categoryRank: '',
          isDeleted: '',
          createTime: ''
        },
        dataRule: {
          categoryName: [
            { required: true, message: '分类的名称不能为空', trigger: 'blur' }
          ],
          categoryIcon: [
            { required: true, message: '分类的图标不能为空', trigger: 'blur' }
          ],
          categoryRank: [
            { required: true, message: '分类的排序值 被使用的越多数值越大不能为空', trigger: 'blur' }
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
        this.dataForm.categoryId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.categoryId) {
            this.$http({
              url: this.$http.adornUrl(`/blog/blogcategory/info/${this.dataForm.categoryId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.categoryName = data.blogCategory.categoryName
                this.dataForm.categoryIcon = data.blogCategory.categoryIcon
                this.dataForm.categoryRank = data.blogCategory.categoryRank
                this.dataForm.isDeleted = data.blogCategory.isDeleted
                this.dataForm.createTime = data.blogCategory.createTime
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
              url: this.$http.adornUrl(`/blog/blogcategory/${!this.dataForm.categoryId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'categoryId': this.dataForm.categoryId || undefined,
                'categoryName': this.dataForm.categoryName,
                'categoryIcon': this.dataForm.categoryIcon,
                'categoryRank': this.dataForm.categoryRank,
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
